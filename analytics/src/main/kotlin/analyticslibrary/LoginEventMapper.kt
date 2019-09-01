package analyticslibrary

import tracking.LoginEvent

fun loginEventMapper(event: LoginEvent): AnalyticsEvent = when (event) {
    is LoginEvent.Login -> AnalyticsEvent("username" to event.username)
}