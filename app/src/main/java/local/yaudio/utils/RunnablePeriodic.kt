package local.yaudio.utils

import android.os.Handler
import android.os.Looper

/** For periodic executing Runnable */
class RunnablePeriodic(private val action: Runnable,
                       private var periodMillis: Long = 1000) : Runnable
{
    private var isStarted = false
    fun idStarted(): Boolean = isStarted

    override fun run() {
        if(isStarted) {
            action.run()
            Handler(Looper.getMainLooper())
                .postDelayed(this, periodMillis)
        }
    }

    fun onStart() {
        if(!isStarted) {
            isStarted = true
            run()
        }
    }

    fun onStop() {
        if(isStarted)
            isStarted = false
    }

    fun setPeriod(periodMillis: Long) { this.periodMillis = periodMillis }
}