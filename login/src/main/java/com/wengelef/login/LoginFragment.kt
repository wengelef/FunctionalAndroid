package com.wengelef.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wengelef.functionalandroid.ext.observe
import com.wengelef.login.di.LoginModule
import com.wengelef.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fr_login.*
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

        observe(loginViewModel.getViewState()) { state ->
            Log.e("State", "$state")

            when (state) {
                LoginViewModel.LoginViewState.InvalidInput -> {
                    Toast.makeText(context, "Input must be 3 or more Characters", Toast.LENGTH_LONG).show()
                }
                LoginViewModel.LoginViewState.Success -> {
                    Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
                }
            }
        }

        loginViewModel.loadUsers()

        loginButton.setOnClickListener { loginViewModel.login(username_input.text.toString()) }
    }
}

