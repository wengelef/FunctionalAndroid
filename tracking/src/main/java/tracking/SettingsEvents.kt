package tracking

sealed class SettingsEvent : Event {
    data class EnablePush(val enabled: Boolean) : SettingsEvent()
}
