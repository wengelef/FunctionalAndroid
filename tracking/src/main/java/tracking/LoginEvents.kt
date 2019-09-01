package tracking

sealed class LoginEvent : Event {
    data class Login(val username: String) : LoginEvent()
}

