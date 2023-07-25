package com.lifespandh.irefgraphs

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View

/**
 * BarGraphView is a CustomView to create scalable bar graphs.
 * Params:
 * numberOfBars. Default value is 10.
 * maxHeight: To set the context-dependent Y dimension.
 * barColor.
 * heights: array of context-dependent bar heights. The size of the array should match numberOfBars.
 *
 * Use it in an xml layout with optional parameters app:barColor2, app:maxHeight, and app:numberOfBars if custom.
 * heights are to be set in the fragment class.
 *
 */
class BarGraphView : View {

    companion object {
        private const val startDuration: Long = 1000
        private const val startTick: Long = 50
        private const val alphaMax = 255
    }

    private var _maxHeight = 0f
    private var _barWidthRatio = .75f

    private var _barColor1 = Color.RED
    private var _barColor2 = Color.BLUE
    private var _barColor3 = Color.GRAY
    private var _barColor4 = Color.GREEN
    var colors = intArrayOf()

    private var _topLineColor = Color.GRAY

    private var _showShadow = true
    private var _showMedian = false
    private var _showBaseline = false
    private var _showTop = false


    private val startSteps: Int = (startDuration / startTick).toInt()
    private var alpha = 0
    private val alphaStep = alphaMax / startSteps

    var heights1 = floatArrayOf()
    var heights2: FloatArray? = floatArrayOf()
    var heights3: FloatArray? = floatArrayOf()
    var heights4: FloatArray? = floatArrayOf()

    var heightsLinesTop: FloatArray? = floatArrayOf()

    var baseLine: Float? = null
    var baseLineColor = Color.DKGRAY

    var xAxisShow: Boolean = false
    var xAxisLabels: Array<String>? = null

    var yAxisShow: Boolean = false
    var yAxisLabels: Array<String>? = null
    //number of units heights to match the heights scale
    var yAxisLabelStep: Float? = null
    var xLabelOrientation: LabelOrientation = LabelOrientation.Horizontal

    var wideBarLine: Float? = null

    var barColor1: Int
        get() = _barColor1
        set(value) {
            _barColor1 = value
        }

    var barColor2: Int
        get() = _barColor2
        set(value) {
            _barColor2 = value
        }

    var barColor3: Int
        get() = _barColor3
        set(value) {
            _barColor3 = value
        }

    var barColor4: Int
        get() = _barColor4
        set(value) {
            _barColor4 = value
        }



    var maxHeight: Float
        get() {
            if (_maxHeight == 0f) {
                val max = if (heights4 != null && heights4!!.any()) heights4!!.max()
                else if (heights3 != null && heights3!!.any()) heights3!!.max()
                else if (heights2 != null && heights2!!.any()) heights2!!.max()
                else if (heights1.any()) heights1.max()
                else _maxHeight
                _maxHeight = 1.05f * max!!
            }
            return _maxHeight
        }
        set(value) {
            _maxHeight = value
        }

    var barWidthRatio: Float
        get() {
            if (_barWidthRatio == 0f && heights1.any()) {
                _barWidthRatio = .75f
            }
            return _barWidthRatio
        }
        set(value) {
            _barWidthRatio = value
        }

    var showShadow: Boolean
        get() {
            return _showShadow
        }
        set(value) {
            _showShadow = value
        }

    var showMedian: Boolean
        get() {
            return _showMedian
        }
        set(value) {
            _showMedian = value
        }

    var showBaseline: Boolean
        get() {
            return _showBaseline
        }
        set(value) {
            _showBaseline = value
        }

    var showTop: Boolean
        get() {
            return _showTop
        }
        set(value) {
            _showTop = value
        }

    var topLineColor: Int
        get() {
            return _topLineColor
        }
        set(value) {
            _topLineColor = value
        }

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

    @SuppressLint("SuspiciousIndentation")
    private fun init(attrs: AttributeSet?, defStyle: Int) {

        val a: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.BarGraphView, defStyle, 0
        )

        _barColor1 = a.getColor(
            R.styleable.BarGraphView_barColor1,
            barColor1
        )

        _barColor2 = a.getColor(
            R.styleable.BarGraphView_barColor2,
            barColor2
        )

        _barColor3 = a.getColor(
            R.styleable.BarGraphView_barColor3,
            barColor3
        )

        _barColor4 = a.getColor(
            R.styleable.BarGraphView_barColor4,
            barColor4
        )

        _maxHeight = a.getFloat(
            R.styleable.BarGraphView_maxH,
            maxHeight
        )

        _barWidthRatio = a.getFloat(
            R.styleable.BarGraphView_barWidthRatio,
            barWidthRatio
        )

        _showShadow = a.getBoolean(
            R.styleable.BarGraphView_showShadow,
            showShadow
        )

        _showMedian = a.getBoolean(
            R.styleable.BarGraphView_showMedian,
            showMedian
        )

        _showBaseline = a.getBoolean(
            R.styleable.BarGraphView_showBaseline,
            showBaseline
        )

        _showTop = a.getBoolean(
            R.styleable.BarGraphView_showTopLines,
            showTop
        )

        a.recycle()
      colors = intArrayOf(
            Color.LTGRAY,
            Color.GRAY,
            Color.DKGRAY)

        timer(startDuration, startTick).start()
    }

    private fun timer(millisInFuture: Long, countDownInterval: Long): CountDownTimer {
        return object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                alpha += alphaStep + 2
                if (alpha >= alphaMax) {
                    alpha = alphaMax
                    cancel()
                }
                invalidate()
            }

            override fun onFinish() {
                if (alpha < alphaMax) {
                    alpha = alphaMax
                    invalidate()
                }
            }
        }
    }


    private fun getDrawingFrame(): RectF =
        RectF(0f, 0f, (right - left).toFloat(), (bottom - top).toFloat())


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        BarGraph.drawBarGraphCanvas(
            canvas,
            getDrawingFrame(),
            BaseDrawing.ResizingBehavior.AspectFit,
            heights1,
            heights2,
            heights3,
            heights4,
            maxHeight,
            barColor1,
            barColor2,
            barColor3,
            barColor4,
            colors,
            alpha,
            barWidthRatio,
            showShadow,
            showMedian,
            showBaseline,
            baseLine,
            baseLineColor,
            showTop,
            heightsLinesTop,
            topLineColor,
            xAxisShow,
            xAxisLabels,
            yAxisShow,
            yAxisLabels,
            yAxisLabelStep,
            xLabelOrientation,
            wideBarLine
        )
    }
}

enum class LabelOrientation {
    Horizontal,
    Inclined,
    Vertical
}