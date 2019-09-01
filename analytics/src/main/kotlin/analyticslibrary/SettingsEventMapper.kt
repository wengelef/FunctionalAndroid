package analyticslibrary

import tracking.SettingsEvent

fun settingsEventMapper(event: SettingsEvent) = when (event) {
    is SettingsEvent.EnablePush -> AdobeAnalyticsEvent("enablePush" to event.enabled.toString())
}