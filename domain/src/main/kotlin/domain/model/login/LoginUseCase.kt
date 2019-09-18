package domain.model.login

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.core.extensions.fx
import arrow.fx.IO
import arrow.fx.fix
import domain.model.User

// UseCase
typealias LoginUseCaseFn = (String) -> Either<LoginError, User>

// Repository Interface
typealias LoginUser = (LoginInput) -> IO<Either<LoginError, User>>

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
    object IOError : LoginError()
}

sealed class NetworkErrorType {
    object Offline : NetworkErrorType()
}

fun loginUseCase(loginUser: LoginUser, input: String): Either<LoginError, User> = Either.fx {
    val (loginInput) = LoginInput(input)
        .mapLeft { loginInputError -> LoginError.InvalidInput(loginInputError) }

    val (user) = loginUser(loginInput).fix()
        .unsafeRunSync()
        .mapLeft { LoginError.IOError }

    user
}