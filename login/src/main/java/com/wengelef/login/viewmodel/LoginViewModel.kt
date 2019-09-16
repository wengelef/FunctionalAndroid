package com.wengelef.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.LoginUseCaseFn
import kotlinx.coroutines.launch
import tracking.LoginTracker
import util.Dispatcher

class LoginViewModel(
    private val loginTracker: LoginTracker,
    private val loginUseCase: LoginUseCaseFn,
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
                loginUseCase(username)
                    .fold(
                        { LoginViewState.InvalidInput },
                        { LoginViewState.Success }
                    )
            }

            viewState.value = state
        }
    }
}