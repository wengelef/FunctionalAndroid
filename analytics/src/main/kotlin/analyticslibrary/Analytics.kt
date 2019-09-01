package analyticslibrary

typealias AdobeTracker = (AdobeAnalyticsEvent) -> Unit

typealias AdobeEventMapper<T> = T.() -> AdobeAnalyticsEvent

data class AdobeAnalyticsEvent(val values: HashMap<String, String> = hashMapOf()){
    companion object {
        operator fun invoke(values: List<Pair<String, String>>): AdobeAnalyticsEvent {
            return AdobeAnalyticsEvent(values.toMap() as HashMap<String, String>)
        }

        operator fun invoke(value: Pair<String, String>): AdobeAnalyticsEvent {
            return AdobeAnalyticsEvent(hashMapOf(value))
        }
    }
}


fun adobeTracker(adobeAnalyticsEvent: AdobeAnalyticsEvent) {
    println(adobeAnalyticsEvent.toString())
}

/*
fun <T : Event> getTracker(
    tracker: AdobeTracker,
    eventMapper: AdobeEventMapper<T>): Tracker<T> = { event -> tracker.invoke(event.let(eventMapper)) }
*/
