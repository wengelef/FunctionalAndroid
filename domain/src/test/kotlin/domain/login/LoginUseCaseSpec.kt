package domain.login

import arrow.core.Left
import arrow.core.Right
import arrow.fx.IO
import arrow.syntax.function.partially1
import domain.model.User
import domain.model.Username
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class LoginUseCaseSpec : WordSpec() {

    init {
        "Valid Username" should {
            "return a valid User" {
                val loginUserFn: LoginUserFn = { IO { Right(validUser) } }
                val useCase = ::loginUseCase.partially1(loginUserFn)

                useCase(validUserName).unsafeRunSync() shouldBe
                        Right(validUser)
            }
        }

        "Invalid Username" should {
            "return a validation error" {
                val loginUserFn: LoginUserFn = { IO { Left(LoginError.InvalidInput(LoginInputError.NotEnoughCharacters)) } }
                val useCase = ::loginUseCase.partially1(loginUserFn)

                useCase(invalidUsername).unsafeRunSync() shouldBe
                        Left(LoginError.InvalidInput(LoginInputError.NotEnoughCharacters))
            }
        }
    }

    companion object {
        private const val invalidUsername = "Fl"
        private const val validUserName = "Florian Wengelewski"
        val validUser = User(Username(validUserName))
    }
}