package com.lifespandh.irefgraphs


import android.graphics.*
import android.text.Layout
import android.text.TextPaint


/**
 * Drawing bar graphs for custom views.
 */
object BarGraph : BaseDrawing() {

    /**
     * Drawing method uses the base height and width agains the custom view drawing is placing and sizing elements.
     * Resizing is calculated against those base height and width.
     */

    private const val BASE_HEIGHT = 300f
    private const val BASE_WIDTH = 650f

    /**
     * graph area sizes excluding extra space for x and y axis labels
     */
    private var BASE_HEIGHT_GRAPH = BASE_HEIGHT
    private var BASE_WIDTH_GRAPH = BASE_WIDTH

    private const val FONT_SIZE = 27f

    private var startX: Float = 0f
    private var unitHeight: Float = 0f

    /**
     * width of the one value: bar + space
     */
    private var barXSpace: Float = 0f
    private var barWidth: Float = 0f

    private var xAxisLabelHeight: Float = 0f
    private var xAxisLabelWidth: Float = 0f
    private var xAxisLabelLength: Float = 0f
    private var yAxisLabelWidth: Float = 0f

    /**
     * y-size of the value unit
     */
    private var yGraphStep: Float = 0f

    private var numberOfBars = 0

    private object CanvasCache {

        internal var arraySize = 0
        internal val paint = Paint()
        lateinit var originalFrame: RectF
        internal val resizedFrame = RectF()

        internal lateinit var rectangles: Array<RectF>
        internal lateinit var paths: Array<Path>

        internal var shadow: GraphicShadow = GraphicShadow()
        internal val shadowPaint = Paint()

        internal val bezierMedianRect = RectF()
        internal val bezierMedianPath = Path()

        internal val bezierBaseLineRect = RectF()
        internal val bezierBaseLinePath = Path()

        internal val bezierXAxisPath = Path()

        internal val bezierYAxisPath = Path()

        fun setProperties() {
            originalFrame = RectF(0f, 0f, BASE_WIDTH, BASE_HEIGHT)

            rectangles = Array(arraySize) { RectF() }
            paths = Array(arraySize) { Path() }
        }
    }

    private fun setCache(arraySize: Int) {
        CanvasCache.arraySize = arraySize
        CanvasCache.setProperties()
    }

    // Resize to Target Frame
    private fun resize(
        canvas: Canvas,
        resizing: ResizingBehavior,
        targetFrame: RectF
    ) {
        canvas.save()
        val resizedFrame = CanvasCache.resizedFrame
        resizingBehaviorApply(
            resizing,
            CanvasCache.originalFrame,
            targetFrame,
            resizedFrame
        )
        canvas.apply {
            translate(resizedFrame.left, resizedFrame.top)
            scale(
                resizedFrame.width() / BASE_WIDTH,
                resizedFrame.height() / BASE_HEIGHT
            )
        }
    }

    fun drawBarGraphCanvas(
        canvas: Canvas,
        targetFrame: RectF,
        resizing: ResizingBehavior,
        heights1: FloatArray,
        heights2: FloatArray? = null,
        heights3: FloatArray? = null,
        heights4: FloatArray? = null,
        maxHeight: Float,
        color1: Int = Color.GREEN,
        color2: Int = Color.GRAY,
        color3: Int = Color.RED,
        color4: Int = Color.BLUE,
        colors: IntArray = intArrayOf(),
        alpha: Int = 255,
        barWidthRatio: Float = .75f,
        showShadow: Boolean = true,
        showMedian: Boolean = false,
        showBaseline: Boolean = false,
        baseLine: Float? = null,
        baseLineColor: Int = Color.DKGRAY,
        showTop: Boolean = false,
        heightsLinesTop: FloatArray? = null,
        topLineColor: Int,
        xAxisShow: Boolean = false,
        xAxisLabels: Array<String>? = null,
        yAxisShow: Boolean = false,
        yAxisLabels: Array<String>? = null,
        yAxisLabelStep: Float? = null,
        xLabelOrientation: LabelOrientation = LabelOrientation.Horizontal,
        wideBarLine: Float? = null
    ) {

        numberOfBars = heights1.size
        val paint = CanvasCache.paint
        val shadow = CanvasCache.shadow[GraphColor.colorByChangingAlpha(Color.BLACK, alpha / 2), 2f, 2f, 3f]

        val xLabelsExist = xAxisLabels != null && xAxisLabels.any()
        if (xLabelsExist) {
            xAxisLabelLength = FONT_SIZE + (xAxisLabels!!.maxBy { it.length })!!.length * FONT_SIZE

            xAxisLabelHeight = if (xLabelOrientation == LabelOrientation.Horizontal) {
                FONT_SIZE * 2
            } else {
                xAxisLabelLength
            }
        }

        BASE_HEIGHT_GRAPH =
            BASE_HEIGHT - (if (xAxisShow && xLabelsExist) xAxisLabelHeight / 1.4f else 0f)

        val countedMaxHeight: Float = if (maxHeight <= 0f) {
            if (heights4 != null && heights4.any()) heights4.max()!!
            else if (heights3 != null && heights3.any()) heights3.max()!!
            else if (heights2 != null && heights2.any()) heights2.max()!!
            else if (heights1.any()) heights1.max()!!
            else 0f
        } else {
            maxHeight
        }

        BASE_WIDTH_GRAPH = BASE_WIDTH - (if (yAxisShow) yAxisLabelWidth else 0f)

        setCache(numberOfBars)

        unitHeight = BASE_HEIGHT_GRAPH * 0.9f / (countedMaxHeight + 1)

        var localYLabels: Array<String>? = null

        if (yAxisLabelStep != null) {
            yGraphStep = yAxisLabelStep * unitHeight
            val size = (BASE_HEIGHT_GRAPH / yGraphStep).toInt()
            val labels = mutableListOf<String>()
            for (i in 0..size) {
                labels.add(i, (yAxisLabelStep * i).toInt().toString())
            }
            localYLabels = labels.toTypedArray()
        } else if (yAxisLabels != null && yAxisLabels.any()) {
            localYLabels = yAxisLabels
            yGraphStep = BASE_HEIGHT_GRAPH / (localYLabels.size)
        } else {
            localYLabels = arrayOf("")
            yGraphStep = BASE_HEIGHT_GRAPH
        }

        if (localYLabels.any()) {
            yAxisLabelWidth =
                FONT_SIZE / 2 + (localYLabels.maxBy { it.length })!!.length * FONT_SIZE
        }

        resize(canvas, resizing, targetFrame)

        barXSpace = BASE_WIDTH_GRAPH / numberOfBars
        barWidth = barXSpace * barWidthRatio

        startX = BASE_WIDTH - BASE_WIDTH_GRAPH

        for (i in 0 until numberOfBars) {

            if (heights4 != null && heights4.any()) {

                // draw Rectangle4
                drawBar(
                    i,
                    heights4,
                    paint,
                    showShadow,
                    canvas,
                    alpha,
                    shadow,
                    color4,
                    colors
                )
            }

            if (heights3 != null && heights3.any()) {

                // draw Rectangle3
                drawBar(
                    i,
                    heights3,
                    paint,
                    showShadow,
                    canvas,
                    alpha,
                    shadow,
                    color3,
                    colors
                )
            }

            if (heights2 != null && heights2.any()) {

                // draw Rectangle2
                drawBar(
                    i,
                    heights2,
                    paint,
                    showShadow,
                    canvas,
                    alpha,
                    shadow,
                    color2,
                    colors
                )
            }

            // draw Rectangle
            drawBar(
                i,
                heights1,
                paint,
                showShadow,
                canvas,
                alpha,
                shadow,
                color1,
                colors
            )

        }

        val displayTop = showTop && heightsLinesTop != null && heightsLinesTop.isNotEmpty()
        if (displayTop) {
            drawTopLines(heightsLinesTop, paint, alpha, canvas, topLineColor)
        }

        if (showMedian) {
            drawMedian(heights1, paint, canvas, shadow)
        }

        if (baseLine != null && showBaseline) {
            drawBaseLine(baseLine, paint, canvas, shadow, baseLineColor)
        }

        if (yAxisShow) {
            drawYaxis(paint, canvas, localYLabels)
        }

        if (xAxisShow) {
            drawXaxis(paint, canvas, xAxisLabels, xLabelOrientation)
        }

        drawWideBarLine(wideBarLine, paint, alpha, canvas, Color.GRAY)

    }

    private fun drawBar(
        i: Int,
        heights: FloatArray,
        paint: Paint,
        showShadow: Boolean,
        canvas: Canvas,
        alpha: Int,
        shadow: GraphicShadow,
        color: Int,
        colors: IntArray
    ) {

        val barHeight = heights[i] * unitHeight
        val yAxis = if (i < heights.size) BASE_HEIGHT_GRAPH - barHeight else 0f

        val rectangle = CanvasCache.rectangles[i]
        rectangle.set(
            (i * barXSpace + startX),
            yAxis,
            (i * barXSpace + barWidth + startX),
            yAxis + barHeight
        )
        val pathBack = CanvasCache.paths[i]
        pathBack.reset()
        pathBack.moveTo(rectangle.left, rectangle.top)
        pathBack.lineTo(rectangle.right, rectangle.top)
        pathBack.lineTo(rectangle.right, rectangle.bottom)
        pathBack.lineTo(rectangle.left, rectangle.bottom)
        pathBack.close()

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG

        if (showShadow) {
            showShadow(canvas, alpha, shadow, paint, pathBack)
        }



        paint.style = Paint.Style.FILL
        paint.color = color


        paint.shader = LinearGradient(0f, 0f, 0f, barHeight, colors, null, Shader.TileMode.CLAMP)

        canvas.drawPath(pathBack, paint)
    }

    private fun showShadow(
        canvas: Canvas,
        alpha: Int,
        shadow: GraphicShadow,
        paint: Paint,
        path: Path
    ) {
        canvas.saveLayerAlpha(
            null,
            alpha,
            Canvas.ALL_SAVE_FLAG
        ) //TODO (Timur): keep it for now .saveLayerAlpha(rectangle.left, rectangle.top, rectangle.right, rectangle.bottom, 255)
        run {
            canvas.translate(shadow.dx, shadow.dy)

            val shadowPaint = CanvasCache.shadowPaint
            shadowPaint.set(paint)
            shadow.setBlurOfPaint(shadowPaint)
            canvas.drawPath(path, shadowPaint)
            shadowPaint.xfermode = GlobalCache.blendModeSourceIn
            canvas.saveLayer(
                null,
                shadowPaint,
                Canvas.ALL_SAVE_FLAG
            ) //TODO (Timur): keep it for now .saveLayer(rectangle, shadowPaint)
            run { canvas.drawColor(shadow.color) }
            canvas.restore()
        }

        canvas.restore()
    }

    private fun drawWideBarLine(
        wideBarLine: Float?,
        paint: Paint,
        alpha: Int,
        canvas: Canvas,
        wideBarLineColor: Int
    ) {
        if (wideBarLine == null || wideBarLine == 0f) {
            return
        }

        val height = BASE_HEIGHT_GRAPH - wideBarLine * unitHeight
        val extend = barWidth * 0.1f
        val x1 = startX - extend
        val x2 = startX + barWidth + extend

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = wideBarLineColor
        paint.alpha = alpha
        paint.strokeWidth = 3f
        canvas.drawLine(x1, height, x2, height, paint)
    }

    private fun drawTopLines(
        heightsLinesTop: FloatArray?,
        paint: Paint,
        alpha: Int,
        canvas: Canvas,
        topLineColor: Int
    ) {
        if (heightsLinesTop == null || heightsLinesTop.size < 2) {
            return
        }

        val topsSize = heightsLinesTop.size - 1
        val topsSizeXY = topsSize * 4
        val yAxesTopLines = FloatArray(topsSizeXY)

        var k = 0
        for (i in 0..topsSize) {
            val heightOfLineTop = BASE_HEIGHT_GRAPH - heightsLinesTop[i] * unitHeight
            val xSpace = barXSpace * i + barWidth / 2 + startX
            if (i == 0 || i == topsSize) {
                yAxesTopLines[k++] = xSpace
                yAxesTopLines[k++] = heightOfLineTop
            } else {
                yAxesTopLines[k++] = xSpace
                yAxesTopLines[k++] = heightOfLineTop
                yAxesTopLines[k++] = xSpace
                yAxesTopLines[k++] = heightOfLineTop
            }
        }

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = topLineColor
        paint.alpha = alpha
        paint.strokeWidth = 3f
        canvas.drawLines(yAxesTopLines, paint)
    }

    private fun drawBaseLine(
        baseLine: Float,
        paint: Paint,
        canvas: Canvas,
        shadow: GraphicShadow,
        baseLineColor: Int
    ) {
        // Bezier baseLine
        val baseLineHeight = BASE_HEIGHT_GRAPH - baseLine * unitHeight
        val bezierBaseLineRect = CanvasCache.bezierBaseLineRect
        bezierBaseLineRect.set(startX, baseLineHeight, BASE_WIDTH_GRAPH + startX, baseLineHeight)
        val bezierBaseLinePath = CanvasCache.bezierBaseLinePath
        bezierBaseLinePath.reset()
        bezierBaseLinePath.moveTo(BASE_WIDTH_GRAPH + startX, baseLineHeight)
        bezierBaseLinePath.cubicTo(
            BASE_WIDTH_GRAPH + startX,
            baseLineHeight,
            startX,
            baseLineHeight,
            startX,
            baseLineHeight
        )

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        canvas.saveLayerAlpha(null, 255, Canvas.ALL_SAVE_FLAG)
        run {
            canvas.translate(shadow.dx, shadow.dy)

            val shadowPaint = CanvasCache.shadowPaint
            shadowPaint.set(paint)
            shadow.setBlurOfPaint(shadowPaint)
            canvas.drawPath(bezierBaseLinePath, shadowPaint)
            shadowPaint.xfermode = GlobalCache.blendModeSourceIn
            canvas.saveLayer(null, shadowPaint, Canvas.ALL_SAVE_FLAG)
            run { canvas.drawColor(shadow.color) }
            canvas.restore()
        }
        canvas.restore()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.color = baseLineColor
        canvas.drawPath(bezierBaseLinePath, paint)
    }

    private fun drawMedian(
        heights: FloatArray,
        paint: Paint,
        canvas: Canvas,
        shadow: GraphicShadow
    ) {
        // Bezier median
        val heightsNonNulls = heights.filter { it > 0 }
        val medianWidth = BASE_WIDTH_GRAPH * heightsNonNulls.size / heights.size
        val medianHeight = BASE_HEIGHT_GRAPH - (heightsNonNulls.average() * unitHeight).toFloat()
        val bezierMedianRect = CanvasCache.bezierMedianRect
        bezierMedianRect.set(startX, medianHeight, medianWidth + startX, medianHeight)
        val bezierMedianPath = CanvasCache.bezierMedianPath
        bezierMedianPath.reset()
        bezierMedianPath.moveTo(medianWidth + startX, medianHeight)
        bezierMedianPath.cubicTo(
            medianWidth + startX,
            medianHeight,
            0f,
            medianHeight,
            0f,
            medianHeight
        )

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        canvas.saveLayerAlpha(null, 255, Canvas.ALL_SAVE_FLAG)
        run {
            canvas.translate(shadow.dx, shadow.dy)

            val shadowPaint = CanvasCache.shadowPaint
            shadowPaint.set(paint)
            shadow.setBlurOfPaint(shadowPaint)
            canvas.drawPath(bezierMedianPath, shadowPaint)
            shadowPaint.xfermode = GlobalCache.blendModeSourceIn
            canvas.saveLayer(null, shadowPaint, Canvas.ALL_SAVE_FLAG)
            run { canvas.drawColor(shadow.color) }
            canvas.restore()
        }
        canvas.restore()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.color = Color.GRAY
        canvas.drawPath(bezierMedianPath, paint)
    }

    private fun drawXaxis(
        paint: Paint,
        canvas: Canvas,
        xAxisLabels: Array<String>? = null,
        xLabelOrientation: LabelOrientation = LabelOrientation.Horizontal
    ) {
        val xHeight = BASE_HEIGHT_GRAPH + 5
        val bezierXAxisPath = CanvasCache.bezierXAxisPath
        bezierXAxisPath.reset()
        bezierXAxisPath.moveTo(BASE_WIDTH, xHeight)
        bezierXAxisPath.cubicTo(BASE_WIDTH, xHeight, startX - 5, xHeight, startX - 5, xHeight)

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        canvas.saveLayerAlpha(null, 255, Canvas.ALL_SAVE_FLAG)

        canvas.restore()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.color = Color.GRAY
        canvas.drawPath(bezierXAxisPath, paint)

        if (xAxisLabels != null && xAxisLabels.any()) {

            val rotationShift: Float = when (xLabelOrientation) {
                LabelOrientation.Horizontal -> barXSpace / 4
                LabelOrientation.Inclined -> barXSpace / 2
                else -> -barXSpace * 0.6f  //vertical
            }

            val angle = when (xLabelOrientation) {
                LabelOrientation.Horizontal -> 0f
                LabelOrientation.Inclined -> -70f
                else -> -90f //vertical
            }

            val pivotAncorX = when (xLabelOrientation) {
                LabelOrientation.Horizontal -> 0f
                LabelOrientation.Inclined -> xAxisLabelHeight / 3f
                else -> xAxisLabelHeight / 1.6f //vertical
            }

            val pivotAncorY = when (xLabelOrientation) {
                LabelOrientation.Horizontal -> 10f
                LabelOrientation.Inclined -> 10f
                else -> -30f //vertical
            }

            val xAxisBottom = when (xLabelOrientation) {
                LabelOrientation.Vertical -> BASE_HEIGHT_GRAPH + xAxisLabelHeight * 0.5f + 70
                LabelOrientation.Inclined -> BASE_HEIGHT_GRAPH + xAxisLabelHeight * 1.2f
                else -> BASE_HEIGHT_GRAPH + xAxisLabelHeight
            }

            val xAxisStartShift = when (xLabelOrientation) {
                LabelOrientation.Vertical -> xAxisLabelHeight * 0.55f
                LabelOrientation.Inclined -> xAxisLabelHeight * 0.5f
                else -> xAxisLabelHeight * 0.7f
            }

            for (i in xAxisLabels.indices) {

                canvas.save()
                val textRect = RectF()

                textRect.set(
                    barXSpace * i + startX - xAxisStartShift,
                    BASE_HEIGHT_GRAPH,
                    barXSpace * i + startX + xAxisStartShift,
                    xAxisBottom
                )

                val textTextPaint = TextPaint()
                textTextPaint.reset()
                textTextPaint.flags = Paint.ANTI_ALIAS_FLAG
                textTextPaint.color = Color.GRAY

                textTextPaint.textSize = FONT_SIZE
                val textStaticLayout =
                    GraphStaticLayout()[textRect.width()
                        .toInt(), Layout.Alignment.ALIGN_CENTER, xAxisLabels[i], textTextPaint]
                canvas.save()
                canvas.clipRect(textRect)

                canvas.translate(
                    textRect.left + rotationShift,
                    textRect.top + (textRect.height() - textStaticLayout.height) / 2f
                )

                canvas.rotate(angle, pivotAncorX, pivotAncorY)

                textStaticLayout.draw(canvas)
                canvas.restore()
            }
        }
        canvas.restore()
    }

    private fun drawYaxis(
        paint: Paint,
        canvas: Canvas,
        localYLabels: Array<String>? = null
    ) {
        val yHeight = BASE_HEIGHT_GRAPH + 5
        val bezierYAxisPath = CanvasCache.bezierYAxisPath
        bezierYAxisPath.reset()
        bezierYAxisPath.moveTo(startX - 5, yHeight)
        bezierYAxisPath.cubicTo(startX - 5, yHeight, startX - 5, 10f, startX - 5, 10f)

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        canvas.saveLayerAlpha(null, 255, Canvas.ALL_SAVE_FLAG)

        canvas.restore()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        paint.color = Color.GRAY
        canvas.drawPath(bezierYAxisPath, paint)

        if (localYLabels != null && localYLabels.any())

            if (yGraphStep > 0) {
                for (i in localYLabels.indices) {

                    canvas.save()

                    //text
                    val textRect = RectF()
                    textRect.set(
                        startX - yAxisLabelWidth,
                        BASE_HEIGHT_GRAPH - i * yGraphStep, // TODO +20
                        startX,
                        BASE_HEIGHT_GRAPH - i * yGraphStep - 20
                    )
                    val textTextPaint = TextPaint()
                    textTextPaint.reset()
                    textTextPaint.flags = Paint.ANTI_ALIAS_FLAG
                    textTextPaint.color = Color.GRAY

                    textTextPaint.textSize = 27f
                    val textStaticLayout =
                        GraphStaticLayout()[textRect.width()
                            .toInt(), Layout.Alignment.ALIGN_CENTER, localYLabels[i], textTextPaint]
                    canvas.save()

                    canvas.clipRect(textRect)
                    canvas.translate(
                        textRect.left,
                        textRect.top + (textRect.height() - textStaticLayout.height) / 2f
                    )

                    textStaticLayout.draw(canvas)
                    canvas.restore()
                }
            }

        canvas.restore()
    }
}