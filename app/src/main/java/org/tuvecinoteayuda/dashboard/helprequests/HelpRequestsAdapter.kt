package org.tuvecinoteayuda.dashboard.helprequests

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import org.tuvecinoteayuda.core.ui.LifecycleAdapter
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest

class HelpRequestsAdapter(
    private var dataSet: MutableList<HelpRequest> = mutableListOf(),
    private var lifecycleOwner : LifecycleOwner,
    private var onItemClick: (HelpRequest) -> Unit
) : LifecycleAdapter<HelpRequestsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpRequestsViewHolder {
        return HelpRequestsViewHolder(parent, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: HelpRequestsViewHolder, position: Int) {
        holder.bind(dataSet[position], onItemClick)
    }

    override fun getItemCount() = dataSet.size

    fun setData(dataSet: List<HelpRequest>) {
        this.dataSet.apply {
            clear()
            addAll(dataSet)
        }
        notifyDataSetChanged()
    }
}
