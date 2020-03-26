package org.tuvecinoteayuda.dashboard.helprequests

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import org.tuvecinoteayuda.core.ui.LifecycleAdapter
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest

class HelpRequestsAdapter(
    private var dataSet: List<HelpRequest> = emptyList()
) : LifecycleAdapter<HelpRequestsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpRequestsViewHolder {
        return HelpRequestsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: HelpRequestsViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun setData(dataSet: List<HelpRequest>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}
