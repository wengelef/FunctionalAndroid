package com.wengelef.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import arrow.fx.IO
import arrow.fx.extensions.fx
import domain.deleteusers.DeleteUsersUseCaseFn
import domain.getusers.GetUsersUseCaseFn
import domain.login.LoginError
import domain.login.LoginUseCaseFn
import domain.model.User
import kotlinx.coroutines.Dispatchers
import tracking.LoginTracker

sealed class LoginViewState
object Idle : LoginViewState()
object InvalidInput : LoginViewState()
object NetworkError : LoginViewState()
object Success : LoginViewState()
object UnexpectedError : LoginViewState()

class LoginViewModel(
    private val loginTracker: LoginTracker,
    private val loginUseCase: LoginUseCaseFn,
    private val getUsersUseCase: GetUsersUseCaseFn,
    private val deleteUsersUseCase: DeleteUsersUseCaseFn
) : ViewModel() {

    private val viewState = MutableLiveData<LoginViewState>()
        .apply { value = Idle }

    fun getViewState(): LiveData<LoginViewState> = viewState

    fun deleteUsers() {
        IO.fx {
            val result = !deleteUsersUseCase()
            continueOn(Dispatchers.Main)
            result.fold(
                { deleteUsersError -> Log.e("DeleteUsers", "Error $deleteUsersError") },
                { users -> Log.e("DeletUsers", "Users : $users") }
            )
        }.unsafeRunAsync(::onUnexpectedError)
    }

    fun getUsers() {
        IO.fx {
            val result = !getUsersUseCase()
            continueOn(Dispatchers.Main)
            result.fold(
                { dbError -> Log.e("GetUsers", "Error $dbError") },
                { users -> Log.e("GetUsers", "Users : $users") }
            )
        }.unsafeRunAsync(::onUnexpectedError)
    }

    fun login(username: String) {
        IO.fx {
            val result = !loginUseCase(username)
            continueOn(Dispatchers.Main)
            viewState.value = result.fold(::loginErrorToViewState, ::loginResultToViewState)
        }.unsafeRunAsync(::onUnexpectedError)
    }

    private fun <T> onUnexpectedError(result: Either<Throwable, T>) {
        result.mapLeft { viewState.value = UnexpectedError }
    }

    private fun loginErrorToViewState(loginError: LoginError): LoginViewState = when (loginError) {
        is LoginError.NetworkError -> NetworkError
        is LoginError.InvalidInput -> InvalidInput
        LoginError.IOError -> NetworkError
    }

    private fun loginResultToViewState(result: User): LoginViewState = Success
}