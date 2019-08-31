package com.wengelef.functionalandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wengelef.functionalandroid.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tracker = MainTracker(AdobeAnalyticsTracker())

        tracker.trackEvent(MainTracker.MainEvent.Event1)
    }
}

interface Tracker<in T : Tracker.Event> {

    interface EventMapper<in T : Event, out U>{
        fun mapEvent(event: T): U
    }

    interface Event

    fun trackEvent(event: T)

}


class MainTracker(private val adobeTracker: AdobeAnalyticsTracker) :
    Tracker<MainTracker.MainEvent>,
    Tracker.EventMapper<MainTracker.MainEvent, AdobeAnalyticsEvent> by MainEventMapper {

    sealed class MainEvent : Tracker.Event {
        object Event1 : MainEvent()
    }

    override fun trackEvent(event: MainEvent) {
        adobeTracker.trackEvent(mapEvent(event))
    }
}

object MainEventMapper : Tracker.EventMapper<MainTracker.MainEvent, AdobeAnalyticsEvent> {
    override fun mapEvent(event: MainTracker.MainEvent): AdobeAnalyticsEvent = when (event) {
        MainTracker.MainEvent.Event1 -> AdobeAnalyticsEvent()
    }
}

class AdobeAnalyticsTracker {
    fun trackEvent(event: AdobeAnalyticsEvent) {
        Log.e("AdobeAnalyticsTracker", "Event Tracked $event")
    }
}

class AdobeAnalyticsEvent {
    // values
}