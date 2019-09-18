package data

import arrow.core.Either
import arrow.core.Right
import arrow.core.extensions.either.applicativeError.handleError
import arrow.core.extensions.either.monad.flatMap
import arrow.core.flatMap
import arrow.fx.IO
import arrow.fx.IODispatchers.CommonPool
import arrow.fx.extensions.fx
import arrow.fx.fix
import domain.model.InputToUserName
import domain.model.User
import domain.model.login.LoginError
import domain.model.login.LoginInput
import domain.model.login.NetworkErrorType
import java.io.IOException

fun loginUser(
    loginService: LoginServiceFn,
    saveUserToDB: SaveUserToDbFn,
    userDtoToUser: UserDtoToUser,
    input: LoginInput
): IO<Either<LoginError, User>> = IO.fx {
    continueOn(CommonPool)

    // todo this?
    val (username) = LoginInput(input.input)
        .fromEither { IOException() }

    val (userDto) = loginService(username.input)
        .flatMap(saveUserToDB)

    Right(userDtoToUser(userDto))
}