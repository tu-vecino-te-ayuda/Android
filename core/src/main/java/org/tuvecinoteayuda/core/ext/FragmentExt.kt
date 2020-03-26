package org.tuvecinoteayuda.core.ext

import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.tuvecinoteayuda.core.R

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
    context?.also {
        Toast.makeText(it, msg, Toast.LENGTH_LONG).show()
    }
}

fun Fragment.showSnackBarError(
    @StringRes msg: Int,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    showSnackBarError(getString(msg), duration)
}

fun Fragment.showSnackBarError(
    msg: String,
    duration: Int = Snackbar.LENGTH_SHORT
) {

    view?.also { view ->
        val snackbar = Snackbar.make(view, msg, duration)
        snackbar.also {
            val context = view.context

            val group = it.view as ViewGroup
            group.setBackgroundColor(ContextCompat.getColor(view.context, R.color.gray))
            ViewCompat.setElevation(
                group, context.resources.getDimensionPixelSize(
                    R
                        .dimen.app_bar_elevation
                ).toFloat()
            )
            it.show()

        }
    }
}
