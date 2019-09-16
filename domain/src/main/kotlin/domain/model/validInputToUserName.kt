package domain.model

fun validInputToUserName(input: LoginInput): Username = Username(input.input)