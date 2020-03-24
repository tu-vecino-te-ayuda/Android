package org.tuvecinoteayuda.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import org.tuvecinoteayuda.core.databinding.AutoCompleteTextviewItemBinding
import java.util.*

/**
 * The filtering is performed calling toString() in the model class.
 */
class AutoCompleteAdapter<T>(
    private val context: Context
): BaseAdapter(), Filterable {

    private var data: MutableList<T> = mutableListOf()
    private var dataFiltered: MutableList<T> = mutableListOf()
    private val filter by lazy { AutoCompleteFilter() }

    @Suppress("UNCHECKED_CAST")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder = if (convertView == null) {
            AutoCompleteViewHolder<T>(
                AutoCompleteTextviewItemBinding.inflate(
                    LayoutInflater.from(context), parent, false
                )
            ).apply { getRoot().tag = this }
        } else {
            convertView.tag as AutoCompleteViewHolder<T>
        }
        viewHolder.bind(dataFiltered[position])
        return viewHolder.getRoot()
    }

    override fun getItem(position: Int) = dataFiltered[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = dataFiltered.size

    override fun getFilter(): Filter = filter

    fun setData(data: List<T>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        this.dataFiltered.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    private inner class AutoCompleteViewHolder<T>(
        private val binding: AutoCompleteTextviewItemBinding
    ) {

        fun bind(data: T) {
            binding.item.text = data.toString()
        }

        fun getRoot() = binding.root
    }

    private inner class AutoCompleteFilter : Filter() {
        override fun performFiltering(userText: CharSequence?): FilterResults {
            return FilterResults().apply {
                if (userText.isNullOrBlank()) {
                    values = data.toList()
                    count = data.count()
                } else {
                    val userTextString = userText.sanitizeUserText()
                    data.filter {
                        it.toString().startsWith(userTextString, ignoreCase = true)
                    }.let { filteredRegions ->
                        values = filteredRegions
                        count = filteredRegions.count()
                    }
                }
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            dataFiltered.clear()
            (results?.values as? MutableList<T>)?.run {
                sortBy { it.toString().toLowerCase(Locale.ROOT) }
                dataFiltered.addAll(this)
            }
            if (results?.count ?: 0 > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

        private fun CharSequence.sanitizeUserText(): String {
            return this.trim().toString()
        }
    }
}
