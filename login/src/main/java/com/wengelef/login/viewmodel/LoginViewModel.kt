package com.wengelef.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import domain.model.deleteusers.DeleteUsersUseCaseFn
import domain.model.getusers.GetUsersUseCaseFn
import domain.model.login.LoginUseCaseFn
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
        object Success : LoginViewState()
    }

    private val viewState = MutableLiveData<LoginViewState>().apply { value = LoginViewState.Idle }
    fun getViewState(): LiveData<LoginViewState> = viewState

    fun deleteUsers() {
        viewModelScope.launch {
            deleteUsersUseCase()
                .fold(
                    { deleteUsersError -> Log.e("DeleteUsers", "Error $deleteUsersError") },
                    { users -> Log.e("DeleteUsers", "Users : $users")}
                )
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase()
                .fold(
                    { dbError -> Log.e("GetUsers", "Error $dbError") },
                    { users -> Log.e("GetUsers", "Users : $users") }
                )
        }
    }

    fun login(username: String) {
        loginUseCase(username)
            .unsafeRunAsync { cb ->
                cb.map { result ->
                    when (result) {
                        is Either.Right -> viewState.value = LoginViewState.Success
                        is Either.Left -> viewState.value = LoginViewState.InvalidInput
                    }
                }
            }
    }
}