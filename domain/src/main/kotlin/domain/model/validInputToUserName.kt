package domain.model

import domain.login.LoginInput

typealias InputToUserName = (LoginInput) -> Username

fun validInputToUserName(input: LoginInput): Username = Username(input.input)