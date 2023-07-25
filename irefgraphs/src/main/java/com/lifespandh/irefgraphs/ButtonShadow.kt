package com.lifespandh.irefgraphs

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Layout
import android.text.TextPaint
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat


enum class ButtonType {
    Button3to1,
    Button2to1,
    Circle
}

object ButtonShadow : BaseDrawing() {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun drawButtonOut(
        context: Context,
        canvas: Canvas,
        targetFrame: RectF,
        resizing: ResizingBehavior,
        buttonColor: Int,
        text: String,
        buttonType: ButtonType = ButtonType.Circle,
        isPushed: Boolean = false
    ) {

        GlobalCache.typeface = ResourcesCompat.getFont(context,R.font.montserrat_regular)

        val buttonHeight = if (buttonType == ButtonType.Button2to1) 150f else 100f

        // General Declarations
        val paint = Paint()

        val shadowLightColor = Color.argb(84, 246, 247, 243)
        val shadowDarkColor = Color.argb(128, 0, 0, 0)

        val shadowLightColorIn = Color.argb(128, 223, 223, 223)
        val shadowDarkColorIn = Color.argb(128, 63, 63, 63)


        // Local Shadows
        val shadowDark = GraphicShadow()[shadowDarkColor, -3f, -3f, 9f]
        val shadowLight = GraphicShadow()[shadowLightColor, 3f, 3f, 5f]

        val shadowDarkIn = GraphicShadow()[shadowDarkColor, 3f, 3f, 9f]
        val shadowLightIn = GraphicShadow()[shadowLightColor, -3f, -3f, 5f]

        // Resize to Target Frame
        canvas.save()
        val resizedFrame = RectF()
        resizingBehaviorApply(
            resizing,
            RectF(0f, 0f, 300f, buttonHeight),
            targetFrame,
            resizedFrame
        )
        canvas.translate(resizedFrame.left, resizedFrame.top)
        canvas.scale(resizedFrame.width() / 300f, resizedFrame.height() / buttonHeight)

        // Rectangle
        val rectangle3Rect = RectF()
        rectangle3Rect[10f, 10f, 290f] = buttonHeight - 10f
        val rectangle3Path = Path()
        rectangle3Path.reset()
        rectangle3Path.addRoundRect(rectangle3Rect, 20f, 20f, Path.Direction.CW)

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = buttonColor
        canvas.drawPath(rectangle3Path, paint)

//        if (!isPushed) {
//            //outer dark bottom right shadow
//            canvas.saveLayerAlpha(null, 255)
//            canvas.translate(shadowDark.dx, shadowDark.dy)
//            val shadowPaint = Paint()
//            shadowPaint.set(paint)
//            shadowDark.setBlurOfPaint(shadowPaint)
//            canvas.drawPath(rectangle3Path, shadowPaint)
//            shadowPaint.xfermode = GlobalCache.blendModeSourceIn
//            canvas.saveLayer(null, shadowPaint)
//            canvas.drawColor(shadowDark.color)
//            canvas.restore()
//
//            //outer light top left shadow
//            canvas.saveLayerAlpha(null, 255)
//            canvas.translate(shadowLight.dx, shadowLight.dy)
//            val shadowPaint3 = Paint()
//            shadowPaint3.set(paint)
//            shadowLight.setBlurOfPaint(shadowPaint3)
//            canvas.drawPath(rectangle3Path, shadowPaint3)
//            shadowPaint3.xfermode = GlobalCache.blendModeSourceIn
//            canvas.saveLayer(null, shadowPaint3)
//            canvas.drawColor(shadowLight.color)
//            canvas.restore()
//        }

        if (isPushed) {
            //inner top left dark shadow
            canvas.saveLayerAlpha(null, 255)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = shadowDarkIn.color
            canvas.drawPath(rectangle3Path, paint)

            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.xfermode = GlobalCache.blendModeDestinationOut
            canvas.saveLayer(null, paint)

            canvas.translate(shadowDarkIn.dx, shadowDarkIn.dy)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = Color.WHITE
            shadowDarkIn.setBlurOfPaint(paint)
            canvas.drawPath(rectangle3Path, paint)
            canvas.restore()

            //inner bottom right light shadow
            canvas.saveLayerAlpha(null, 255)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = shadowLightIn.color
            canvas.drawPath(rectangle3Path, paint)

            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.xfermode = GlobalCache.blendModeDestinationOut
            canvas.saveLayer(null, paint)

            canvas.translate(shadowLightIn.dx, shadowLightIn.dy)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = Color.WHITE
            shadowLightIn.setBlurOfPaint(paint)
            canvas.drawPath(rectangle3Path, paint)
            canvas.restore()
        } else {

            //inner bottom right dark shadow
            canvas.saveLayerAlpha(null, 255)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = shadowDark.color
            canvas.drawPath(rectangle3Path, paint)

            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.xfermode = GlobalCache.blendModeDestinationOut
            canvas.saveLayer(null, paint)

            canvas.translate(shadowDark.dx, shadowDark.dy)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = Color.WHITE
            shadowDark.setBlurOfPaint(paint)
            canvas.drawPath(rectangle3Path, paint)
            canvas.restore()

            //inner top left light shadow
            canvas.saveLayerAlpha(null, 255)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = shadowLight.color
            canvas.drawPath(rectangle3Path, paint)

            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.xfermode = GlobalCache.blendModeDestinationOut
            canvas.saveLayer(null, paint)

            canvas.translate(shadowLight.dx, shadowLight.dy)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = Color.WHITE
            shadowLight.setBlurOfPaint(paint)
            canvas.drawPath(rectangle3Path, paint)
            canvas.restore()
        }
        canvas.restore()

        val textRect = RectF()
        textRect[0f, 0f, 280f] = buttonHeight
        val textPaint = TextPaint()
        textPaint.reset()
        textPaint.flags = Paint.ANTI_ALIAS_FLAG
        textPaint.color = Color.WHITE

        textPaint.typeface = GlobalCache.typeface
        textPaint.textSize = if (text.length < 11) 35f else 28f
        if (isPushed) {
            textPaint.color = Color.BLACK
        }
        val text1StaticLayout = GraphStaticLayout()[textRect.width()
            .toInt(), Layout.Alignment.ALIGN_CENTER, text, textPaint]
        canvas.save()
        canvas.clipRect(textRect)
        canvas.translate(
            textRect.left,
            textRect.top + (textRect.height() - text1StaticLayout.height) / 2f
        )
        text1StaticLayout.draw(canvas)
        canvas.restore()
    }


    fun drawCircleOut(
        canvas: Canvas,
        targetFrame: RectF,
        resizing: ResizingBehavior,
        buttonColor: Int,
        isPushed: Boolean = false
    ) {
        // General Declarations
        val paint = Paint()

        // Local Colors
        val shadowDarkColor = Color.argb(215, 0, 0, 0)
        val shadowLightColor = Color.argb(84, 246, 247, 243)

        // Local Shadows
        val shadowDark = GraphicShadow()[shadowDarkColor, -5f, -5f, 9f]
        val shadowLight = GraphicShadow()[shadowLightColor, 5f, 5f, 5f]

        val shadowDarkIn = GraphicShadow()[shadowDarkColor, 5f, 5f, 9f]
        val shadowLightIn = GraphicShadow()[shadowLightColor, -5f, -5f, 5f]

        // Resize to Target Frame
        canvas.save()
        canvas.save()
        val resizedFrame = RectF()
        resizingBehaviorApply(
            resizing,
            RectF(0f, 0f, 200f, 200f),
            targetFrame,
            resizedFrame
        )

        canvas.translate(resizedFrame.left, resizedFrame.top)
        canvas.scale(resizedFrame.width() / 200f, resizedFrame.height() / 200f)

        // Oval
        val ovalRect = RectF()
        ovalRect[5f, 5f, 195f] = 195f
        val ovalPath = Path()
        ovalPath.reset()
        ovalPath.addOval(ovalRect, Path.Direction.CW)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = buttonColor
        canvas.drawPath(ovalPath, paint)


        if (isPushed) {
            //inner top left dark shadow
            canvas.saveLayerAlpha(null, 255)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = shadowDarkIn.color
            canvas.drawPath(ovalPath, paint)

            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.xfermode = GlobalCache.blendModeDestinationOut
            canvas.saveLayer(null, paint)

            canvas.translate(shadowDarkIn.dx, shadowDarkIn.dy)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = Color.WHITE
            shadowDarkIn.setBlurOfPaint(paint)
            canvas.drawPath(ovalPath, paint)
            canvas.restore()

            //inner bottom right light shadow
            canvas.saveLayerAlpha(null, 255)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = shadowLightIn.color
            canvas.drawPath(ovalPath, paint)

            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.xfermode = GlobalCache.blendModeDestinationOut
            canvas.saveLayer(null, paint)

            canvas.translate(shadowLightIn.dx, shadowLightIn.dy)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = Color.WHITE
            shadowLightIn.setBlurOfPaint(paint)
            canvas.drawPath(ovalPath, paint)
            canvas.restore()
        } else {

            //inner bottom right dark shadow
            canvas.saveLayerAlpha(null, 255)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = shadowDark.color
            canvas.drawPath(ovalPath, paint)

            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.xfermode = GlobalCache.blendModeDestinationOut
            canvas.saveLayer(null, paint)

            canvas.translate(shadowDark.dx, shadowDark.dy)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = Color.WHITE
            shadowDark.setBlurOfPaint(paint)
            canvas.drawPath(ovalPath, paint)
            canvas.restore()

            //inner top left light shadow
            canvas.saveLayerAlpha(null, 255)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = shadowLight.color
            canvas.drawPath(ovalPath, paint)

            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.xfermode = GlobalCache.blendModeDestinationOut
            canvas.saveLayer(null, paint)

            canvas.translate(shadowLight.dx, shadowLight.dy)
            paint.reset()
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.color = Color.WHITE
            shadowLight.setBlurOfPaint(paint)
            canvas.drawPath(ovalPath, paint)
            canvas.restore()
        }

        canvas.restore()
        canvas.restore()
    }

}
