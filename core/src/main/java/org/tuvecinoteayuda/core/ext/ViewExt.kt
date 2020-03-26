package org.tuvecinoteayuda.core.ext

import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.core.view.postDelayed
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

fun View?.showOrHide(show: Boolean) {
    this?.visibility = if (show) View.VISIBLE else View.GONE
}

fun TextInputLayout?.removeErrorOnTyping() {
    this?.editText?.addTextChangedListener { this.error = null }
}

fun TextInputEditText?.setMaxLength(maxLength: Int) {
    this?.filters = arrayOf<InputFilter>(LengthFilter(maxLength))
}

fun AutoCompleteTextView.clearOnFocus() {
    setOnClickListener {
        text.clear()
        postDelayed(200) { showDropDown() }
    }
}
