
package com.myntra.repurpose.view.customview

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.FrameLayout
import android.widget.RelativeLayout
import java.util.concurrent.atomic.AtomicInteger

class LabelView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.textViewStyle
) : androidx.appcompat.widget.AppCompatTextView(context!!, attrs, defStyle) {
    private var _offsetx = 0f
    private var _offsety = 0f
    private var _anchorx = 0f
    private var _anchory = 0f
    private var _angel = 0f
    private var _labelViewContainerID = 0
    private val _animation: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val tran = t.matrix
            tran.postTranslate(_offsetx, _offsety)
            tran.postRotate(_angel, _anchorx, _anchory)
        }
    }

    private fun init() {
        if (layoutParams !is ViewGroup.LayoutParams) {
            val layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setLayoutParams(layoutParams)
        }

        // the default value
        //setPadding(dip2Px(40), dip2Px(2), dip2Px(40), dip2Px(2));
        _labelViewContainerID = -1
        gravity = android.view.Gravity.CENTER
        setTextColor(Color.WHITE)
        typeface = Typeface.DEFAULT_BOLD
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        setBackgroundColor(Color.BLUE)
    }

    fun setTargetView(target: View, distance: Int, gravity: Gravity) {
        if (!replaceLayout(target)) {
            return
        }
        val d = dip2Px(distance.toFloat())
        val vto = viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeGlobalOnLayoutListener(this)
                calcOffset(measuredWidth, d, gravity, target.measuredWidth, false)
            }
        })
    }

    fun setTargetViewInBaseAdapter(
        target: View?,
        targetWidth: Int,
        distance: Int,
        gravity: Gravity
    ) {
        if (!replaceLayout(target)) {
            return
        }
        //measure(0, 0);
        //calcOffset(getMeasuredWidth(), distance, gravity, targetWidth, true);
        calcOffset(dip2Px(targetWidth.toFloat()), distance, gravity, targetWidth, true)
    }

    fun remove() {
        if (parent == null || _labelViewContainerID == -1) {
            return
        }
        val frameContainer = parent as ViewGroup
        assert(frameContainer.childCount == 2)
        val target = frameContainer.getChildAt(0)
        val parentContainer = frameContainer.parent as ViewGroup
        val groupIndex = parentContainer.indexOfChild(frameContainer)
        if (frameContainer.parent is RelativeLayout) {
            for (i in 0 until parentContainer.childCount) {
                if (i == groupIndex) {
                    continue
                }
                val view = parentContainer.getChildAt(i)
                val para = view.layoutParams as RelativeLayout.LayoutParams
                for (j in para.rules.indices) {
                    if (para.rules[j] == _labelViewContainerID) {
                        para.rules[j] = target.id
                    }
                }
                view.layoutParams = para
            }
        }
        val frameLayoutParam = frameContainer.layoutParams
        target.layoutParams = frameLayoutParam
        parentContainer.removeViewAt(groupIndex)
        frameContainer.removeView(target)
        frameContainer.removeView(this)
        parentContainer.addView(target, groupIndex)
        _labelViewContainerID = -1
    }

    @SuppressLint("NewApi")
    private fun replaceLayout(target: View?): Boolean {
        if (parent != null || target == null || target.parent == null || _labelViewContainerID != -1) {
            return false
        }
        val parentContainer = target.parent as ViewGroup
        if (target.parent is FrameLayout) {
            (target.parent as FrameLayout).addView(this)
        } else if (target.parent is ViewGroup) {
            val groupIndex = parentContainer.indexOfChild(target)
            _labelViewContainerID = generateViewId()

            // relativeLayout need copy rule
            if (target.parent is RelativeLayout) {
                for (i in 0 until parentContainer.childCount) {
                    if (i == groupIndex) {
                        continue
                    }
                    val view = parentContainer.getChildAt(i)
                    val para = view.layoutParams as RelativeLayout.LayoutParams
                    for (j in para.rules.indices) {
                        if (para.rules[j] == target.id) {
                            para.rules[j] = _labelViewContainerID
                        }
                    }
                    view.layoutParams = para
                }
            }
            parentContainer.removeView(target)

            // new dummy layout
            val labelViewContainer = FrameLayout(context)
            val targetLayoutParam = target.layoutParams
            labelViewContainer.layoutParams = targetLayoutParam
            target.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )

            // add target and label in dummy layout
            labelViewContainer.addView(target)
            labelViewContainer.addView(this)
            labelViewContainer.id = _labelViewContainerID

            // add dummy layout in parent container
            parentContainer.addView(labelViewContainer, groupIndex, targetLayoutParam)
        }
        return true
    }

    private fun calcOffset(
        labelWidth: Int,
        distance: Int,
        gravity: Gravity,
        targetWidth: Int,
        isDP: Boolean
    ) {
        val d = dip2Px(distance.toFloat())
        val tw = if (isDP) dip2Px(targetWidth.toFloat()) else targetWidth
        val edge = ((labelWidth - 2 * d) / (2 * 1.414)).toFloat()
        if (gravity == Gravity.LEFT_TOP) {
            _anchorx = -edge
            _offsetx = _anchorx
            _angel = -45f
        } else if (gravity == Gravity.RIGHT_TOP) {
            _offsetx = tw + edge - labelWidth
            _anchorx = tw + edge
            _angel = 45f
        }
        _anchory = (1.414 * d + edge).toFloat()
        _offsety = _anchory
        clearAnimation()
        startAnimation(_animation)
    }

    private fun dip2Px(dip: Float): Int {
        return (dip * context.resources.displayMetrics.density + 0.5f).toInt()
    }

    enum class Gravity {
        LEFT_TOP, RIGHT_TOP
    }

    companion object {
        private val sNextGeneratedId = AtomicInteger(1)
        fun generateViewId(): Int {
            while (true) {
                val result = sNextGeneratedId.get()
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                var newValue = result + 1
                if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result
                }
            }
        }
    }

    init {
        init()
        _animation.fillBefore = true
        _animation.fillAfter = true
        _animation.isFillEnabled = true
    }
}