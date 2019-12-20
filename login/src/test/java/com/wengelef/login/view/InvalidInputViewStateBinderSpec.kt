package com.wengelef.login.view

import com.wengelef.login.databinding.FrLoginBinding
import com.wengelef.login.viewmodel.LoginViewModel
import io.kotlintest.specs.FreeSpec
import io.mockk.mockk
import io.mockk.verify

class InvalidInputViewStateBinderSpec : FreeSpec() {

    init {
        "Invalid Input should show the Invalid Text spec" {
            val viewBinding: FrLoginBinding = mockk()

            val viewStateBinder = InvalidInputViewStateBinder(viewBinding)

            viewStateBinder.onStateUpdate(LoginViewModel.LoginViewState.InvalidInput)

            verify { viewBinding.resultText.text = "Invalid Input" }
        }
    }
}