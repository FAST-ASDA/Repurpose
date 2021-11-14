
package com.myntra.repurpose.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

class FontText : androidx.appcompat.widget.AppCompatTextView {
    var TAG: String = javaClass.getName()
    private var mContext: Context
    private var ttfName: String? = null

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context

        // Typeface.createFromAsset doesn't work in the layout editor.
        // Skipping...
        if (isInEditMode()) {
            return
        }
        for (i in 0 until attrs.getAttributeCount()) {
            ttfName = attrs.getAttributeValue(
                Utils.ATTRIBUTE_SCHEMA,
                Utils.ATTRIBUTE_TTF_KEY
            )
            if (null != ttfName) init()
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        mContext = context
    }

    private fun init() {
        val font: Typeface? = Utils.getFonts(mContext, ttfName)
        if (null != font) typeface = font
    }

    public override fun setTypeface(tf: Typeface?) {
        super.setTypeface(tf)
    }
}