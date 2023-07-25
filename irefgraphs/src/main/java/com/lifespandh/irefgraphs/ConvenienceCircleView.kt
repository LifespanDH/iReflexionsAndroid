package com.lifespandh.irefgraphs

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.lifespandh.irefgraphs.BaseDrawing
import com.lifespandh.irefgraphs.ConvenienceCircle


class ConvenienceCircleView : View {

    var bgColor = Color.argb(255, 51, 51, 51)
    var circleValue: Int = 50
    var circleMaxValue: Int = 100

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.PushButton, defStyle, 0
        )

        a.recycle()
    }

    fun invalidateView() {
        invalidate()
    }

    fun updateValue(newValue: Int) {
        circleValue = newValue
        invalidateView()
    }

    private fun getDrawingFrame(): RectF =
        RectF(0f, 0f, (right - left).toFloat(), (bottom - top).toFloat())

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        ConvenienceCircle.drawConvenienceCircle(
            context,
            canvas,
            getDrawingFrame(),
            BaseDrawing.ResizingBehavior.AspectFit,
            bgColor,
            circleValue,
            circleMaxValue
        )
    }

}