package com.lifespandh.irefgraphs

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import java.lang.ref.WeakReference
import java.util.*
import kotlin.math.abs


/**
 * Class to contain methods for custom view drawings resizing, shading and gradient colors.
 */
abstract class BaseDrawing {

    object GlobalCache {
        internal var blendModeSourceIn = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        internal var blendModeDestinationOut = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)

        var typeface: Typeface? = null
    }

    // Resizing Behavior
    enum class ResizingBehavior {
        AspectFit, //!< The content is proportionally resized to fit into the target rectangle.
        AspectFill, //!< The content is proportionally resized to completely fill the target rectangle.
        Stretch, //!< The content is stretched to match the entire target rectangle.
        Center
        //!< The content is centered in the target rectangle, but it is NOT resized.
    }

    // Resizing Behavior
    protected fun resizingBehaviorApply(
        behavior: ResizingBehavior,
        rect: RectF,
        target: RectF?,
        result: RectF
    ) {
        if (rect == target || target == null) {
            result.set(rect)
            return
        }

        if (behavior == ResizingBehavior.Stretch) {
            result.set(target)
            return
        }

        val xRatio = abs(target.width() / rect.width())
        val yRatio = abs(target.height() / rect.height())
        var scale = 0f

        when (behavior) {
            ResizingBehavior.AspectFit -> {
                scale = Math.min(xRatio, yRatio)
            }
            ResizingBehavior.AspectFill -> {
                scale = Math.max(xRatio, yRatio)
            }
            ResizingBehavior.Center -> {
                scale = 1f
            }
            else -> {}
        }

        val newWidth = Math.abs(rect.width() * scale)
        val newHeight = Math.abs(rect.height() * scale)
        result.set(
                target.centerX() - newWidth / 2,
                target.centerY() - newHeight / 2,
                target.centerX() + newWidth / 2,
                target.centerY() + newHeight / 2
        )
    }
}
 class GraphColor : Color() {
    companion object {
        private fun colorToHSV(originalColor: Int): FloatArray {
            val hsv = FloatArray(3)
            RGBToHSV(red(originalColor), green(originalColor), blue(originalColor), hsv)
            return hsv
        }

        fun colorByChangingHue(originalColor: Int, newHue: Float): Int {
            val hsv = colorToHSV(originalColor)
            hsv[0] = newHue
            return HSVToColor(alpha(originalColor), hsv)
        }

        fun colorByChangingSaturation(originalColor: Int, newSaturation: Float): Int {
            val hsv = colorToHSV(originalColor)
            hsv[1] = newSaturation
            return HSVToColor(alpha(originalColor), hsv)
        }

        fun colorByChangingValue(originalColor: Int, newValue: Float): Int {
            val hsv = colorToHSV(originalColor)
            hsv[2] = newValue
            return HSVToColor(alpha(originalColor), hsv)
        }

        fun hue(color: Int): Float {
            return colorToHSV(color)[0]
        }

        fun saturation(color: Int): Float {
            return colorToHSV(color)[1]
        }

        fun brightness(color: Int): Float {
            return colorToHSV(color)[2]
        }

        fun colorByChangingAlpha(color: Int, newAlpha: Int): Int {
            return argb(newAlpha, red(color), green(color), blue(color))
        }

        fun colorByBlendingColors(c1: Int, ratio: Float, c2: Int): Int {
            return argb(
                    ((1f - ratio) * alpha(c1) + ratio * alpha(c2)).toInt(),
                    ((1f - ratio) * red(c1) + ratio * red(c2)).toInt(),
                    ((1f - ratio) * green(c1) + ratio * green(c2)).toInt(),
                    ((1f - ratio) * blue(c1) + ratio * blue(c2)).toInt()
            )
        }

        fun colorByApplyingHighlight(color: Int, ratio: Float): Int {
            return colorByBlendingColors(color, ratio, colorByChangingAlpha(WHITE, alpha(color)))
        }

        fun colorByApplyingShadow(color: Int, ratio: Float): Int {
            return colorByBlendingColors(color, ratio, colorByChangingAlpha(BLACK, alpha(color)))
        }
    }
}

 class GraphGradient(private val colors: IntArray, pos: FloatArray?) {
    private val positions: FloatArray

    init {
        var positions = pos
        if (positions == null) {
            val steps = colors.size
            positions = FloatArray(steps)
            for (i in 0 until steps)
                positions[i] = i.toFloat() / (steps - 1)
        }
        this.positions = positions
    }

    fun linearGradient(x0: Float, y0: Float, x1: Float, y1: Float): LinearGradient {
        return LinearGradient(x0, y0, x1, y1, this.colors, this.positions, Shader.TileMode.CLAMP)
    }


    fun radialGradient(
            startX: Float,
            startY: Float,
            startRadius: Float,
            endX: Float,
            endY: Float,
            endRadius: Float
    ): RadialGradient {
        val steps = this.colors.size
        val positions = FloatArray(steps)

        if (startRadius > endRadius) {
            val ratio = endRadius / startRadius
            val colors = IntArray(steps)

            for (i in 0 until steps) {
                colors[i] = this.colors[steps - i - 1]
                positions[i] = (1 - this.positions[steps - i - 1]) * (1 - ratio) + ratio
            }

            return RadialGradient(endX, endY, startRadius, colors, positions, Shader.TileMode.CLAMP)
        } else {
            val ratio = startRadius / endRadius

            for (i in 0 until steps) {
                positions[i] = this.positions[i] * (1 - ratio) + ratio
            }

            return RadialGradient(
                    startX,
                    startY,
                    endRadius,
                    this.colors,
                    positions,
                    Shader.TileMode.CLAMP
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is GraphGradient)
            return false
        val other2 = other as GraphGradient?
        return Arrays.equals(this.colors, other2!!.colors) && Arrays.equals(
                this.positions,
                other.positions
        )
    }

    override fun hashCode(): Int {
        var result = colors.contentHashCode()
        result = 31 * result + positions.contentHashCode()
        return result
    }
}

 class GraphRadialGradient {
    private var shader: RadialGradient? = null
    private var graphRadialGradient: GraphGradient? = null
    private var x0 = 0f
    private var y0 = 0f
    private var x1 = 0f
    private var y1 = 0f
    private var radius0 = 0f
    private var radius1 = 0f
    operator fun get(
        graphGradient: GraphGradient,
        x0: Float,
        y0: Float,
        radius0: Float,
        x1: Float,
        y1: Float,
        radius1: Float
    ): RadialGradient? {
        if (this.shader == null
            || this.x0 != x0
            || this.y0 != y0
            || this.radius0 != radius0
            || this.x1 != x1
            || this.y1 != y1
            || this.radius1 != radius1
            || this.graphRadialGradient != graphGradient
        ) {
            this.x0 = x0
            this.y0 = y0
            this.radius0 = radius0
            this.x1 = x1
            this.y1 = y1
            this.radius1 = radius1
            this.graphRadialGradient = graphGradient
            this.shader = graphGradient.radialGradient(x0, y0, radius0, x1, y1, radius1)
        }
        return this.shader
    }
}
 class GraphLinearGradient {
    private var shader: LinearGradient? = null
    private var graphLinearGradient: GraphGradient? = null
    private var x0 = 0f
    private var y0 = 0f
    private var x1 = 0f
    private var y1 = 0f
    operator fun get(
        graphGradient: GraphGradient,
        x0: Float,
        y0: Float,
        x1: Float,
        y1: Float
    ): LinearGradient? {
        if (this.shader == null
            || this.x0 != x0
            || this.y0 != y0
            || this.x1 != x1
            || this.y1 != y1
            || this.graphLinearGradient != graphGradient
        ) {
            this.x0 = x0
            this.y0 = y0
            this.x1 = x1
            this.y1 = y1
            this.graphLinearGradient = graphGradient
            this.shader = graphGradient.linearGradient(x0, y0, x1, y1)
        }
        return this.shader
    }
}
 class GraphStaticLayout {
    private var layout: StaticLayout? = null
    private var width: Int = 0
    private var alignment: Layout.Alignment? = null
    private var source: CharSequence? = null
    private var paint: TextPaint? = null

    operator fun get(
            width: Int,
            alignment: Layout.Alignment,
            source: CharSequence,
            paint: TextPaint
    ): StaticLayout {
        if (this.layout == null
            || this.width != width
            || this.alignment != alignment
            || this.source != source
            || this.paint != paint
        ) {
            this.width = width
            this.alignment = alignment
            this.source = source
            this.paint = paint
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.layout = StaticLayout.Builder.obtain(
                        source,
                        0,
                        source.length,
                        paint,
                        width
                ).setAlignment(alignment).build()
            } else {
                this.layout = StaticLayout(
                        source,
                        paint,
                        width,
                        alignment,
                        1f,
                        0f,
                        false
                )
            }
        }
        return this.layout!!
    }
}
 class GraphicShadow {
    var color: Int = 0
    var dx = 0f
    var dy = 0f
    private var radius = 0f
    private var blurMaskFilter: BlurMaskFilter? = null

    constructor()

    constructor(color: Int, dx: Float, dy: Float, radius: Float) {
        this[color, dx, dy, radius]
    }

    operator fun get(color: Int, dx: Float, dy: Float, radius: Float): GraphicShadow {
        this.color = color
        this.dx = dx
        this.dy = dy

        if (this.radius != radius) {
            this.blurMaskFilter = null
            this.radius = radius
        }

        return this
    }

    fun setBlurOfPaint(paint: Paint) {
        if (this.radius <= 0)
            return

        if (this.blurMaskFilter == null)
            this.blurMaskFilter = BlurMaskFilter(this.radius, BlurMaskFilter.Blur.NORMAL)

        paint.maskFilter = this.blurMaskFilter
    }
}
internal class GraphBitmap {
    var shader: BitmapShader? = null
        private set
    private var bitmap: Bitmap? = null
    var bounds: RectF? = null
        private set
    private var resource = 0
    private var context: WeakReference<Context>? = null
    operator fun get(context: Context, resource: Int): GraphBitmap {
        if (this.context == null || this.context!!.get() !== context || this.resource != resource) {
            this.context = WeakReference(context)
            this.resource = resource
            bitmap = BitmapFactory.decodeResource(context.resources, resource)
            bounds = RectF(0f, 0f, bitmap!!.width.toFloat(), bitmap!!.height.toFloat())
            shader = BitmapShader(bitmap!!, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        }
        return this
    }

    operator fun get(bitmap: Bitmap): GraphBitmap {
        if (this.bitmap != bitmap) {
            context = null
            resource = 0
            this.bitmap = bitmap
            bounds = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
            shader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        }
        return this
    }
}
