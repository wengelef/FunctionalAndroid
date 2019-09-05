package domain

inline class Username(val value: String)

sealed class LoginInput {
    data class Valid(val value: String) : LoginInput()
    object Invalid : LoginInput()

    companion object {
        operator fun invoke(input: String): LoginInput {
            return if (input.length < 3) {
                Invalid
            } else {
                Valid(input)
            }
        }
    }
}

data class User(val username: Username)

typealias LoginUseCaseFn = suspend (LoginInput.Valid) -> User

inline class LoginUseCase(private val loginUseCaseFn: LoginUseCaseFn) {
    suspend operator fun invoke(loginInput: LoginInput.Valid) = loginUseCaseFn(loginInput)
}

typealias GetUsersUseCaseFn = suspend () -> List<User>

inline class GetUsersUseCase(private val getUsersUseCaseFn: GetUsersUseCaseFn) {
    suspend operator fun invoke() = getUsersUseCaseFn()
}