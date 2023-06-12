package com.lifespandh.irefgraphs

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.sqrt

/**
 *
 */
class CheckInCircleView : View {

    var checkinObjects: List<CheckinObject> = listOf()
    var isCategory = false
    var selectedSector: MutableLiveData<Int> = MutableLiveData<Int>()

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

    fun invalidateView() {
        invalidate()
    }

    private fun getDrawingFrame(): RectF
            = RectF(0f, 0f, (right - left).toFloat(), (bottom - top).toFloat())

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        CheckinCircle.drawCheckInWheel(
            context,
            canvas,
            getDrawingFrame(),
            BaseDrawing.ResizingBehavior.AspectFit,
            checkinObjects,
            isCategory
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val y = event.y

        val widthToCenter = (right - left) / 2
        val heightToCenter = (bottom - top) / 2

        val xDistance = x - widthToCenter
        val yDistance = heightToCenter - y
        val vectorLength = sqrt(xDistance * xDistance + yDistance * yDistance)

        var sine: Float = 4f // military time sine
        var angle: Double = -1000.0

        if (vectorLength <= widthToCenter) {
            sine = yDistance / vectorLength
            angle = asin(sine) * 180 / PI // angle
        }

        if (angle != -1000.0) {
            if (xDistance < 0) {
                angle = 180 - angle
            } else if (yDistance < 0) {
                angle += 360
            }

            var sectorNumber = 0
            if (angle <= 90 && angle > 30) {
                sectorNumber = 0
            } else if (angle <= 30 || angle > 330) {
                sectorNumber = 1
            } else if (angle <= 330 && angle > 270) {
                sectorNumber = 2
            } else if (angle <= 270 && angle > 210) {
                sectorNumber = 3
            } else if (angle <= 210 && angle > 150) {
                sectorNumber = 4
            } else if (angle <= 150 && angle > 90) {
                sectorNumber = 5
            }

            selectedSector.value = sectorNumber
            checkinObjects[sectorNumber].isSelected = !checkinObjects[sectorNumber].isSelected
//            if (isCategory) {
//                deselectAllSectors(sectorNumber)
//            }

            invalidate()
        }

        return super.onTouchEvent(event)
    }

    private fun deselectAllSectors(exceptIndex: Int) {
        for((index, sector) in checkinObjects.withIndex()) {
            if (index != exceptIndex) {
                sector.isSelected = false
            }
        }
    }

}
