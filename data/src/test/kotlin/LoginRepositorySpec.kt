import arrow.core.Right
import data.LoginServiceFn
import data.model.UserDto
import data.loginUser
import data.userDtoToUser
import domain.model.User
import domain.model.Username
import domain.model.validInputToUserName
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import util.partially

class LoginRepositorySpec : WordSpec() {

    init {
        "UserDtoToUser" should {
            "map a UserDto to a Domain User" {
                val userDto = UserDto(USERNAME)
                userDtoToUser(userDto) shouldBe User(Username(USERNAME))
            }
        }

        "LoginUseCase" should {
            "call the LoginService and Save the User to DB" {
                val loginService: LoginServiceFn = { name -> Right(UserDto(name)) }
                val loginUseCase =
                    ::loginUser
                        .partially(::validInputToUserName, loginService, { Right(UserDto(USERNAME)) })

                loginUseCase(USERNAME) shouldBe Right(User(Username(USERNAME)))
            }
        }
    }

    private companion object {
        const val USERNAME = "Florian Wengelewski"
    }
}