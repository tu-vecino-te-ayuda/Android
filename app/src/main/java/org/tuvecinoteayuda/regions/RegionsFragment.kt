package org.tuvecinoteayuda.regions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_regions.*
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.databinding.FragmentRegionsBinding
import org.tuvecinoteayuda.register.BaseFilterableAdapter
import org.tuvecinoteayuda.register.RegionsAdapter

class RegionsFragment : Fragment(), BaseFilterableAdapter.Contract {

    private lateinit var binding: FragmentRegionsBinding
    private val viewModel: RegionsViewModel by viewModels { ViewModelFactory.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        FragmentRegionsBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            return root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeScreenState()

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    private fun observeScreenState() {
        viewModel.regions.observe(viewLifecycleOwner, Observer { regionList ->
//            recycler.adapter = RegionsAdapter(ArrayList(regionList), {}, this)
        })
    }

    override fun onDataFilteredEmpty() {

    }

    override fun onDataFilteredNotEmpty() {
    }


}