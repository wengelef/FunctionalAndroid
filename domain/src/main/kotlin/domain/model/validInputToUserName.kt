package domain.model

fun validInputToUserName(input: LoginInput.Valid): Username =
    Username(input.value)