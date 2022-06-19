package com.gmail.maystruks08.currencyconverter.domain.exceptions

sealed class AppError : Exception() {

    sealed class LocaleExceptions : AppError() {
        object NoConnection : LocaleExceptions()
        object NoCachedData : LocaleExceptions()
    }

    sealed class RemoteExceptions : AppError() {
        object ResponseEmptyError : RemoteExceptions()
        object AuthorizationError : RemoteExceptions()
        object DataNotFound : RemoteExceptions()
        object InternalServerError : RemoteExceptions()
    }
}
