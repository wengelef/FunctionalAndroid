package domain.model.getusers

import arrow.core.Either
import arrow.fx.IO
import domain.model.User

// UseCase
typealias GetUsersUseCaseFn = () -> IO<Either<GetUsersError, List<User>>>

// Repository Interface
typealias GetUsers = () -> IO<Either<GetUsersError, List<User>>>

sealed class GetUsersError {
    object DbError : GetUsersError()
}

fun getUsersUseCase(getUsers: GetUsers): IO<Either<GetUsersError, List<User>>> = getUsers()