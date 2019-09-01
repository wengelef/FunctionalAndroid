package tracking

typealias LoginTracker = Tracker<LoginEvent>

sealed class LoginEvent : Event {
    data class Login(val username: String) : LoginEvent()
}

