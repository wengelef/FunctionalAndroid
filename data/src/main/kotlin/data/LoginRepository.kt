package data

import domain.*

data class UserDto(val username: String)

typealias LoginWithRepository = (Username) -> User
typealias UsersFromRepository = () -> List<User>

typealias LoginService = (String) -> UserDto
typealias SaveUserToDB = (UserDto) -> Unit

typealias GetUsersFromDB = () -> List<UserDto>

typealias ValidInputToUsername = (LoginInput.Valid) -> Username

fun validInputToUserName(input: LoginInput.Valid): Username =
    Username(input.value)

fun getLoginUseCase(
    inputMapper: ValidInputToUsername,
    repository: LoginWithRepository): LoginUseCase = { input ->

    repository(input.let(inputMapper))
}

fun loginRepository(loginService: LoginService, saveUserToDB: SaveUserToDB): LoginWithRepository = { username ->
    val userDto = loginService(username.value)
    saveUserToDB(userDto)
    User(Username(userDto.username))
}

fun getUsersUseCase(usersFromRepository: UsersFromRepository): GetUsersUseCase = {
    usersFromRepository()
}

fun usersFromRepository(getUsersFromDB: GetUsersFromDB): UsersFromRepository = {
    getUsersFromDB().map { userDto -> User(Username(userDto.username)) }
}