package data

import domain.model.LoginInput
import domain.model.User
import domain.model.Username

fun validInputToUserName(input: LoginInput.Valid): Username =
    Username(input.value)

fun loginRepository(loginService: LoginService, saveUserToDB: SaveUserToDB): LoginWithRepository = { username ->
    val userDto = loginService(username.value)
    saveUserToDB(userDto)
    User(Username(userDto.username))
}

fun usersFromRepository(getUsersFromDB: GetUsersFromDB): UsersFromRepository = {
    getUsersFromDB().map { userDto -> User(Username(userDto.username)) }
}