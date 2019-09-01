package analyticslibrary

import tracking.LoginEvent

fun loginEventMapper(event: LoginEvent): AdobeAnalyticsEvent = when (event) {
    is LoginEvent.Login -> AdobeAnalyticsEvent("username" to event.username)
}