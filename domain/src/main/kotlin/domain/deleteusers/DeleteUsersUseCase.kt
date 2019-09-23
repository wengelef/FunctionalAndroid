package domain.deleteusers

import arrow.core.Either
import arrow.fx.IO
import domain.model.User

// UseCase
typealias DeleteUsersUseCaseFn = () -> IO<Either<DeleteUsersError, List<User>>>

// Repository Interface
typealias DeleteUsersFn = () -> IO<Either<DeleteUsersError, List<User>>>

sealed class DeleteUsersError {
    object DbError : DeleteUsersError()
}

fun deleteUsersUseCase(deleteUsers: DeleteUsersFn): IO<Either<DeleteUsersError, List<User>>> = deleteUsers()