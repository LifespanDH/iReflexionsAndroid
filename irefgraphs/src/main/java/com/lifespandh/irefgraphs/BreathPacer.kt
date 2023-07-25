package com.lifespandh.irefgraphs

import android.graphics.*
import kotlin.math.ceil


object BreathPacer: BaseDrawing() {

    private object CacheForBreathPacer {
        val paint = Paint()
        val originalFrame = RectF(0f, 0f, 200f, 200f)
        val resizedFrame = RectF()
        val ovalRect = RectF()
        val ovalPath: Path = Path()
        val oval2Rect = RectF()
        val oval2Path: Path = Path()
    }

    fun drawBreathPacer(
        canvas: Canvas,
        targetFrame: RectF?,
        resizing: ResizingBehavior,
        cycle: Float,
        color: Int = Color.argb(153, 186, 241, 232),
        color2: Int = Color.argb(77, 186, 241, 232)
    ) {
        // General Declarations
        val paint: Paint = CacheForBreathPacer.paint

        // Local Variables
        val starts = 100f - cycle / 2f
        val angle = if (cycle < 1f) 89f else if (cycle > 179f) -269f else 90f - 2f * cycle

        // Resize to Target Frame
        canvas.save()
        val resizedFrame: RectF = CacheForBreathPacer.resizedFrame
        resizingBehaviorApply(
            resizing,
            CacheForBreathPacer.originalFrame,
            targetFrame,
            resizedFrame
        )
        canvas.translate(resizedFrame.left, resizedFrame.top)
        canvas.scale(resizedFrame.width() / 200f, resizedFrame.height() / 200f)

        // Oval 2
        val oval2Rect = CacheForBreathPacer.oval2Rect
        oval2Rect[10f, 10f, 190f] = 190f
        val oval2Path = CacheForBreathPacer.oval2Path
        oval2Path.reset()
        oval2Path.addArc(
            oval2Rect,
            -90f,
            90f - angle + if (-angle < -90f) 360f * ceil((angle - 90f) / 360f.toDouble())
                .toFloat() else 0f
        )

        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.strokeWidth = 20f
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeMiter = 10f
        canvas.save()
        paint.style = Paint.Style.STROKE
        paint.color = color
        canvas.drawPath(oval2Path, paint)
        canvas.restore()


        // Oval
        val ovalRect: RectF = CacheForBreathPacer.ovalRect
        ovalRect[starts, starts, starts + cycle] = starts + cycle
        val ovalPath: Path = CacheForBreathPacer.ovalPath
        ovalPath.reset()
        ovalPath.addOval(ovalRect, Path.Direction.CW)
        paint.reset()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.FILL
        paint.color = color2
        canvas.drawPath(ovalPath, paint)
        canvas.restore()
    }

}