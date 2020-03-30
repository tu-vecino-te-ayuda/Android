package org.tuvecinoteayuda.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import org.tuvecinoteayuda.core.R
import org.tuvecinoteayuda.core.databinding.RowLayoutBinding

class RowView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding: RowLayoutBinding = RowLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RowView,
            0, 0
        ).apply {

            try {
                binding.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        getResourceId(R.styleable.RowView_android_src, 0)
                    )
                )

                binding.title.text = getString(R.styleable.RowView_title)
                binding.subtitle.text = getString(R.styleable.RowView_subtitle)
            } finally {
                recycle()
            }
        }
    }
}