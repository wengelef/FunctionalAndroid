package com.wengelef.login.view

import androidx.viewbinding.ViewBinding

interface ViewStateBinder <State, T : ViewBinding> {
    fun bind(state: State, binding: T)
}