package com.wengelef.functionalandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wengelef.functionalandroid.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        val universalLogger = UniversalAdobeLogger(AdobeAnalyticsTracker())

        universalLogger.trackEvent(MainTracker.MainEvent.MainEvent1)*/

        val tracker = provideTracker(::adobeTracker, ::mapper)

        tracker.invoke(MainEvent.MainEvent1)

    }
}



interface Event
interface ExternalEvent

fun <T : Event, U : ExternalEvent> provideTracker(
    tracker: ExternalTracker,
    eventMapper: T.() -> U): (T) -> Unit = { event -> tracker.invoke(eventMapper(event)) }

typealias ExternalTracker = (ExternalEvent) -> Unit

fun mapper(event: MainEvent): AdobeAnalyticsEvent {
    return AdobeAnalyticsEvent()
}

sealed class MainEvent : Event {

    object MainEvent1 : MainEvent()
}

fun adobeTracker(externalEvent: ExternalEvent) {
    Log.e("AdobeAnalyticsTracker", "$externalEvent")
}

class AdobeAnalyticsEvent : ExternalEvent {
    // values
}