package com.lifespandh.irefgraphs

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Layout
import android.text.TextPaint
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import java.util.*
import kotlin.math.ceil


data class CheckinObject(
    val orderNumber: Int,
    val color: Int,
    val text: String,
    var isSelected: Boolean = false
)

object CheckinCircle : BaseDrawing() {

    private val paint = Paint()

    @RequiresApi(Build.VERSION_CODES.Q)
    fun drawCheckInWheel(
        context: Context,
        canvas: Canvas,
        targetFrame: RectF = RectF(0f, 0f, 200f, 200f),
        resizing: ResizingBehavior,
        checkinObjects: List<CheckinObject>,
        isCategory: Boolean = false
    ) {

        GlobalCache.typeface = ResourcesCompat.getFont(context,R.font.montserrat_bold)
        // General Declarations
        val currentTransformation: Stack<Matrix> = Stack<Matrix>()
        currentTransformation.push(Matrix())

        // Resize to Target Frame
        canvas.save()
        val resizedFrame = RectF()
        resizingBehaviorApply(
            resizing,
            RectF(0f, 0f, 200f, 200f),
            targetFrame,
            resizedFrame
        )

        canvas.apply {
            translate(resizedFrame.left, resizedFrame.top)
            scale(resizedFrame.width() / 200f, resizedFrame.height() / 200f)
        }

        // we need to display selected sectors on top of non-selected
        val checkinObjectsSorted = checkinObjects.sortedBy {
            it.isSelected
        }

        for(checkinObj in checkinObjectsSorted) {
            when(checkinObj.orderNumber) {
                0 -> drawSector0(checkinObjects[0], canvas, currentTransformation, isCategory)
                1 -> drawSector1(checkinObjects[1], canvas, currentTransformation, isCategory)
                2 -> drawSector2(checkinObjects[2], canvas, currentTransformation, isCategory)
                3 -> drawSector3(checkinObjects[3], canvas, currentTransformation, isCategory)
                4 -> drawSector4(checkinObjects[4], canvas, currentTransformation, isCategory)
                5 -> drawSector5(checkinObjects[5], canvas, currentTransformation, isCategory)
            }
        }
    }

    private fun drawSector0(
        checkinObject: CheckinObject,
        canvas: Canvas,
        currentTransformation: Stack<Matrix>,
        isCategory: Boolean
    ) {
        // Oval 0
        val oval0Rect = RectF()
        if (checkinObject.isSelected && isCategory) {
            oval0Rect[8f, -1f, 198f] = 189f
        } else {
            oval0Rect[5f, 5f, 195f] = 195f
        }
        val oval0Path = Path()
        oval0Path.reset()
        oval0Path.addArc(oval0Rect, -90f, 60f)
        oval0Path.lineTo(oval0Rect.centerX(), oval0Rect.centerY())
        oval0Path.close()
        fillAndStrokeShape(checkinObject, canvas, oval0Path, isCategory)

        // Text2
        canvas.save()
        canvas.translate(126.66f, 52.82f)
        currentTransformation.peek().postTranslate(126.66f, 52.82f)
        canvas.rotate(-60f)
        currentTransformation.peek().postRotate(-60f)
        applyTextAttributes(checkinObject, canvas, isCategory)
    }

    private fun drawSector1(
        checkinObject: CheckinObject,
        canvas: Canvas,
        currentTransformation: Stack<Matrix>,
        isCategory: Boolean
    ) {
        // oval 1
        val oval1Rect = RectF()

        if (checkinObject.isSelected && isCategory) {
            oval1Rect[11f, 5f, 201f] = 195f
        } else {
            oval1Rect[5f, 5f, 195f] = 195f
        }
        val oval1Path = Path()
        oval1Path.reset()
        oval1Path.addArc(oval1Rect, -30f, 60f)
        oval1Path.lineTo(oval1Rect.centerX(), oval1Rect.centerY())
        oval1Path.close()
        fillAndStrokeShape(checkinObject, canvas, oval1Path, isCategory)

        // Text1
        canvas.save()
        canvas.translate(154f, 100f)
        currentTransformation.peek().postTranslate(154f, 100f)
        applyTextAttributes(checkinObject, canvas, isCategory)
    }

    private fun drawSector2(
        checkinObject: CheckinObject,
        canvas: Canvas,
        currentTransformation: Stack<Matrix>,
        isCategory: Boolean
    ) {
        // oval 2
        val oval2Rect = RectF()
        if (checkinObject.isSelected && isCategory) {
            oval2Rect[8f, 10f, 198f] = 200f
        } else {
            oval2Rect[5f, 5f, 195f] = 195f
        }

        val oval2Path = Path()
        oval2Path.reset()
        oval2Path.addArc(oval2Rect, 30f, 60f)
        oval2Path.lineTo(oval2Rect.centerX(), oval2Rect.centerY())
        oval2Path.close()
        fillAndStrokeShape(checkinObject, canvas, oval2Path, isCategory) //

        // Text2
        canvas.save()
        canvas.translate(126.66f, 147.82f)
        currentTransformation.peek().postTranslate(126.66f, 147.82f)
        canvas.rotate(60f)
        currentTransformation.peek().postRotate(60f)
        applyTextAttributes(checkinObject, canvas, isCategory)
    }

    private fun drawSector3(
        checkinObject: CheckinObject,
        canvas: Canvas,
        currentTransformation: Stack<Matrix>,
        isCategory: Boolean
    ) {
        // oval 3
        val oval3Rect = RectF()
        if (checkinObject.isSelected && isCategory) {
            oval3Rect[2f, 10f, 192f] = 200f
        } else {
            oval3Rect[5f, 5f, 195f] = 195f
        }

        val oval3Path = Path()
        oval3Path.reset()
        oval3Path.addArc(oval3Rect, 90f, 60f)
        oval3Path.lineTo(oval3Rect.centerX(), oval3Rect.centerY())
        oval3Path.close()
        fillAndStrokeShape(checkinObject, canvas, oval3Path, isCategory) //

        // Text3
        canvas.save()
        canvas.translate(69f, 148f)
        currentTransformation.peek().postTranslate(69f, 148f)
        canvas.rotate(-60f)
        currentTransformation.peek().postRotate(-60f)
        applyTextAttributes(checkinObject, canvas, isCategory)
    }

    private fun drawSector4(
        checkinObject: CheckinObject,
        canvas: Canvas,
        currentTransformation: Stack<Matrix>,
        isCategory: Boolean
    ) {
        // oval 4
        val oval4Rect = RectF()
        if (checkinObject.isSelected && isCategory) {
            oval4Rect[0f, 5f, 190f] = 195f
        } else {
            oval4Rect[5f, 5f, 195f] = 195f
        }

        val oval4Path = Path()
        oval4Path.reset()
        oval4Path.addArc(oval4Rect, 150f, 360f * ceil(300f / 360f.toDouble()).toFloat() - 300f)
        oval4Path.lineTo(oval4Rect.centerX(), oval4Rect.centerY())
        oval4Path.close()
        fillAndStrokeShape(checkinObject, canvas, oval4Path, isCategory) //

        // Text4
        canvas.save()
        canvas.translate(47f, 99f)
        currentTransformation.peek().postTranslate(47f, 99f)
        applyTextAttributes(checkinObject, canvas, isCategory)
    }

    private fun drawSector5(
        checkinObject: CheckinObject,
        canvas: Canvas,
        currentTransformation: Stack<Matrix>,
        isCategory: Boolean
    ) {
        // oval 5
        val oval5Rect = RectF()
        if (checkinObject.isSelected && isCategory) {
            oval5Rect[2f, 0f, 192f] = 190f
        } else {
            oval5Rect[5f, 5f, 195f] = 195f
        }

        val oval5Path = Path()
        oval5Path.reset()
        oval5Path.addArc(oval5Rect, -150f, 60f)
        oval5Path.lineTo(oval5Rect.centerX(), oval5Rect.centerY())
        oval5Path.close()
        fillAndStrokeShape(checkinObject, canvas, oval5Path, isCategory) //

        // Text5
        canvas.save()
        canvas.translate(73.66f, 52.82f)
        currentTransformation.peek().postTranslate(73.66f, 52.82f)
        canvas.rotate(60f)
        currentTransformation.peek().postRotate(60f)
        applyTextAttributes(checkinObject, canvas, isCategory)
    }

    private fun fillAndStrokeShape(
        checkinObject: CheckinObject,
        canvas: Canvas,
        bezierPath: Path,
        isCategory: Boolean
    ) {
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = checkinObject.color
        canvas.drawPath(bezierPath, paint)

        if (checkinObject.isSelected && !isCategory) { //
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.strokeWidth = 4f
            paint.strokeMiter = 10f
            canvas.save()
            paint.style = Paint.Style.STROKE
            paint.color = Color.WHITE
            canvas.drawPath(bezierPath, paint)
            canvas.restore()
        }
    }

    private fun applyTextAttributes(
        checkinObject: CheckinObject,
        canvas: Canvas,
        isCategory: Boolean
    ) {
        val textRect = RectF()
        textRect[-37f, -10f, 37f] = 10f
        val textPaint = TextPaint()
        textPaint.reset()
        textPaint.flags = Paint.ANTI_ALIAS_FLAG
        textPaint.color = Color.WHITE

        textPaint.typeface = GlobalCache.typeface
        val fontSize = if (checkinObject.text.length < 7) 15f else if (checkinObject.text.length < 10) 12f else 10f
        textPaint.textSize = fontSize
        if (checkinObject.isSelected && isCategory) { //
            textPaint.color = Color.BLACK
        }
        val text1StaticLayout = GraphStaticLayout()[textRect.width()
            .toInt(), Layout.Alignment.ALIGN_CENTER, checkinObject.text, textPaint]
        canvas.save()
        canvas.clipRect(textRect)
        canvas.translate(
            textRect.left,
            textRect.top + (textRect.height() - text1StaticLayout.height) / 2f
        )
        text1StaticLayout.draw(canvas)
        canvas.restore()
        canvas.restore()
    }
}
