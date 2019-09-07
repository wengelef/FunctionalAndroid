package domain

import domain.model.LoginInput
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

class LoginInputSpec : FreeSpec() {

    init {
        "Short names should be invalid" {
            LoginInput("ab") shouldBe LoginInput.Invalid
        }

        "Long names should be valid" {
            LoginInput("Florian Wengelewski") shouldBe LoginInput.Valid("Florian Wengelewski")
        }
    }
}