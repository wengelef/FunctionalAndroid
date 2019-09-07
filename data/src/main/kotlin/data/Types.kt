package data

import data.model.UserDto
import domain.model.LoginInput
import domain.model.User
import domain.model.Username

typealias LoginWithRepository = (Username) -> User
typealias UsersFromRepository = () -> List<User>
typealias LoginService = (String) -> UserDto
typealias SaveUserFn = (UserDto) -> Unit
typealias GetUsersFn = () -> List<UserDto>
typealias ValidInputToUsername = (LoginInput.Valid) -> Username

class SaveUserToDB(private val saveUserFn: SaveUserFn) {
    operator fun invoke(userDto: UserDto) = saveUserFn(userDto)
}

class GetUsersFromDB(private val getUsersFn: GetUsersFn) {
    operator fun invoke() = getUsersFn()
}