package domain.model.login

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.fx.IO
import arrow.fx.IODispatchers.CommonPool
import domain.model.User

// UseCase
typealias LoginUseCaseFn = (String) -> IO<Either<LoginError, User>>

// Repository Interface
typealias LoginUser = (String) -> IO<Either<LoginError, User>>

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

fun loginUseCase(loginUser: LoginUser, input: String): IO<Either<LoginError, User>> =
    IO(CommonPool) { LoginInput(input).mapLeft { LoginError.InvalidInput(it) } }
        .flatMap { maybeInput ->
            maybeInput.fold(
                { invalidInput -> IO { Left(invalidInput) } },
                { validInput -> loginUser(validInput.input) }
            )
        }