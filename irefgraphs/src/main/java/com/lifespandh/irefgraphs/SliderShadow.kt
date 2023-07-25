package com.lifespandh.irefgraphs

import android.content.Context
import android.graphics.*
import android.os.Build
import androidx.annotation.RequiresApi
import com.lifespandh.irefgraphs.BaseDrawing
import com.lifespandh.irefgraphs.GraphicShadow


object SliderShadow : BaseDrawing() {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun drawSliderShadow(
            context: Context,
            canvas: Canvas,
            targetFrame: RectF,
            resizing: ResizingBehavior,
            length: Float,
            sliderCanvasColor: Int,
            sliderValueBarColor: Int,
            sliderValue: Int
    ) {

        GlobalCache.typeface = context.resources.getFont(R.font.montserrat_regular)

        val paint = Paint()
        val canvasLength = length + 6

        // Local Colors
        val shadowColorValueBarLight = Color.argb(96, 255, 255, 255)
        val shadowColorValueBarDark = Color.argb(128, 0, 0, 0)

        // Local Shadows
        val canvasBarShadowLight = GraphicShadow()[shadowColorValueBarLight, -4f, -4f, 4f]
        val canvasBarShadowDark = GraphicShadow()[shadowColorValueBarDark, 6f, 6f, 10f]

        val shadowValueBarLight = GraphicShadow()[shadowColorValueBarLight, 3f, 3f, 5f]
        val shadowValueBarDark = GraphicShadow()[shadowColorValueBarDark, -3f, -3f, 5f]

        // Local Variables
        val multiplier = length / 100
        val sliderMove =
            if (sliderValue < 0f) 0f
            else if (sliderValue * multiplier > length) length
            else sliderValue * multiplier

        // Resize to Target Frame
        canvas.save()
        val resizedFrame = RectF()
        resizingBehaviorApply(
                resizing,
                RectF(0f, 0f, canvasLength, 50f),
                targetFrame,
                resizedFrame
        )
        canvas.translate(resizedFrame.left, resizedFrame.top)
        canvas.scale(resizedFrame.width() / canvasLength, resizedFrame.height() / 50f)

        // Rectangle canvas
        val rectangleRect = RectF()
        rectangleRect[0f, 0f, canvasLength] = 50f
        val rectanglePath = Path()
        rectanglePath.reset()
        rectanglePath.addRoundRect(rectangleRect, 6f, 6f, Path.Direction.CW)

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = sliderCanvasColor
        canvas.drawPath(rectanglePath, paint)

        // inner upper left dark shadow
        canvas.saveLayerAlpha(null, 255)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = canvasBarShadowDark.color
        canvas.drawPath(rectanglePath, paint)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.xfermode = GlobalCache.blendModeDestinationOut
        canvas.saveLayer(null, paint)

        canvas.translate(canvasBarShadowDark.dx, canvasBarShadowDark.dy)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = Color.WHITE
        canvasBarShadowDark.setBlurOfPaint(paint)
        canvas.drawPath(rectanglePath, paint)
        canvas.restore()

        // inner lower right dark shadow
        canvas.saveLayerAlpha(null, 255)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = canvasBarShadowLight.color
        canvas.drawPath(rectanglePath, paint)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.xfermode = GlobalCache.blendModeDestinationOut
        canvas.saveLayer(null, paint)

        canvas.translate(canvasBarShadowLight.dx, canvasBarShadowLight.dy)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = Color.WHITE
        canvasBarShadowLight.setBlurOfPaint(paint)
        canvas.drawPath(rectanglePath, paint)
        canvas.restore()

        canvas.restore()

        // Rectangle value bar
        val rectangle2Rect = RectF()
        rectangle2Rect[3f, 3f, 3f + sliderMove] = 47f
        val rectangle2Path = Path()
        rectangle2Path.reset()
        rectangle2Path.addRoundRect(rectangle2Rect, 6f, 6f, Path.Direction.CW)

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = sliderValueBarColor
        canvas.drawPath(rectangle2Path, paint)

        // inner upper left light shadow
        canvas.saveLayerAlpha(null, 255)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = shadowValueBarLight.color
        canvas.drawPath(rectangle2Path, paint)

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.xfermode = GlobalCache.blendModeDestinationOut
        canvas.saveLayer(null, paint)

        canvas.translate(shadowValueBarLight.dx, shadowValueBarLight.dy)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = Color.WHITE
        shadowValueBarLight.setBlurOfPaint(paint)
        canvas.drawPath(rectangle2Path, paint)
        canvas.restore()

        // inner lower right dark shadow
        canvas.saveLayerAlpha(null, 255)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = shadowValueBarDark.color
        canvas.drawPath(rectangle2Path, paint)

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.xfermode = GlobalCache.blendModeDestinationOut
        canvas.saveLayer(null, paint)

        canvas.translate(shadowValueBarDark.dx, shadowValueBarDark.dy)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = Color.WHITE
        shadowValueBarDark.setBlurOfPaint(paint)
        canvas.drawPath(rectangle2Path, paint)
        canvas.restore()

        canvas.restore()
        canvas.restore()
    }
}
