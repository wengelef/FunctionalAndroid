package data

import arrow.core.Either
import arrow.core.Right
import arrow.fx.IO
import arrow.fx.IODispatchers.CommonPool
import arrow.fx.extensions.fx
import domain.model.User
import domain.model.login.LoginError

fun loginUser(
    loginService: LoginServiceFn,
    saveUserToDB: SaveUserToDbFn,
    userDtoToUser: UserDtoToUser,
    validInput: String
): IO<Either<LoginError, User>> = IO.fx {
    continueOn(CommonPool)

    val (userDto) = loginService(validInput)
        .flatMap(saveUserToDB)

    Right(userDtoToUser(userDto))
}