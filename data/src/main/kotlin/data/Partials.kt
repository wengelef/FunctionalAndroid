package data

import domain.GetUsersUseCase
import domain.LoginUseCase

fun getLoginUseCase(inputMapper: ValidInputToUsername, repository: LoginWithRepository) =
    LoginUseCase { input -> repository(input.let(inputMapper)) }

fun getUsersUseCase(usersFromRepository: UsersFromRepository) = GetUsersUseCase {
    usersFromRepository()
}