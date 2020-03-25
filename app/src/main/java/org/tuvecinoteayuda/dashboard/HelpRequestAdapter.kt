package org.tuvecinoteayuda.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.databinding.CardHelpRequestBinding

class HelpRequestAdapter(
    private var dataSet: List<HelpRequest>
) : RecyclerView.Adapter<HelpRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpRequestViewHolder {
        val view = CardHelpRequestBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HelpRequestViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: HelpRequestViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

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
