package com.wengelef.login.viewmodel

import domain.model.LoginInput

inline class Input(val value: String)

fun Input.validate(): LoginInput = LoginInput(this.value)