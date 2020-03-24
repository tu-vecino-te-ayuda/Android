package org.tuvecinoteayuda.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.loading_button.view.*
import org.tuvecinoteayuda.core.R
import org.tuvecinoteayuda.core.ext.hide
import org.tuvecinoteayuda.core.ext.show

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.loading_button, this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingButton,
            0, 0
        ).apply {

            try {
                button.text = getString(R.styleable.LoadingButton_loading_button_text)
            } finally {
                recycle()
            }
        }
    }

    fun showLoading() {
        button.hide()
        loading.show()
    }

    fun hideLoading() {
        button.show()
        loading.hide()
    }

    fun setOnButtonClickListener(action: () -> Unit) {
        button.setOnClickListener { action() }
    }
}
