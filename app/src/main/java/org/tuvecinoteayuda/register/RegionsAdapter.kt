package org.tuvecinoteayuda.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.tuvecinoteayuda.R
import org.tuvecinoteayuda.data.regions.models.Region


class RegionsAdapter(
    var dataSet: ArrayList<Region>,
    private val onClickAction: (Region) -> Unit,
    ui: Contract
) : BaseFilterableAdapter<Region>(dataSet, ui) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_generic, parent, false)
        return RegionViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RegionViewHolder)?.bind(dataSetFiltered[position], onClickAction)
    }

    override fun evaluateFilter(data: Region, charSequence: CharSequence): Boolean =
        data.name.contains(charSequence, ignoreCase = true) || data.name.contains(
            charSequence,
            ignoreCase = true
        )


}