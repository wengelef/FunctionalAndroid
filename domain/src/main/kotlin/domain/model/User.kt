package domain.model

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