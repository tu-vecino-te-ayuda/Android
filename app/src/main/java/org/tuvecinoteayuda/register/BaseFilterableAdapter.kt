package org.tuvecinoteayuda.register

import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import org.tuvecinoteayuda.register.BaseFilterableAdapter.Contract


/**
 * BaseAdapter to be able to filter elements in lists
 *
 * This class is created to implement easily lists filterable with fragment than implements [Contract]
 *
 */
abstract class BaseFilterableAdapter<T>(dataSet: List<T>, private val ui: Contract) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    protected var dataset: List<T> = ArrayList()
    protected var dataSetFiltered: List<T> = ArrayList()

    init {
        dataSetFiltered = dataSet
    }

    override fun getItemCount(): Int {
        return dataSetFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                val filterResults = Filter.FilterResults()

                if (charString.isEmpty()) {
                    filterResults.values = dataset
                } else {
                    filterResults.values = dataset.filter {
                        evaluateFilter(it, charSequence)
                    }
                }

                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: Filter.FilterResults
            ) {
                dataSetFiltered = filterResults.values as List<T>
                notifyDataSetChanged()
                if (dataSetFiltered.isEmpty()) {
                    ui.onDataFilteredEmpty()
                } else {
                    ui.onDataFilteredNotEmpty()
                }
            }
        }
    }

    abstract fun evaluateFilter(data: T, charSequence: CharSequence): Boolean

    interface Contract {
        fun onDataFilteredEmpty()
        fun onDataFilteredNotEmpty()
    }
}