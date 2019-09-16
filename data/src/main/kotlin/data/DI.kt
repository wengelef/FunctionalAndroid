package data

import arrow.core.Either
import arrow.core.extensions.either.monad.binding
import domain.InputToUserName
import domain.model.LoginError
import domain.model.LoginInput
import domain.model.NetworkErrorType
import domain.model.User

fun provideGetUsersUseCase(
    getUsersFn: GetUsersFn,
    userDtoToUser: UserDtoToUser
): Either<DbError, List<User>> = getUsersFn().map { userDtos -> userDtos.map(userDtoToUser) }

fun provideLoginUseCase(
    inputMapper: InputToUserName,
    loginService: LoginServiceFn,
    saveUserToDB: SaveUserFn,
    input: String
): Either<LoginError, User> = binding {
    val (username) = LoginInput(input)
        .mapLeft { inputError -> LoginError.InvalidInput(inputError) }
        .map(inputMapper)

    val (userDto) = loginService(username.value)
        .mapLeft { LoginError.NetworkError(NetworkErrorType.Offline) }

    saveUserToDB(userDto)

    User(username)
}