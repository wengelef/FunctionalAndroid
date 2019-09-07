import data.*
import data.model.UserDto
import domain.model.LoginInput
import domain.model.User
import domain.model.Username
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class LoginRepositorySpec : WordSpec() {

    init {
        "ValidInputToUserName" should {
            "return a Username with the input as Value" {
                val validInput = LoginInput.Valid(USERNAME)
                validInputToUserName(validInput) shouldBe Username(USERNAME)
            }
        }

        "LoginRepository" should {
            "call the LoginService and save the User to DB" {
                val service: LoginServiceFn = { name -> UserDto(name) }
                val saveUser = SaveUserToDB { Unit }

                val loginRepository = loginRepository(service, saveUser)

                loginRepository(Username(USERNAME)) shouldBe User(
                    Username(
                        USERNAME
                    )
                )
            }
        }

        "LoginUseCase" should {
            "map a Valid Input and call the Repository" {
                val inputMapper: ValidInputToUsername = { input -> Username(input.value) }
                val repository: LoginWithRepository = { username -> User(Username(username.value)) }

                val loginUseCase = getLoginUseCase(inputMapper, repository)

                loginUseCase(LoginInput.Valid(USERNAME)) shouldBe User(
                    Username(
                        USERNAME
                    )
                )
            }
        }
    }

    private companion object {
        const val USERNAME = "Florian Wengelewski"
    }
}