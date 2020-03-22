package org.tuvecinoteayuda.view

import android.view.View
import androidx.core.widget.addTextChangedListener
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

fun TextInputLayout.removeErrorOnTyping() {
    this.editText?.addTextChangedListener { this.error = null }
}