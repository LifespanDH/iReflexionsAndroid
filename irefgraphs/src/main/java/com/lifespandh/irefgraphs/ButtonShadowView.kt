package com.lifespandh.irefgraphs

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.lifespandh.irefgraphs.BaseDrawing
import com.lifespandh.irefgraphs.ButtonShadow
import com.lifespandh.irefgraphs.ButtonType

class ButtonShadowView: View {

    var isPushed = false
    var isSelected: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var buttonColor: Int = Color.argb(255, 63, 120, 183)
    var buttonType: ButtonType = ButtonType.Button3to1

//    private var _text: String = ""

    var text: String = ""
//        get() = _text
//        set(value) {
//            _text = value
//        }

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

//        _text = a.getString(
//            styleable.PushButton_text
//        )!!

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

        if (buttonType == ButtonType.Circle) {
            ButtonShadow.drawCircleOut(
                canvas,
                getDrawingFrame(),
                BaseDrawing.ResizingBehavior.AspectFit,
                buttonColor,
                isPushed
            )
        } else {
            ButtonShadow.drawButtonOut(
                context,
                canvas,
                getDrawingFrame(),
                BaseDrawing.ResizingBehavior.AspectFit,
                buttonColor,
                text,
                buttonType,
                isPushed
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        isSelected.value = isPushed
        invalidate()
        return super.onTouchEvent(event)
    }

}