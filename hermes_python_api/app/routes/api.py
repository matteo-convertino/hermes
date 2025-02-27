from fastapi import APIRouter

from app.endpoints import telegram_api

router = APIRouter()
router.include_router(telegram_api.router)
