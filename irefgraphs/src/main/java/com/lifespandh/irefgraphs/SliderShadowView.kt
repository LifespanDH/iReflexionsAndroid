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
import com.lifespandh.irefgraphs.SliderShadow


class SliderShadowView: View {

    /**
     * sliderValueBarColor is a color of the filled colored part
     */
    var sliderValueBarColor: Int = Color.argb(255, 63, 120, 183)
    /**
     * sliderCanvasColor is a color of the unfilled dark part
     */
    var sliderCanvasColor: Int = Color.argb(255, 67, 67, 67)
    /**
     * sliderValue should be in the range of 0 to 100
     */
    var sliderValue: Int = 50
    /**
     * length defines the ratio between widht and height. Default sizes are length = 500, height = 50
     * If change is required, to preserve the ratio of shadows change the length value to keep the required widht/height ratio
     */
    var length: Float = 500f

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
        val a: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.PushButton, defStyle, 0
        )

        a.recycle()
    }

    fun invalidateView() {
        invalidate()
    }

    private fun getDrawingFrame(): RectF
            = RectF(0f, 0f, (right - left).toFloat(), (bottom - top).toFloat())

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        SliderShadow.drawSliderShadow(
            context,
            canvas,
            getDrawingFrame(),
            BaseDrawing.ResizingBehavior.AspectFit,
            length,
            sliderCanvasColor,
            sliderValueBarColor,
            sliderValue
        )
    }

}