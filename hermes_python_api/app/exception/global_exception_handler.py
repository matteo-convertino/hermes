from starlette.requests import Request
from starlette.responses import JSONResponse

from app.exception.model.custom_exception import CustomException


async def global_exception_handler(request: Request, exc: CustomException):
    return JSONResponse(
        status_code=exc.status_code,
        content={"errors": [exc.msg]},
    )
