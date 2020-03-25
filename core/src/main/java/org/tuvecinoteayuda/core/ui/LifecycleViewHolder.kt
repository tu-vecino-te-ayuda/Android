package org.tuvecinoteayuda.core.ui

import android.view.View
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView

@Suppress("LeakingThis")
abstract class LifecycleViewHolder(
    itemView: View,
    parent: Lifecycle
) : RecyclerView.ViewHolder(itemView), LifecycleOwner {

    private val lifecycleRegistry = RecyclerViewLifecycleRegistry(this, parent)

    fun onAppear() {
        lifecycleRegistry.highestState = Lifecycle.State.RESUMED
    }

    fun onDisappear() {
        lifecycleRegistry.highestState = Lifecycle.State.CREATED
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}

private class RecyclerViewLifecycleRegistry(
    owner: LifecycleOwner,
    private val parent: Lifecycle
) : LifecycleRegistry(owner) {

    @Suppress("unused")
    private val parentLifecycleObserver = object : LifecycleObserver {
        @OnLifecycleEvent(Event.ON_ANY)
        fun onAny() {
            this@RecyclerViewLifecycleRegistry.currentState = parent.currentState
        }
    }

    var highestState = State.INITIALIZED
        set(value) {
            field = value
            if (parent.currentState >= value) {
                currentState = value
            }
        }

    init {
        observeParent()
    }

    private fun observeParent() {
        parent.addObserver(parentLifecycleObserver)
    }

    private fun ignoreParent() {
        parent.removeObserver(parentLifecycleObserver)
    }

    override fun setCurrentState(nextState: State) {
        val maxNextState = if (nextState > highestState) highestState else nextState
        if (nextState == State.DESTROYED) ignoreParent()
        super.setCurrentState(maxNextState)
    }
}
