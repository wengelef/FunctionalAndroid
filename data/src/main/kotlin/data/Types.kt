package data

import data.model.UserDto
import domain.model.LoginInput
import domain.model.User
import domain.model.Username

// Service
typealias LoginServiceFn = (String) -> UserDto

// Cache
typealias SaveUserFn = (UserDto) -> Unit
typealias GetUsersFn = () -> List<UserDto>

// Mappers
typealias UserDtoToUser = (UserDto) -> User