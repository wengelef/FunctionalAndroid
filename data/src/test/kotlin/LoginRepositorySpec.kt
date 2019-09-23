import arrow.core.Left
import arrow.core.Right
import arrow.fx.IO
import arrow.syntax.function.partially1
import data.*
import data.model.UserDto
import domain.deleteusers.DeleteUsersError
import domain.getusers.GetUsersError
import domain.login.LoginError
import domain.login.NetworkErrorType
import domain.model.User
import domain.model.Username
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import util.partially
import java.io.IOException
import java.lang.IllegalStateException

class LoginRepositorySpec : WordSpec() {

    init {
        "userDtoToUser" should {
            "map a UserDto to a Domain User" {
                val userDto = UserDto(USERNAME)
                userDtoToUser(userDto) shouldBe VALID_USER
            }
        }

        "loginUser" should {
            "call the LoginService, Save the User to DB and return" {
                val loginService: LoginServiceFn = { name -> IO.just(UserDto(name)) }
                val saveUserToDb: SaveUserToDbFn = { userDto -> IO.just(UserDto(userDto.username)) }

                val loginUser = ::loginUser.partially(loginService, saveUserToDb, ::userDtoToUser)

                loginUser(USERNAME).unsafeRunSync() shouldBe Right(VALID_USER)
            }

            "propagate a DB Error" {
                val loginService: LoginServiceFn = { name -> IO { UserDto(name) } }
                val saveUserToDb: SaveUserToDbFn = { IO.raiseError(IllegalStateException()) }

                val loginUser = ::loginUser.partially(loginService, saveUserToDb, ::userDtoToUser)

                loginUser(USERNAME).unsafeRunSync() shouldBe Left(LoginError.IOError)
            }

            "propagate a Service Error" {
                val loginService: LoginServiceFn = { IO.raiseError(IllegalStateException()) }
                val saveUserToDb: SaveUserToDbFn = { IO.raiseError(IllegalStateException()) }

                val loginUser = ::loginUser.partially(loginService, saveUserToDb, ::userDtoToUser)

                loginUser(USERNAME).unsafeRunSync() shouldBe Left(LoginError.NetworkError(NetworkErrorType.Offline))
            }
        }

        "getUsers" should {
            "return a list of users" {
                val getUsersFromDbFn: GetUsersFromDbFn = { IO { listOfUsersDtos } }

                val getUsers = ::getUsers
                    .partially1(getUsersFromDbFn)
                    .partially1(::userDtoToUser)

                getUsers().unsafeRunSync() shouldBe Right(listOfUsers)
            }

            "propagate a DB Error" {
                val getUsersFromDbFn: GetUsersFromDbFn = { IO.raiseError(IOException()) }

                val getUsers = ::getUsers
                    .partially1(getUsersFromDbFn)
                    .partially1(::userDtoToUser)

                getUsers().unsafeRunSync() shouldBe Left(GetUsersError.DbError)
            }
        }

        "deleteUsers" should {
            "return an empty list of users" {
                val deleteUsersFromDbFn = { IO { listOfUsersDtos } }

                val deleteUsers = ::deleteUsers
                    .partially1(deleteUsersFromDbFn)
                    .partially1(::userDtoToUser)

                deleteUsers().unsafeRunSync() shouldBe Right(listOfUsers)
            }

            "propagate a DB Error" {
                val deleteUsersFromDbFn: DeleteUsersFromDbFn = { IO.raiseError(IOException()) }

                val deleteUsers = ::deleteUsers
                    .partially1(deleteUsersFromDbFn)
                    .partially1(::userDtoToUser)

                deleteUsers().unsafeRunSync() shouldBe Left(DeleteUsersError.DbError)
            }
        }
    }

    private companion object {
        const val USERNAME = "Florian Wengelewski"
        val VALID_USER = User(Username(USERNAME))

        val listOfUsers = listOf(
            User(Username("Flo")),
            User(Username("Peter")))

        val listOfUsersDtos = listOf(
            UserDto("Flo"),
            UserDto("Peter"))
    }
}