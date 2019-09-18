package data

import arrow.fx.IO
import data.model.UserDto
import domain.model.User

// Service
typealias LoginServiceFn = (String) -> IO<UserDto>

// Cache
typealias SaveUserToDbFn = (UserDto) -> IO<UserDto>
typealias GetUsersFromDbFn = () -> IO<List<UserDto>>
typealias DeleteUsersFromDbFn = () -> IO<List<UserDto>>

// Mappers
typealias UserDtoToUser = (UserDto) -> User

sealed class DbError {
    object IO : DbError()
}

sealed class LoginServiceError {
    object Offline : LoginServiceError()
}