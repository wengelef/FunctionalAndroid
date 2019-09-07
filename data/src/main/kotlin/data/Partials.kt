package data

import domain.model.LoginInput
import domain.model.User

fun provideGetUsersUseCase(
    getUsersFn: GetUsersFn,
    userDtoToUser: UserDtoToUser
): List<User> = getUsersFn().map(userDtoToUser)

fun provideLoginUseCase(
    inputMapper: ValidInputToUsername,
    loginService: LoginServiceFn,
    saveUserToDB: SaveUserFn,
    validInput: LoginInput.Valid
): User {
    return validInput.let(inputMapper)
        .let { username ->
            val userDto = loginService(username.value)
            saveUserToDB(userDto)
            User(username)
        }
}