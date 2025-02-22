package core.tools.shake

import core.tools.haptic.IOSHapticFeedback
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import platform.CoreMotion.CMMotionManager
import platform.Foundation.NSOperationQueue
import kotlin.math.sqrt

@OptIn(ExperimentalForeignApi::class)
class IOSShakeDetector : ShakeDetector {

    private companion object {
        const val SHAKE_THRESHOLD = 3.5f
        const val DELAY_INTERVAL = 750
        const val VIBRATE_DELAY = 150L
    }

    private val motionManager = CMMotionManager()
    private val touchFeedback = IOSHapticFeedback()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var lastShakeTime: Long = 0

    override fun start(onShake: () -> Unit) {
        if (!motionManager.isAccelerometerAvailable()) return

        motionManager.accelerometerUpdateInterval = 0.1
        motionManager.startAccelerometerUpdatesToQueue(NSOperationQueue.mainQueue) { data, _ ->
            if (data == null) return@startAccelerometerUpdatesToQueue

            data.acceleration.useContents {
                val totalAcceleration = sqrt((x * x + y * y + z * z))
                val currentTime = Clock.System.now().toEpochMilliseconds()
                if (totalAcceleration > SHAKE_THRESHOLD && (currentTime - lastShakeTime > DELAY_INTERVAL)) {
                    lastShakeTime = currentTime
                    vibrate()
                    onShake()
                }
            }
        }
    }

    override fun stop() {
        motionManager.stopAccelerometerUpdates()
    }

    private fun vibrate() {
        scope.launch {
            touchFeedback.performHeavyImpact()
            delay(VIBRATE_DELAY)
            touchFeedback.performHeavyImpact()
        }
    }
}
