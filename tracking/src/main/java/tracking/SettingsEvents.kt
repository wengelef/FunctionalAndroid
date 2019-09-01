package tracking

typealias SettingsTracker = Tracker<SettingsEvent>

sealed class SettingsEvent : Event {
    data class EnablePush(val enabled: Boolean) : SettingsEvent()
}
