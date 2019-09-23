package domain.getusers

import arrow.core.Left
import arrow.core.Right
import arrow.fx.IO
import arrow.syntax.function.partially1
import domain.model.User
import domain.model.Username
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class GetUsersUseCaseSpec : WordSpec() {

    init {
        "Success" should {
            "return a List of users" {
                val getUsersFn = IO { Right(listOfUsers) }
                val useCase = ::getUsersUseCase.partially1 { getUsersFn }

                useCase().unsafeRunSync() shouldBe Right(listOfUsers)
            }
        }

        "Errors" should {
            "be propagated" {
                val getUsersFn = IO { Left(GetUsersError.DbError) }
                val useCase = ::getUsersUseCase.partially1 { getUsersFn }

                useCase().unsafeRunSync() shouldBe Left(GetUsersError.DbError)
            }
        }
    }


    companion object {
        val listOfUsers = listOf(
            User(Username("Flo")),
            User(Username("Peter")))
    }
}