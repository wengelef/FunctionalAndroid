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
import com.wengelef.login.viewmodel.InvalidInput
import com.wengelef.login.viewmodel.LoginViewModel
import com.wengelef.login.viewmodel.LoginViewState
import com.wengelef.login.viewmodel.Success
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

private val loadFeatures by lazy { loadKoinModules(LoginModule()) }
private fun injectFeatures() = loadFeatures

class LoginFragment : Fragment() {

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectFeatures()

        val binding = FrLoginBinding.bind(view)

        observe(loginViewModel.getViewState()) { state -> bindState(state, binding) }

        binding.apply {
            loginButton.setOnClickListener { loginViewModel.login(usernameInput.text.toString()) }
            deleteButton.setOnClickListener { loginViewModel.deleteUsers() }
        }

        loginViewModel.getUsers()
    }

    private fun bindState(state: LoginViewState, binding: FrLoginBinding) {
        when (state) {
            is InvalidInput -> InvalidInputViewStateBinder.bind(state, binding)
            is Success -> SuccessViewStateBinder.bind(state, binding)
        }
    }
}