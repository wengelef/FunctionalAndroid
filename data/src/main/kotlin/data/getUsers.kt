package data

import arrow.core.Either
import arrow.core.Right
import arrow.core.extensions.fx
import arrow.fx.IO
import arrow.fx.IODispatchers.CommonPool
import arrow.fx.extensions.fx
import arrow.fx.fix
import domain.model.User
import domain.model.getusers.GetUsersError

fun getUsers(
    getUsersFromDb: GetUsersFromDbFn,
    userDtoToUser: UserDtoToUser
): IO<Either<GetUsersError, List<User>>> = IO.fx {
    continueOn(CommonPool)
    val (userDtos) = getUsersFromDb()
    Right(userDtos.map(userDtoToUser))
}