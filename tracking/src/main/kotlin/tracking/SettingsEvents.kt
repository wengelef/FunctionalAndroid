package tracking

import tracking.model.Event

typealias SettingsTracker = Tracker<SettingsEvent>

sealed class SettingsEvent : Event {
    data class EnablePush(val enabled: Boolean) : SettingsEvent()
}
