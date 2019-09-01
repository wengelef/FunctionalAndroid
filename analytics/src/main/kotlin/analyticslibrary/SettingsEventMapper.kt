package analyticslibrary

import tracking.SettingsEvent

fun settingsEventMapper(event: SettingsEvent) = when (event) {
    is SettingsEvent.EnablePush -> AnalyticsEvent("enablePush" to event.enabled.toString())
}