package domain

import domain.model.LoginInput
import domain.model.User

typealias LoginUseCaseFn = (LoginInput.Valid) -> User
typealias GetUsersUseCaseFn = () -> List<User>