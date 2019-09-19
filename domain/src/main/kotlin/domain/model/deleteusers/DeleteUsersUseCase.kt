package domain.model.deleteusers

import arrow.core.Either
import arrow.core.extensions.fx
import arrow.fx.IO
import arrow.fx.IODispatchers.CommonPool
import arrow.fx.extensions.fx
import arrow.fx.fix
import domain.model.User

// UseCase
typealias DeleteUsersUseCaseFn = () -> IO<Either<DeleteUsersError, List<User>>>

// Repository Interface
typealias DeleteUsers = () -> IO<Either<DeleteUsersError, List<User>>>

sealed class DeleteUsersError {
    object DbError : DeleteUsersError()
}

fun deleteUsersUseCase(deleteUsers: DeleteUsers): IO<Either<DeleteUsersError, List<User>>> = deleteUsers()