package domain

import arrow.core.Either
import domain.model.LoginError
import domain.model.LoginInput
import domain.model.User
import domain.model.Username

typealias LoginUseCaseFn = (String) -> Either<LoginError, User>
typealias GetUsersUseCaseFn = () -> List<User>

typealias InputToUserName = (LoginInput) -> Username
