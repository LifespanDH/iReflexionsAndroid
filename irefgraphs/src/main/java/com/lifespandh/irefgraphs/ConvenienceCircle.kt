package com.lifespandh.irefgraphs

import android.content.Context
import android.graphics.*
import android.os.Build
import androidx.annotation.RequiresApi
import com.lifespandh.irefgraphs.BaseDrawing
import com.lifespandh.irefgraphs.GraphBitmap
import kotlin.math.ceil


object ConvenienceCircle : BaseDrawing() {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun drawConvenienceCircle(
            context: Context,
            canvas: Canvas,
            targetFrame: RectF,
            resizing: ResizingBehavior,
            backgroundColor: Int,
            valueCircle: Int,
            circleMaxValue: Int
    ) {

        val paint = Paint()
        val displayDensity = context.resources.displayMetrics.density

        // Local Colors
        val emptySegmentColor = Color.argb(255, 102, 102, 102)

        // Local Images
        val circleImage = GraphBitmap()[context, R.drawable.ring]

        // Local Variables
        val valueCircleExpression =
                if (valueCircle < 0f) 89f
                else if (valueCircle > 100f) -270f
                else 90f - valueCircle * 3.6f

        // Resize to Target Frame
        canvas.save()
        val resizedFrame = RectF()
        resizingBehaviorApply(resizing, RectF(0f, 0f, 300f, 300f), targetFrame, resizedFrame)
        canvas.translate(resizedFrame.left, resizedFrame.top)
        canvas.scale(resizedFrame.width() / 300f, resizedFrame.height() / 300f)

        // RectangleColorWheel
        val rectangleColorWheelRect = RectF()
        rectangleColorWheelRect[-1f, -1f, 299f] = 299f
        val rectangleColorWheelPath = Path()
        rectangleColorWheelPath.reset()
        rectangleColorWheelPath.addRect(rectangleColorWheelRect, Path.Direction.CW)

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.shader = circleImage.shader

        canvas.save()
        canvas.clipPath(rectangleColorWheelPath)
        canvas.translate(rectangleColorWheelRect.left, rectangleColorWheelRect.top)
        canvas.scale(1 / displayDensity, 1 / displayDensity)
        canvas.drawPaint(paint)
        canvas.restore()

        // FrameBezier
        val frameBezierRect = RectF()
        frameBezierRect[0f, 0f, 300f] = 300f
        val frameBezierPath = Path()
        frameBezierPath.reset()
        frameBezierPath.moveTo(149.5f, 3f)
        frameBezierPath.cubicTo(116.72f, 3f, 86.43f, 13.7f, 61.94f, 31.79f)
        frameBezierPath.cubicTo(25.58f, 58.65f, 2f, 101.82f, 2f, 150.5f)
        frameBezierPath.cubicTo(2f, 231.96f, 68.04f, 298f, 149.5f, 298f)
        frameBezierPath.cubicTo(230.96f, 298f, 297f, 231.96f, 297f, 150.5f)
        frameBezierPath.cubicTo(297f, 69.04f, 230.96f, 3f, 149.5f, 3f)
        frameBezierPath.close()
        frameBezierPath.moveTo(300f, 0f)
        frameBezierPath.cubicTo(300f, 0f, 300f, 300f, 300f, 300f)
        frameBezierPath.lineTo(0f, 300f)
        frameBezierPath.lineTo(0f, 0f)
        frameBezierPath.lineTo(300f, 0f)
        frameBezierPath.lineTo(300f, 0f)
        frameBezierPath.close()

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = backgroundColor
        canvas.drawPath(frameBezierPath, paint)

         // InnerOval
        val innerOvalRect = RectF()
        innerOvalRect[32f, 33f, 267f] = 268f
        val innerOvalPath = Path()
        innerOvalPath.reset()
        innerOvalPath.addOval(innerOvalRect, Path.Direction.CW)

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = backgroundColor
        canvas.drawPath(innerOvalPath, paint)

        // SliderOval
        val sliderOvalRect = RectF()
        sliderOvalRect[17f, 18f, 282f] = 283f
        val sliderOvalPath = Path()
        sliderOvalPath.reset()
        sliderOvalPath.addArc(
                sliderOvalRect,
                -valueCircleExpression,
                valueCircleExpression - 90f
                        + if (-90f < -valueCircleExpression) 360f * ceil(((90f - valueCircleExpression) / 360f)
                        .toDouble()).toFloat() else 0f)

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.strokeWidth = 30f
        paint.strokeMiter = 10f
        canvas.save()
        paint.style = Paint.Style.STROKE
        paint.color = emptySegmentColor
        canvas.drawPath(sliderOvalPath, paint)
        canvas.restore()

        canvas.restore()
    }
}
