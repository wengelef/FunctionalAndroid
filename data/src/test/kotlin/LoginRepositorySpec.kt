import arrow.core.Right
import arrow.core.orNull
import arrow.fx.IO
import data.LoginServiceFn
import data.SaveUserToDbFn
import data.loginUser
import data.model.UserDto
import data.userDtoToUser
import domain.model.User
import domain.model.Username
import domain.model.login.LoginInput
import io.kotlintest.TestCaseConfig
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

        "LoginUser" should {
            //todo this
            "call the LoginService and Save the User to DB".config(enabled = false) {
                val loginService: LoginServiceFn = { name -> IO.just(UserDto(name)) }
                val saveUserToDb: SaveUserToDbFn = { userDto -> IO.just(UserDto(userDto.username)) }

                val loginUser = ::loginUser.partially(loginService, saveUserToDb, ::userDtoToUser)

                loginUser(LoginInput(USERNAME).orNull()!!) shouldBe IO.just(Right(User(Username(USERNAME))))
            }
        }
    }

    private companion object {
        const val USERNAME = "Florian Wengelewski"
    }
}