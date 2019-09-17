package domain.model

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right


class LoginInput private constructor(val input: String) {
    companion object {
        operator fun invoke(input: String): Either<LoginInputError, LoginInput> = when {
            input.length < 3 -> Left(LoginInputError.NotEnoughCharacters)
            input.length > 30 -> Left(LoginInputError.TooManyCharacters)
            else -> Right(LoginInput(input))
        }
    }
}

sealed class LoginInputError {
    object NotEnoughCharacters : LoginInputError()
    object TooManyCharacters : LoginInputError()
}

sealed class LoginError {
    data class InvalidInput(val value: LoginInputError) : LoginError()
    data class NetworkError(val value: NetworkErrorType) : LoginError()
}

sealed class NetworkErrorType {
    object Offline : NetworkErrorType()
}