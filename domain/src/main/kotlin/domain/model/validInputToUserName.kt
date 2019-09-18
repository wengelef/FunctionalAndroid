package domain.model

import domain.model.login.LoginInput

typealias InputToUserName = (LoginInput) -> Username

fun validInputToUserName(input: LoginInput): Username = Username(input.input)