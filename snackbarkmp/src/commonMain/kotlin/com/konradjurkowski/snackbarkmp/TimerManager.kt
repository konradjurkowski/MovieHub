package com.konradjurkowski.snackbarkmp

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class TimerManager() {
    fun scheduleTimer(
        visibilityDuration: Long,
        onTimerTriggered: () -> Unit
    )
    fun cancelTimer()
}
