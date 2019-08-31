package com.wengelef.functionalandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wengelef.functionalandroid.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val universalLogger = UniversalAdobeLogger(AdobeAnalyticsTracker())

        universalLogger.trackEvent(MainTracker.MainEvent.MainEvent1)
    }
}

interface Tracker<in T : Tracker.Event> {

    interface Event

    fun trackEvent(event: T)
}

interface MainTracker : Tracker<MainTracker.MainEvent> {
    sealed class MainEvent : Tracker.Event {
        object MainEvent1 : MainEvent()
    }
}

class MainTrackerImpl(private val analyticsTracker: AdobeAnalyticsTracker) : MainTracker {

    override fun trackEvent(event: MainTracker.MainEvent) {
        when (event) {
            is MainTracker.MainEvent.MainEvent1 -> analyticsTracker.trackEvent(AdobeAnalyticsEvent())
        }
    }
}

class UniversalAdobeLogger(analyticsTracker: AdobeAnalyticsTracker) :
        MainTracker by MainTrackerImpl(analyticsTracker)

class AdobeAnalyticsTracker {
    fun trackEvent(event: AdobeAnalyticsEvent) {
        Log.e("AdobeAnalyticsTracker", "$event")
    }
}

class AdobeAnalyticsEvent {
    // values
}