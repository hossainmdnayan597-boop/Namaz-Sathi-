package com.example.data.qibla

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.abs

class CompassSensorManager(context: Context) {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager

    val isSensorAvailable: Boolean
        get() {
            val sm = sensorManager ?: return false
            val hasRotation = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null
            val hasOrientationPair = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null &&
                    sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null
            return hasRotation || hasOrientationPair
        }

    fun getDeviceAzimuth(): Flow<Float> = callbackFlow {
        val sm = sensorManager
        if (sm == null) {
            trySend(0f)
            close()
            return@callbackFlow
        }

        val rotationSensor = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        val accelSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        val rotationMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)
        val accelerometerReading = FloatArray(3)
        val magnetometerReading = FloatArray(3)
        var lastAzimuth = 0f

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return

                if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                    SensorManager.getOrientation(rotationMatrix, orientationAngles)
                    var azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
                    azimuth = (azimuth + 360f) % 360f
                    // Low-pass filter for smooth needle movement
                    val smoothed = smoothAngle(lastAzimuth, azimuth)
                    lastAzimuth = smoothed
                    trySend(smoothed)
                } else {
                    if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                        System.arraycopy(event.values, 0, accelerometerReading, 0, 3)
                    } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                        System.arraycopy(event.values, 0, magnetometerReading, 0, 3)
                    }

                    if (SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading)) {
                        SensorManager.getOrientation(rotationMatrix, orientationAngles)
                        var azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
                        azimuth = (azimuth + 360f) % 360f
                        val smoothed = smoothAngle(lastAzimuth, azimuth)
                        lastAzimuth = smoothed
                        trySend(smoothed)
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        if (rotationSensor != null) {
            sm.registerListener(listener, rotationSensor, SensorManager.SENSOR_DELAY_UI)
        } else {
            accelSensor?.let { sm.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI) }
            magSensor?.let { sm.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI) }
        }

        awaitClose {
            sm.unregisterListener(listener)
        }
    }

    private fun smoothAngle(current: Float, target: Float, alpha: Float = 0.25f): Float {
        var diff = target - current
        while (diff < -180f) diff += 360f
        while (diff > 180f) diff -= 360f
        return (current + alpha * diff + 360f) % 360f
    }
}
