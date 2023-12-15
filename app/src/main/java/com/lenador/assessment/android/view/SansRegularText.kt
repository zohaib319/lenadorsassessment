package com.lenador.assessment.android.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * Created By Zohaib on 15/12/2023.
 */

class SansRegularText : AppCompatTextView {
    constructor(context: Context) : super(context) {
        setFont()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setFont()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setFont()
    }

    private fun setFont() {
        val font: Typeface = Typeface.createFromAsset(context.assets, "fonts/noto_sans_regular.ttf")
        setTypeface(font, Typeface.NORMAL)
    }
}