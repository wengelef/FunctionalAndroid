package domain

import domain.model.LoginInput
import domain.model.User
import domain.model.Username

typealias LoginUseCaseFn = (LoginInput.Valid) -> User
typealias GetUsersUseCaseFn = () -> List<User>

typealias ValidInputToUsername = (LoginInput.Valid) -> Username
