package org.tuvecinoteayuda.register

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import org.tuvecinoteayuda.data.regions.models.Region
import org.tuvecinoteayuda.databinding.AutoCompleteTextviewItemBinding
import java.util.*

class RegionsAdapter(
    private val context: Context
) : BaseAdapter(), Filterable {

    private var regions: MutableList<Region> = mutableListOf()
    private var regionsFiltered: MutableList<Region> = mutableListOf()
    private val filter by lazy { RegionFilter() }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder = if (convertView == null) {
            RegionViewHolder(
                AutoCompleteTextviewItemBinding.inflate(
                    LayoutInflater.from(context), parent, false
                )
            ).apply { getRoot().tag = this }
        } else {
            convertView.tag as RegionViewHolder
        }
        viewHolder.bind(regionsFiltered[position])
        return viewHolder.getRoot()
    }

    override fun getItem(position: Int) = regionsFiltered[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = regionsFiltered.size

    override fun getFilter(): Filter = filter

    fun setRegions(regions: List<Region>) {
        this.regions.apply {
            clear()
            addAll(regions)
        }
        this.regionsFiltered.apply {
            clear()
            addAll(regions)
        }
        notifyDataSetChanged()
    }

    private inner class RegionViewHolder(
        private val binding: AutoCompleteTextviewItemBinding
    ) {

        fun bind(region: Region) {
            binding.item.text = region.name
        }

        fun getRoot() = binding.root
    }

    private inner class RegionFilter : Filter() {
        override fun performFiltering(userText: CharSequence?): FilterResults {
            return FilterResults().apply {
                if (userText.isNullOrBlank()) {
                    values = regions.toList()
                    count = regions.count()
                } else {
                    val userTextString = userText.sanitizeUserText()
                    regions.filter {
                        it.name.startsWith(userTextString, ignoreCase = true)
                    }.let { filteredRegions ->
                        values = filteredRegions
                        count = filteredRegions.count()
                    }
                }
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            regionsFiltered.clear()
            (results?.values as? MutableList<Region>)?.run {
                sortBy { it.name.toLowerCase(Locale.ROOT) }
                regionsFiltered.addAll(this)
            }
            if (results?.count ?: 0 > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

        private fun CharSequence.sanitizeUserText(): String {
            return this.trim().toString().toLowerCase(Locale.ROOT)
        }
    }
}
