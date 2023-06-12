package com.lifespandh.irefgraphs

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import androidx.databinding.ObservableField

class BreathPacerView : View {

    companion object {
        private const val minSize = 0f
        private const val maxFillHeight = 180f
        val TAG = "BreathPacerView"
    }

    var filledHeight = 0f

    private var timeElapsed: Long = 0
    var inhaleTimeMs = 6_000
    var postInhaleTimeMs = 2_000
    var exhaleTimeMs = 10_000
    var postExhaleTimeMa = 3_000

    var inhalePostInhaleMs = inhaleTimeMs + postInhaleTimeMs
    var inhalePostInhaleExhaleMs = inhalePostInhaleMs + exhaleTimeMs
    var totalTimeMs = inhalePostInhaleExhaleMs + postExhaleTimeMa
    private var countDownInterval = 50L

    private var currentBreathState = BreathState.Inhale
    private var inhaleSteps = inhaleTimeMs / countDownInterval
    private var exhaleSteps = exhaleTimeMs / countDownInterval


    private var increaseStep = maxFillHeight / inhaleSteps.toFloat()
    private var decreaseStep = maxFillHeight / exhaleSteps.toFloat()

    private var currentInhaleStep = inhaleSteps + 1
    private var currentExhaleStep = exhaleSteps

    private var _totalTimeMillisecs: Long = 180_000
    private var inhaleCount = 0
    private var exhaleCount = 0

//    var isInhale = ObservableBoolean(true)
    var breathState = ObservableField(BreathState.Inhale)

    private var timer: CountDownTimer = createCountdownTimer(_totalTimeMillisecs, countDownInterval)


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

    }

    fun startBreathPacer(setTrainingTime: Boolean = false) {

        timer.start()!!
    }

    fun stopBreathPacer() {
        timer.cancel()
    }

    private fun createCountdownTimer(
        millisInFuture: Long,
        countDownInterval: Long
    ): CountDownTimer {
        return object : CountDownTimer(millisInFuture, countDownInterval) {

            override fun onTick(millisUntilFinished: Long) {
                timeElapsed += countDownInterval

                if (timeElapsed > totalTimeMs) {
                    timeElapsed = 0
                    filledHeight = 0f
                    currentInhaleStep = inhaleSteps + 1
                    currentExhaleStep = exhaleSteps
                    exhaleCount = 0
                    inhaleCount = 0
                }

                when (timeElapsed) {
                    in 0..inhaleTimeMs -> {
                        if (inhaleCount == 0) {
                            Thread.sleep(100)
                        }
//                        isInhale.set(true)

                        currentBreathState = BreathState.Inhale
                        inhaleCount++
                    }
                    in (inhaleTimeMs + 1)..(inhalePostInhaleMs) -> {
                        currentBreathState = BreathState.PostInhale
                    }
                    in (inhalePostInhaleMs + 1)..(inhalePostInhaleExhaleMs) -> {
                        if (exhaleCount == 0) {
                            Thread.sleep(100)
                        }
                        currentBreathState = BreathState.Exhale
//                        isInhale.set(false)
                        exhaleCount++
                    }
                    in (inhalePostInhaleExhaleMs + 1)..(totalTimeMs) -> {
                        currentBreathState = BreathState.PostExhale
                    }

                }

                breathState.set(currentBreathState)

                if (currentBreathState == BreathState.Inhale) {
                    currentInhaleStep -= 2
                } else if (currentBreathState == BreathState.Exhale) {
                    currentExhaleStep -= 2
                }

                when (currentBreathState) {
                    BreathState.Inhale -> {
                        if (filledHeight < 180){
                            filledHeight += increaseStep * (1 + (currentInhaleStep.toFloat() / inhaleSteps))
                        }
                    }
                    BreathState.Exhale -> {
                        if (filledHeight > 0) {
                            filledHeight -= decreaseStep * (1 + (currentExhaleStep.toFloat() / exhaleSteps))
                        }
                    }
                    BreathState.PostInhale -> {
                        filledHeight = 179F
                    }
                    BreathState.PostExhale -> {
                        filledHeight = 1F
                    }
                }

                invalidate()
            }

            override fun onFinish() {}
        }

    }

    private fun getDrawingFrame(): RectF
            = RectF(0f, 0f, (right - left).toFloat(), (bottom - top).toFloat())

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        BreathPacer.drawBreathPacer(
            canvas,
            getDrawingFrame(),
            BaseDrawing.ResizingBehavior.AspectFit,
            filledHeight
        )
    }

    enum class BreathState {
        Inhale,
        PostInhale,
        Exhale,
        PostExhale
    }
}