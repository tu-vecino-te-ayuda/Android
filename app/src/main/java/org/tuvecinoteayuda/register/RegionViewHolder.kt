package org.tuvecinoteayuda.register

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_generic.view.*
import org.tuvecinoteayuda.data.regions.models.Region

class RegionViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {

    fun bind(item: Region, onClickAction: (Region) -> Unit) {
        itemView.text.text = item.name
        itemView.setOnClickListener { onClickAction(item) }
    }
}
