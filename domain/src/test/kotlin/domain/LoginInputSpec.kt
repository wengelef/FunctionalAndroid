package domain

import domain.model.LoginInput
import domain.model.Username
import domain.model.validInputToUserName
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

class LoginInputSpec : FreeSpec() {

    init {
        "ValidInputToUserName map a Valid Input to a Username" {
            val validInput = LoginInput.Valid(VALID_NAME)
            validInputToUserName(validInput) shouldBe Username(VALID_NAME)
        }

        "Short names should be invalid" {
            LoginInput(INVALID_NAME) shouldBe LoginInput.Invalid
        }

        "Long names should be valid" {
            LoginInput(VALID_NAME) shouldBe LoginInput.Valid(VALID_NAME)
        }
    }

    companion object {
        const val INVALID_NAME = "ab"
        const val VALID_NAME = "Florian Wengelewski"
    }
}