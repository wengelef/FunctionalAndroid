package data

import domain.*

data class UserDto(val username: String)

typealias LoginWithRepository = (Username, LoginService) -> User

typealias LoginService = (String) -> UserDto

typealias ValidInputToUsername = (LoginInput.Valid) -> Username

fun validInputToUserName(input: LoginInput.Valid): Username =
    Username(input.value)

fun loginUseCase(
    inputMapper: ValidInputToUsername,
    repository: LoginWithRepository,
    loginService: LoginService): LoginUseCase = { input -> repository(input.let(inputMapper), loginService) }

fun loginRepository(username: Username, loginService: LoginService): User {
    return loginService(username.value).let(::userDtoToUser)
}

internal fun userDtoToUser(userDto: UserDto): User =
    User(Username(userDto.username))