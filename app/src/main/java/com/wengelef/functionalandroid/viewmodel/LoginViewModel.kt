package com.wengelef.functionalandroid.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.GetUsersUseCase
import domain.LoginInput
import domain.LoginUseCase
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
        Input(username)
            .validate()
            .let { input ->
                Log.e("Input", "$input")
                when (input) {
                    is LoginInput.Valid -> {
                        loginUseCase(input)
                        viewState.value = LoginViewState.Success
                    }
                    else -> viewState.value = LoginViewState.InvalidInput
                }
            }
    }

    fun loadUsers() {
        Log.e("Users", "${getUsersUseCase()}")
    }
}