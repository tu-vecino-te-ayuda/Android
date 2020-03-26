package org.tuvecinoteayuda.core.ui

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

abstract class LifecycleAdapter<VH : LifecycleViewHolder> : RecyclerView.Adapter<VH>() {

    @CallSuper
    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.onAttached()
    }

    @CallSuper
    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetached()
    }

    @CallSuper
    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

}