package com.toolsfordevs.fast.core.util

import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class SensorDetector(private val callback: () -> Unit) : SensorEventListener
{

    private var runnable:CancellableRunnable? = null
    private val handler = Handler(Looper.getMainLooper())

    init
    {
        register()
    }

    override fun onSensorChanged(event: SensorEvent)
    {
        if (true)// check is opening via shaking is enabled
        {
            if (event.sensor.type == 1)
            {
                // app-window will only receive event at the top
                if (checkIfShake(event.values[0], event.values[1], event.values[2]))
                {
                    Log.d("SensorManger", "shake detected $this")
                    runnable?.cancel()
                    runnable = CancellableRunnable { callback() }
                    handler.postDelayed(runnable, 500)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int)
    {
        // do nothing
    }

    private fun register()
    {
        try
        {
            val manager = AppInstance.get().getSystemService(SENSOR_SERVICE) as SensorManager
            val sensor = manager.getDefaultSensor(TYPE_ACCELEROMETER)
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        catch (t: Throwable)
        {
            t.printStackTrace()
        }
    }

    fun unRegister()
    {
        try
        {
            val manager = AppInstance.get().getSystemService(SENSOR_SERVICE) as SensorManager
            val sensor = manager.getDefaultSensor(TYPE_ACCELEROMETER)
            manager.unregisterListener(this, sensor)
        }
        catch (t: Throwable)
        {
            t.printStackTrace()
        }
    }

    companion object
    {
        const val THRESHOLD = 1000
        private var lastCheckTime: Long = 0
        private val lastXyz = FloatArray(3)


        private fun checkIfShake(x: Float, y: Float, z: Float): Boolean
        {
            val currentTime = System.currentTimeMillis()
            val diffTime = currentTime - lastCheckTime
            if (diffTime < 100)
                return false

            lastCheckTime = currentTime
            val deltaX = x - lastXyz[0]
            val deltaY = y - lastXyz[1]
            val deltaZ = z - lastXyz[2]
            lastXyz[0] = x
            lastXyz[1] = y
            lastXyz[2] = z

            val delta = (Math.sqrt((deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ).toDouble()) / diffTime * 10000).toInt()
            return delta > THRESHOLD
        }
    }

    private class CancellableRunnable(val action:() -> Unit) : Runnable
    {
        private var isCancelled = false

        fun cancel()
        {
            isCancelled = true
        }

        override fun run()
        {
            Log.d("SensorManger", "run $isCancelled")
            if (!isCancelled)
                action()
        }
    }

}