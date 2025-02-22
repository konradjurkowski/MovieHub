package core.tools.shake

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import kotlinx.datetime.Clock
import kotlin.math.sqrt

class AndroidShakeDetector(context: Context) : ShakeDetector {

    private companion object {
        const val SHAKE_THRESHOLD = 12f
        const val DELAY_INTERVAL = 750
        const val VIBRATE_DURATION = 500L
    }

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val vibrator = context.getSystemService(Vibrator::class.java)

    private var lastShakeTime = 0L
    private var sensor: Sensor? = null
    private var sensorEventListener: SensorEventListener? = null


    override fun start(onShake: () -> Unit) {
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val acceleration = sqrt((x * x + y * y + z * z).toDouble()) - SensorManager.GRAVITY_EARTH
                val currentTime = Clock.System.now().toEpochMilliseconds()
                if (acceleration > SHAKE_THRESHOLD && (currentTime - lastShakeTime > DELAY_INTERVAL)) {
                    lastShakeTime = currentTime
                    vibrate()
                    onShake()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // NO - OP
            }
        }
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun stop() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    private fun vibrate() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            vibrator.vibrate(VIBRATE_DURATION)
            return
        }

        val vibrationEffect = VibrationEffect.createOneShot(VIBRATE_DURATION, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffect)
    }
}
