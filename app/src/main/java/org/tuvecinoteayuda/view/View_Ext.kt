package org.tuvecinoteayuda.view

import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun View?.hide() {
    this?.visibility = View.GONE
}

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun TextInputLayout?.removeErrorOnTyping() {
    this?.editText?.addTextChangedListener { this.error = null }
}

fun TextInputEditText?.setMaxLenght(maxLength: Int) {
    this?.filters = arrayOf<InputFilter>(LengthFilter(maxLength))
}

