package domain.deleteusers

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.fx.IO
import arrow.syntax.function.partially1
import domain.model.User
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class DeleteUsersUseCaseSpec : WordSpec() {


    init {
        "Success" should {
            "return an empty list of users" {
                val deleteUsersFn = { IO { Either.Right(emptyList<User>()) } }
                val useCase = ::deleteUsersUseCase.partially1(deleteUsersFn)

                useCase.invoke().unsafeRunSync() shouldBe Right(emptyList<User>())
            }
        }

        "Error" should {
            "propagate the error on the Left" {
                val deleteUsersFn = { IO { Either.Left(DeleteUsersError.DbError) } }
                val useCase = ::deleteUsersUseCase.partially1(deleteUsersFn)

                useCase.invoke().unsafeRunSync() shouldBe Left(DeleteUsersError.DbError)
            }
        }

    }
}