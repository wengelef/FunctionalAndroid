package data

import arrow.core.Either
import arrow.core.Right
import arrow.fx.IO
import arrow.fx.IODispatchers
import arrow.fx.extensions.fx
import domain.model.User
import domain.model.deleteusers.DeleteUsersError

fun deleteUsers(
    deleteUsersFromDb: DeleteUsersFromDbFn,
    userDtoToUser: UserDtoToUser
): IO<Either<DeleteUsersError, List<User>>> = IO.fx {
    continueOn(IODispatchers.CommonPool)
    val (userDtos) = deleteUsersFromDb()
    Right(userDtos.map(userDtoToUser))
}