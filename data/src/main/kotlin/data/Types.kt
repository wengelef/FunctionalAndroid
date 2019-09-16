package data

import arrow.core.Either
import data.model.UserDto
import domain.model.LoginError
import domain.model.LoginInput
import domain.model.User
import domain.model.Username

// Service
typealias LoginServiceFn = (String) -> Either<LoginServiceError, UserDto>

// Cache
typealias SaveUserFn = (UserDto) -> Either<DbError, UserDto>
typealias GetUsersFn = () -> Either<DbError, List<UserDto>>

// Mappers
typealias UserDtoToUser = (UserDto) -> User

sealed class DbError {
    object IO : DbError()
}

sealed class LoginServiceError {
    object Offline : LoginServiceError()
}