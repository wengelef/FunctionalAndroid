package com.wengelef.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wengelef.autil.observe
import com.wengelef.login.R
import com.wengelef.login.databinding.FrLoginBinding
import com.wengelef.login.di.LoginModule
import com.wengelef.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

private val loadFeatures by lazy { loadKoinModules(LoginModule()) }
private fun injectFeatures() = loadFeatures

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectFeatures()

        val loginViewModel by viewModel<LoginViewModel>()

        val binding = FrLoginBinding.bind(view)

        val invalidInputBinder = InvalidInputViewStateBinder(binding)
        val successViewStateBinder = SuccessViewStateBinder(binding)

        observe(loginViewModel.getViewState()) { state ->
            when (state) {
                is LoginViewModel.LoginViewState.InvalidInput -> invalidInputBinder.onStateUpdate(state)
                is LoginViewModel.LoginViewState.Success -> successViewStateBinder.onStateUpdate(state)
            }
        }

        loginViewModel.getUsers()

        binding.apply {
            loginButton.setOnClickListener { loginViewModel.login(usernameInput.text.toString()) }
            deleteButton.setOnClickListener { loginViewModel.deleteUsers() }
        }
    }
}

interface ViewState

interface ViewStateBinder<T : ViewState> {
    fun onStateUpdate(state: T)
}

class InvalidInputViewStateBinder(private val binding: FrLoginBinding) : ViewStateBinder<LoginViewModel.LoginViewState.InvalidInput> {
    override fun onStateUpdate(state: LoginViewModel.LoginViewState.InvalidInput) {
        binding.resultText.text = "Invalid Input"
    }
}

class SuccessViewStateBinder(private val binding: FrLoginBinding) : ViewStateBinder<LoginViewModel.LoginViewState.Success> {
    override fun onStateUpdate(state: LoginViewModel.LoginViewState.Success) {
        binding.resultText.text = "Success!"
    }
}