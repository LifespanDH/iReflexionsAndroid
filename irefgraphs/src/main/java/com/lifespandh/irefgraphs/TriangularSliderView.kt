package com.lifespandh.irefgraphs

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData

class TriangularSliderView : View {

    var progress = 0f
    var intensity: MutableLiveData<Int> = MutableLiveData<Int>()

    private var choiceMade: Boolean = false
    private var sizesSet = false
    private var realWidth = 0f
    private var ratioX = 0f
    private var barX1 = 0f
    private var barX2 = 0f
    private var totalBarWidth = 0f

    private val sliderGestureListener
            = object : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent): Boolean {

            setSliderProgress(e)
            return super.onSingleTapUp(e)
        }

        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {

            setSliderProgress(e2)
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {

            setSliderProgress(e2)
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    private fun setSliderProgress(e: MotionEvent?) {
        progress = 100 * (e!!.x - barX1) / totalBarWidth
        when {
            progress < 10 -> {
                intensity.value = 0
            }
            progress < 30 -> {
                intensity.value = 1
            }
            progress < 50 -> {
                intensity.value = 2
            }
            progress < 70 -> {
                intensity.value = 3
            }
            progress < 90 -> {
                intensity.value = 4
            }
            else -> {
                intensity.value = 5
            }
        }
        invalidate()
    }

    private val gestureDetector: GestureDetector = GestureDetector(context, sliderGestureListener)

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
        progress = 0f
        intensity.value = 0
    }

    private fun setFrameSizes() {

        realWidth = (right - left).toFloat()

        ratioX = realWidth / TriangularSlider.BASE_WIDTH
        barX1 = TriangularSlider.SLIDER_BAR_BASE_START * ratioX
        barX2 = TriangularSlider.SLIDER_BAR_BASE_END * ratioX

        totalBarWidth = barX2 - barX1
    }

    private fun getDrawingFrame(): RectF {
        if (!sizesSet){
            setFrameSizes()
            sizesSet = true
        }

        return  RectF(0f, 0f, (right - left).toFloat(), (bottom - top).toFloat())
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        TriangularSlider.drawTriangleSlider (
            canvas,
            getDrawingFrame(),
            BaseDrawing.ResizingBehavior.AspectFit,
            progress
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        return gestureDetector.onTouchEvent(event).let { _ ->

            choiceMade = true
            true
        }

        return super.onTouchEvent(event)
    }
}