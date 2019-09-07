package tracking

import tracking.model.Event

typealias LoginTrackerFn = Tracker<LoginEvent>

class LoginTracker(private val loginTrackerFn: LoginTrackerFn) {
    operator fun invoke(loginEvent: LoginEvent) = loginTrackerFn(loginEvent)
}

sealed class LoginEvent : Event {
    data class Login(val username: String) : LoginEvent()
}

