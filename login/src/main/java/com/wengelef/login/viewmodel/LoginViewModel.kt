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
import util.Dispatcher

class LoginViewModel(
    private val loginTracker: LoginTracker,
    private val loginUseCase: LoginUseCaseFn,
    private val getUsersUseCase: GetUsersUseCaseFn,
    private val deleteUsersUseCase: DeleteUsersUseCaseFn,
    private val dispatcher: Dispatcher
) : ViewModel(), Dispatcher by dispatcher {

    sealed class LoginViewState {
        object Idle : LoginViewState()
        object InvalidInput : LoginViewState()
        object NetworkError : LoginViewState()
        object Success : LoginViewState()
        object UnexpectedError : LoginViewState()
    }

    private val viewState = MutableLiveData<LoginViewState>().apply { value = LoginViewState.Idle }
    fun getViewState(): LiveData<LoginViewState> = viewState

    fun deleteUsers() {
        IO.fx {
            val result = !deleteUsersUseCase().attempt()
            continueOn(Dispatchers.Main)
            result.fold(
                { deleteUsersError -> Log.e("DeleteUsers", "Error ${deleteUsersError}") },
                { users -> Log.e("DeleteUsers", "Users : ${users}") }
            )
        }.unsafeRunAsync(::onUnexpectedError)
    }

    fun getUsers() {
        IO.fx {
            val result = !getUsersUseCase().attempt()
            continueOn(Dispatchers.Main)
            result.fold(
                { dbError -> Log.e("GetUsers", "Error $dbError") },
                { users -> Log.e("GetUsers", "Users : $users") }
            )
        }.unsafeRunAsync(::onUnexpectedError)
    }

    fun login(username: String) {
        IO.fx {
            val result = !loginUseCase(username).attempt()
            continueOn(Dispatchers.Main)
            viewState.value = result.fold(
                { LoginViewState.UnexpectedError },
                ::loginResultToViewState
            )
        }.unsafeRunAsync(::onUnexpectedError)
    }

    private fun <T> onUnexpectedError(result: Either<Throwable, T>) {
        result.mapLeft { viewState.value = LoginViewState.UnexpectedError }
    }

    private fun loginResultToViewState(result: Either<LoginError, User>): LoginViewState =
        result.fold(
            { loginError ->
                when (loginError) {
                    is LoginError.NetworkError -> LoginViewState.NetworkError
                    is LoginError.InvalidInput -> LoginViewState.InvalidInput
                    LoginError.IOError -> LoginViewState.NetworkError
                }
            },
            { LoginViewState.Success })
}