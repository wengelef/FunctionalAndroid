package data

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.fx.IO
import arrow.fx.handleError
import domain.model.User
import domain.model.getusers.GetUsersError

fun getUsers(
    getUsersFromDb: GetUsersFromDbFn,
    userDtoToUser: UserDtoToUser
): IO<Either<GetUsersError, List<User>>> =
    getUsersFromDb().attempt()
        .map {
            it.fold(
                { Left(GetUsersError.DbError) },
                { userDtos -> Right(userDtos.map(userDtoToUser)) }
            )
        }