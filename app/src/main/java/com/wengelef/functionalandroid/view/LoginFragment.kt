package com.wengelef.functionalandroid.view

import analyticslibrary.adobeTracker
import analyticslibrary.loginEventMapper
import analyticslibrary.settingsEventMapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wengelef.functionalandroid.R
import com.wengelef.functionalandroid.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tracking.LoginEvent
import tracking.SettingsEvent
import tracking.getTracker

class LoginFragment : Fragment() {

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginTracker = getTracker(::adobeTracker, ::loginEventMapper)
        val settingsTracker = getTracker(::adobeTracker, ::settingsEventMapper)

        loginTracker(LoginEvent.Login("Flo"))
        settingsTracker(SettingsEvent.EnablePush(true))
    }
}

