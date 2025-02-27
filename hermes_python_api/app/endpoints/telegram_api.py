import base64
import uuid

from fastapi import APIRouter, Depends
from fastapi_restful.cbv import cbv
from hermes_valkey.hermes_valkey import HermesValkey
from starlette import status
from starlette.responses import Response
from telethon import functions
from telethon.errors import SessionPasswordNeededError, PhoneCodeExpiredError, PhoneCodeInvalidError, \
    PhoneNumberInvalidError, PasswordHashInvalidError, FloodWaitError
from telethon.tl.custom import Dialog
from telethon.tl.types import ChatPhoto, Chat

from app.dependencies import get_settings, get_hermes_valkey
from app.dto.request.telegram_init_login_dto import TelegramInitLoginDTO
from app.dto.request.telegram_login_verify_code_dto import TelegramLoginVerifyCodeDTO
from app.exception.model.phone_code_expired import PhoneCodeExpiredException
from app.exception.model.telegram_invalid_password import TelegramInvalidPasswordException
from app.exception.model.telegram_invalid_phone import TelegramInvalidPhoneException
from app.exception.model.telegram_password_needed import TelegramPasswordNeededException
from app.exception.model.telegram_session_expired import TelegramSessionExpiredException
from app.exception.model.telegram_too_many_attempts import TelegramTooManyAttemptsException
from app.exception.model.wrong_device_id import WrongDeviceIdException
from app.utils.custom_telegram_client import CustomTelegramClient
from app.utils.settings import Settings

router = APIRouter(
    prefix="/telegram",
    tags=["/telegram"],
)


@cbv(router)
class TelegramApi:
    def __init__(self, settings: Settings = Depends(get_settings), hermes_valkey: HermesValkey = Depends(get_hermes_valkey)):
        self.settings = settings
        self.hermes_valkey = hermes_valkey

    @router.post("/init-login")
    async def init_login(self, response: Response, init_login_dto: TelegramInitLoginDTO):
        device_id = str(uuid.uuid1())

        async with CustomTelegramClient(init_login_dto.phone_number, device_id, self.settings) as client:
            if not await client.is_user_authorized():
                try:
                    res = await client.send_code_request(init_login_dto.phone_number)
                except (PhoneNumberInvalidError, TypeError):
                    raise TelegramInvalidPhoneException()

                self.hermes_valkey.save_key(f"phone_code_hash_{device_id}", res.phone_code_hash)

                response.status_code = status.HTTP_202_ACCEPTED
                return {"deviceId": device_id}
            else:
                return await client.get_user(device_id)

    @router.post("/login-verify-code")
    async def login_verify_code(self, login_verify_code_dto: TelegramLoginVerifyCodeDTO):
        async with CustomTelegramClient(login_verify_code_dto.phone_number, login_verify_code_dto.device_id, self.settings) as client:
            if not await client.is_user_authorized():
                try:
                    await client.sign_in(
                        login_verify_code_dto.phone_number,
                        login_verify_code_dto.code,
                        password=login_verify_code_dto.password,
                        phone_code_hash=self.hermes_valkey.get_key(f"phone_code_hash_{login_verify_code_dto.device_id}")
                    )
                except SessionPasswordNeededError:
                    if login_verify_code_dto.password is None:
                        result = await client(functions.account.GetPasswordRequest())
                        raise TelegramPasswordNeededException(msg=result.hint)
                    else:
                        try:
                            await client.sign_in(password=login_verify_code_dto.password)
                        except PasswordHashInvalidError:
                            raise TelegramInvalidPasswordException
                        except FloodWaitError as e:
                            raise TelegramTooManyAttemptsException(
                                f"Too many login attempts. You have to wait {e.seconds} seconds.")
                except (PhoneCodeExpiredError, PhoneCodeInvalidError):
                    raise PhoneCodeExpiredException
                except ValueError:
                    raise WrongDeviceIdException

                self.hermes_valkey.delete_key(f"phone_code_hash_{login_verify_code_dto.device_id}")

            return await client.get_user(login_verify_code_dto.device_id)

    @router.get("/get-groups")
    async def get_groups(self, device_id: str, phone_number: str):
        async with CustomTelegramClient(phone_number, device_id, self.settings) as client:
            if not await client.is_user_authorized():
                raise TelegramSessionExpiredException
            else:
                groups = []

                async for dialog in client.iter_dialogs():  # type: Dialog
                    if dialog.is_group:
                        entity: Chat = dialog.entity

                        photo = await client.download_profile_photo(entity, file=bytes)

                        groups.append({
                            "groupId": entity.id,
                            "title": entity.title,
                            "photo": base64.b64encode(photo).decode('utf-8') if photo is not None else None
                        })

                return groups
