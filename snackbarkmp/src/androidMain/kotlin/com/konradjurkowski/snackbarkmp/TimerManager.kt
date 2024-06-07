package com.konradjurkowski.snackbarkmp

import java.util.Timer
import kotlin.concurrent.schedule

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TimerManager {
    private var timer: Timer? = null

    actual fun scheduleTimer(
        visibilityDuration: Long,
        onTimerTriggered: () -> Unit
    ) {
        if (timer != null) {
            cancelTimer()
        }

        timer = Timer("Message Bar Animation Timer", true)
        timer?.schedule(visibilityDuration) {
            onTimerTriggered()
            cancelTimer()
        }
    }

    actual fun cancelTimer() {
        timer?.cancel()
        timer?.purge()
        timer = null
    }
}
