package com.wengelef.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.GetUsersUseCaseFn
import domain.LoginUseCaseFn
import domain.model.LoginInput
import kotlinx.coroutines.launch
import tracking.LoginTracker
import util.Dispatcher

class LoginViewModel(
    private val loginTracker: LoginTracker,
    private val loginUseCase: LoginUseCaseFn,
    private val getUsersUseCase: GetUsersUseCaseFn,
    private val dispatcher: Dispatcher
) : ViewModel(), Dispatcher by dispatcher {

    sealed class LoginViewState {
        object Idle : LoginViewState()
        object InvalidInput : LoginViewState()
        object Success : LoginViewState()
    }

    private val viewState = MutableLiveData<LoginViewState>().apply { value = LoginViewState.Idle }
    fun getViewState(): LiveData<LoginViewState> = viewState

    fun login(username: String) {
        viewModelScope.launch {
            val state = dispatch {
                Input(username)
                    .validate()
                    .let { input ->
                        when (input) {
                            is LoginInput.Valid -> {
                                loginUseCase(input)
                                LoginViewState.Success
                            }
                            else -> LoginViewState.InvalidInput
                        }
                    }
            }

            viewState.value = state
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            dispatch { Log.e("Users", "${getUsersUseCase()}") }
        }
    }
}