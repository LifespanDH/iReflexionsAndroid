package com.lifespandh.irefgraphs

import android.graphics.*


object TriangularSlider : BaseDrawing() {

    const val BASE_WIDTH = 620f
    const val SLIDER_BAR_BASE_START = 5f
    const val SLIDER_BAR_BASE_END = 608f

    fun drawTriangleSlider(
        canvas: Canvas,
        targetFrame: RectF = RectF(0f, 0f, BASE_WIDTH, 110f),
        resizing: ResizingBehavior,
        progress: Float
    ) {
        // General Declarations
        val paint = Paint()

        // Local Colors
        val gradientColor38 = Color.argb(255, 170, 0, 14)
        val gradientColor35 = Color.argb(255, 173, 216, 7)
        val gradientColor36 = Color.argb(255, 138, 230, 14)
        val gradientColor39 = Color.argb(255, 212, 201, 0)
        val gradientColor37 = Color.argb(255, 214, 72, 14)
        val gradientColor40 = Color.argb(255, 147, 226, 12)

        // Local Gradients
        val lineargradient38 = GraphGradient(
            intArrayOf(
                gradientColor36,
                gradientColor40,
                gradientColor35,
                gradientColor39,
                gradientColor37,
                gradientColor38
            ), floatArrayOf(0f, 0.12f, 0.32f, 0.57f, 0.87f, 0.99f)
        )


        // Local Variables
        val progressValue = if (progress < 0f) 0f else if (progress > 100f) 100f else progress
        val yValue: Float = 86f - progressValue * 0.87f
        val height: Float = progressValue * 0.88f + 20f
        val xValue: Float = 3f + progressValue * 6f

        // Resize to Target Frame
        canvas.save()
        val resizedFrame = RectF()
        resizingBehaviorApply(
            resizing,
            RectF(0f, 0f, BASE_WIDTH, 110f),
            targetFrame,
            resizedFrame
        )
        canvas.translate(resizedFrame.left, resizedFrame.top)
        canvas.scale(resizedFrame.width() / BASE_WIDTH, resizedFrame.height() / 110f)

        // Triangular bar
        val bezierRect =  RectF()
        bezierRect[5f, 5f, 613f] = 105f
        val bezierPath = Path()
        bezierPath.reset()
        bezierPath.moveTo(602.51f, 5f)
        bezierPath.lineTo(15.49f, 86.25f)
        bezierPath.lineTo(15.49f, 86.25f)
        bezierPath.cubicTo(9.7f, 86.25f, 5f, 90.64f, 5f, 96.06f)
        bezierPath.lineTo(5f, 95.19f)
        bezierPath.lineTo(5f, 95.19f)
        bezierPath.cubicTo(5f, 100.61f, 9.7f, 105f, 15.49f, 105f)
        bezierPath.lineTo(602.51f, 105f)
        bezierPath.lineTo(602.51f, 105f)
        bezierPath.cubicTo(608.3f, 105f, 613f, 100.61f, 613f, 95.19f)
        bezierPath.lineTo(613f, 14.81f)
        bezierPath.lineTo(613f, 14.81f)
        bezierPath.cubicTo(613f, 9.39f, 608.3f, 5f, 602.51f, 5f)
        bezierPath.close()

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.shader = GraphLinearGradient()[lineargradient38, 5f, 55f, 613f, 55f]
        canvas.drawPath(bezierPath, paint)

        // Rectangle knob
        val rectangleRect = RectF()
        rectangleRect[xValue, yValue, xValue + 13f] = yValue + height
        val rectanglePath = Path()
        rectanglePath.reset()
        rectanglePath.addRoundRect(rectangleRect, 3f, 3f, Path.Direction.CW)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        canvas.drawPath(rectanglePath, paint)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.strokeWidth = 1f
        paint.strokeMiter = 10f
        canvas.save()
        paint.style = Paint.Style.STROKE
        paint.color = Color.LTGRAY
        canvas.drawPath(rectanglePath, paint)
        canvas.restore()
        canvas.restore()
    }

}