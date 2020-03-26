package org.tuvecinoteayuda.core.ui

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView

@Suppress("LeakingThis")
abstract class LifecycleViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView), LifecycleOwner {

    private var lifecycleRegistry: LifecycleRegistry

    init {
        lifecycleRegistry = createLifeCycle()
    }

    @CallSuper
    open fun onAttached() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    @CallSuper
    open fun onDetached() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    @CallSuper
    open fun onRecycled() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        lifecycleRegistry = createLifeCycle()
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    private fun createLifeCycle(): LifecycleRegistry {
        val lifecycle = LifecycleRegistry(this)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        return lifecycle
    }
}
