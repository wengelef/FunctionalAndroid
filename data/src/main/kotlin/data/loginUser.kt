package data

import arrow.core.Either
import arrow.core.Left
import arrow.fx.IO
import domain.model.User
import domain.model.login.LoginError
import domain.model.login.NetworkErrorType

fun loginUser(
    loginService: LoginServiceFn,
    saveUserToDB: SaveUserToDbFn,
    userDtoToUser: UserDtoToUser,
    validInput: String
): IO<Either<LoginError, User>> =
    loginService(validInput).attempt()
        .map { it.mapLeft { LoginError.NetworkError(NetworkErrorType.Offline) } }
        .flatMap { maybeDto ->
            maybeDto.fold(
                { IO { Left(LoginError.IOError) } },
                { userDto -> saveUserToDB(userDto).attempt()
                    .map { it.mapLeft { LoginError.IOError } }
                }
            )
        }
        .map { it.map(userDtoToUser) }