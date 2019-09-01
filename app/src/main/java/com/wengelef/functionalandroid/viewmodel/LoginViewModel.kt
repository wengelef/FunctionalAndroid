package com.wengelef.functionalandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tracking.LoginEvent
import tracking.Tracker

class LoginViewModel(
    private val loginTracker: Tracker<LoginEvent>
) : ViewModel() {

    sealed class LoginViewState {
        object Idle : LoginViewState()
        object Loading : LoginViewState()
    }

    private val viewState = MutableLiveData<LoginViewState>().apply { value = LoginViewState.Idle }
    fun getViewState(): LiveData<LoginViewState> = viewState
}