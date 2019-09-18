package domain.model.getusers

import arrow.core.Either
import arrow.core.extensions.fx
import arrow.fx.IO
import arrow.fx.fix
import domain.model.User

// UseCase
typealias GetUsersUseCaseFn = () -> Either<GetUsersError, List<User>>

// Repository Interface
typealias GetUsers = () -> IO<Either<GetUsersError, List<User>>>

sealed class GetUsersError {
    object DbError : GetUsersError()
}

fun getUsersUseCase(getUsers: GetUsers): Either<GetUsersError, List<User>> = Either.fx {
    val (users) = getUsers().fix()
        .unsafeRunSync()
        .mapLeft { GetUsersError.DbError }

    users
}