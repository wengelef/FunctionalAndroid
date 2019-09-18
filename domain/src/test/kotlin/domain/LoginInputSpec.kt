package domain

import arrow.core.*
import domain.model.login.LoginInput
import domain.model.login.LoginInputError
import domain.model.Username
import domain.model.validInputToUserName
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

class LoginInputSpec : FreeSpec() {

    init {
        "ValidInputToUserName map a Valid Input to a Username" {
            val validInput = LoginInput(VALID_NAME).orNull()!!
            validInputToUserName(validInput) shouldBe Username(VALID_NAME)
        }

        "Short names should be invalid" {
            LoginInput(TOO_SHORT) shouldBe Left(LoginInputError.NotEnoughCharacters)
        }

        "Long names should be invalid" {
            LoginInput(TOO_LONG) shouldBe Left(LoginInputError.TooManyCharacters)
        }

        "Long names should be valid" {
            LoginInput(VALID_NAME).shouldBeTypeOf<Either.Right<LoginInput>>()
        }
    }

    companion object {
        const val TOO_SHORT = "ab"
        const val TOO_LONG = "abcdefghijklmnopqrstuvwxyzabcdefgh"
        const val VALID_NAME = "Florian Wengelewski"
    }
}