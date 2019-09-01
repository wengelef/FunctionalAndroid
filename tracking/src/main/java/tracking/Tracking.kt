package tracking

typealias Tracker<T> = (T) -> Unit

typealias ExternalTracker<T> = (T) -> Unit
typealias EventMapper<T, U> = T.() -> U

fun <T : Event, U> getTracker(
    tracker: ExternalTracker<U>,
    eventMapper: EventMapper<T, U>) : Tracker<T> = { event -> tracker.invoke(event.let(eventMapper)) }

