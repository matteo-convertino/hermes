from contextlib import asynccontextmanager

import httpx
import uvicorn
from fastapi import FastAPI

from app.exception.global_exception_handler import global_exception_handler
from app.exception.model.phone_code_expired import PhoneCodeExpiredException
from app.exception.model.telegram_invalid_password import TelegramInvalidPasswordException
from app.exception.model.telegram_invalid_phone import TelegramInvalidPhoneException
from app.exception.model.telegram_password_needed import TelegramPasswordNeededException
from app.exception.model.telegram_session_expired import TelegramSessionExpiredException
from app.exception.model.telegram_too_many_attempts import TelegramTooManyAttemptsException
from app.exception.model.wrong_device_id import WrongDeviceIdException
from app.routes.api import router as api_router


@asynccontextmanager
async def lifespan(app: FastAPI):
    app.requests_client = httpx.AsyncClient()
    yield
    await app.requests_client.aclose()

app = FastAPI(lifespan=lifespan)

app.include_router(api_router)

app.add_exception_handler(TelegramSessionExpiredException, global_exception_handler)
app.add_exception_handler(TelegramPasswordNeededException, global_exception_handler)
app.add_exception_handler(PhoneCodeExpiredException, global_exception_handler)
app.add_exception_handler(WrongDeviceIdException, global_exception_handler)
app.add_exception_handler(TelegramInvalidPhoneException, global_exception_handler)
app.add_exception_handler(TelegramInvalidPasswordException, global_exception_handler)
app.add_exception_handler(TelegramTooManyAttemptsException, global_exception_handler)


if __name__ == "__main__":
    uvicorn.run("app.main:app", port=8080, host="0.0.0.0")
