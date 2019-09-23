package com.wengelef.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.deleteusers.DeleteUsersUseCaseFn
import domain.getusers.GetUsersUseCaseFn
import domain.login.LoginError
import domain.login.LoginUseCaseFn
import kotlinx.coroutines.launch
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
    }

    private val viewState = MutableLiveData<LoginViewState>().apply { value = LoginViewState.Idle }
    fun getViewState(): LiveData<LoginViewState> = viewState

    fun deleteUsers() {
        viewModelScope.launch {
            deleteUsersUseCase()
                .unsafeRunAsync {
                    it.map { result ->
                        result.fold(
                            { deleteUsersError -> Log.e("DeleteUsers", "Error ${deleteUsersError}") },
                            { users -> Log.e("DeleteUsers", "Users : ${users}") }
                        )
                    }
                }
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase()
                .unsafeRunAsync {
                    it.map { result ->
                        result.fold(
                            { dbError -> Log.e("GetUsers", "Error $dbError") },
                            { users -> Log.e("GetUsers", "Users : $users") }
                        )
                    }
                }
        }
    }

    fun login(username: String) {
        viewModelScope.launch {
            viewState.value = loginUseCase(username)
                .map { result ->
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
                .unsafeRunSync()
        }
    }
}