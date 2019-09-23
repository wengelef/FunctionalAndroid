package data

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.fx.IO
import domain.model.User
import domain.deleteusers.DeleteUsersError

fun deleteUsers(
    deleteUsersFromDb: DeleteUsersFromDbFn,
    userDtoToUser: UserDtoToUser
): IO<Either<DeleteUsersError, List<User>>> =
    deleteUsersFromDb().attempt()
        .map {
            it.fold(
                { Left(DeleteUsersError.DbError) },
                { userDtos -> Right(userDtos.map(userDtoToUser)) }
            )
        }