package data

import arrow.core.Either
import arrow.core.Left
import arrow.fx.IO
import domain.model.User
import domain.login.LoginError
import domain.login.NetworkErrorType

fun loginUser(
    loginService: LoginServiceFn,
    saveUserToDB: SaveUserToDbFn,
    userDtoToUser: UserDtoToUser,
    validInput: String
): IO<Either<LoginError, User>> =
    loginService(validInput).attempt()
        .flatMap { maybeDto ->
            maybeDto.fold(
                { IO { Left(LoginError.NetworkError(NetworkErrorType.Offline)) } },
                { userDto -> saveUserToDB(userDto).attempt()
                    .map { it.mapLeft { LoginError.IOError } }
                }
            )
        }
        .map { it.map(userDtoToUser) }