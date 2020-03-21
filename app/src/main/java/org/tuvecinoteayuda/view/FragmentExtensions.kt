package org.tuvecinoteayuda.view

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showToast(
    @StringRes msg: Int,
    duration: Int = Toast.LENGTH_LONG
) {
    showToast(getString(msg), duration)
}

fun Fragment.showToast(
    msg: String,
    duration: Int = Toast.LENGTH_LONG
) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
}
