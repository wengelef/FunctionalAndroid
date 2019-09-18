package domain.model.deleteusers

import arrow.core.Either
import arrow.core.extensions.fx
import arrow.fx.IO
import arrow.fx.fix
import domain.model.User

// UseCase
typealias DeleteUsersUseCaseFn = () -> Either<DeleteUsersError, List<User>>

// Repository Interface
typealias DeleteUsers = () -> IO<Either<DeleteUsersError, List<User>>>

sealed class DeleteUsersError {
    object DbError : DeleteUsersError()
}

fun deleteUsersUseCase(deleteUsers: DeleteUsers): Either<DeleteUsersError, List<User>> = Either.fx {
    val (users) = deleteUsers().fix()
        .unsafeRunSync()
        .mapLeft { DeleteUsersError.DbError }

    users
}