package org.tuvecinoteayuda.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.databinding.CardHelpRequestBinding

class HelpRequestAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private var dataSet: List<HelpRequest> = emptyList()
) : RecyclerView.Adapter<HelpRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpRequestViewHolder {
        return HelpRequestViewHolder(
            binding = CardHelpRequestBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            parentLifecycle = lifecycleOwner.lifecycle
        )
    }

    override fun onBindViewHolder(holder: HelpRequestViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    override fun onViewAttachedToWindow(holder: HelpRequestViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAppear()
    }

    override fun onViewDetachedFromWindow(holder: HelpRequestViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDisappear()
    }

    fun setData(dataSet: List<HelpRequest>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}
