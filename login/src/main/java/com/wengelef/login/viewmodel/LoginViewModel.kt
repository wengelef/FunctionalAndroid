package com.wengelef.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.GetUsersUseCase
import domain.LoginInput
import domain.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tracking.LoginTracker

inline class Input(val value: String)

fun Input.validate(): LoginInput = LoginInput(this.value)

class LoginViewModel(
    private val loginTracker: LoginTracker,
    private val loginUseCase: LoginUseCase,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    sealed class LoginViewState {
        object Idle : LoginViewState()
        object InvalidInput : LoginViewState()
        object Success : LoginViewState()
    }

    private val viewState = MutableLiveData<LoginViewState>().apply { value = LoginViewState.Idle }
    fun getViewState(): LiveData<LoginViewState> = viewState

    fun login(username: String) {
        viewModelScope.launch {
            val state = withContext(Dispatchers.IO) {
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
            CoroutineScope(Dispatchers.IO).launch {
                Log.e("Users", "${getUsersUseCase()}")
            }
        }
    }
}