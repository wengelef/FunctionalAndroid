package analyticslibrary

data class AnalyticsEvent(val values: HashMap<String, String> = hashMapOf()){
    companion object {
        operator fun invoke(values: List<Pair<String, String>>): AnalyticsEvent {
            return AnalyticsEvent(values.toMap() as HashMap<String, String>)
        }

        operator fun invoke(value: Pair<String, String>): AnalyticsEvent {
            return AnalyticsEvent(hashMapOf(value))
        }
    }
}


fun analyticsTracker(analyticsEvent: AnalyticsEvent) {
    println(analyticsEvent.toString())
}