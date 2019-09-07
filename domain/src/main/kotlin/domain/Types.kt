package domain

import domain.model.LoginInput
import domain.model.User

typealias LoginUseCaseFn = suspend (LoginInput.Valid) -> User
typealias GetUsersUseCaseFn = suspend () -> List<User>

inline class LoginUseCase(private val loginUseCaseFn: LoginUseCaseFn) {
    suspend operator fun invoke(loginInput: LoginInput.Valid) = loginUseCaseFn(loginInput)
}

inline class GetUsersUseCase(private val getUsersUseCaseFn: GetUsersUseCaseFn) {
    suspend operator fun invoke() = getUsersUseCaseFn()
}