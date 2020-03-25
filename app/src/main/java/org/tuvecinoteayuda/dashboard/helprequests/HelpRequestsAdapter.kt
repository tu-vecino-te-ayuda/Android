package org.tuvecinoteayuda.dashboard.helprequests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.databinding.CardHelpRequestBinding

class HelpRequestsAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private var dataSet: List<HelpRequest> = emptyList()
) : RecyclerView.Adapter<HelpRequestsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpRequestsViewHolder {
        return HelpRequestsViewHolder(
            binding = CardHelpRequestBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            parentLifecycle = lifecycleOwner.lifecycle
        )
    }

    override fun onBindViewHolder(holder: HelpRequestsViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    override fun onViewAttachedToWindow(holder: HelpRequestsViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAppear()
    }

    override fun onViewDetachedFromWindow(holder: HelpRequestsViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDisappear()
    }

    fun setData(dataSet: List<HelpRequest>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}
