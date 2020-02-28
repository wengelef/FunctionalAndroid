package com.wengelef.login.view

import android.widget.Toast
import com.wengelef.login.databinding.FrLoginBinding
import com.wengelef.login.viewmodel.InvalidInput
import com.wengelef.login.viewmodel.LoginViewState
import com.wengelef.login.viewmodel.Success

sealed class LoginViewStateBinder<T : LoginViewState> : ViewStateBinder<T, FrLoginBinding>

object InvalidInputViewStateBinder : LoginViewStateBinder<InvalidInput>() {
    override fun bind(state: InvalidInput, binding: FrLoginBinding) {
        Toast.makeText(binding.root.context, "Input must be 3 or more Characters", Toast.LENGTH_LONG).show()
    }
}

object SuccessViewStateBinder : LoginViewStateBinder<Success>() {
    override fun bind(state: Success, binding: FrLoginBinding) {
        Toast.makeText(binding.root.context, "Success!", Toast.LENGTH_LONG).show()
    }
}
