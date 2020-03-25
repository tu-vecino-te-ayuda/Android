package org.tuvecinoteayuda.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import org.tuvecinoteayuda.R
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.core.ui.VerticalItemDecorator
import org.tuvecinoteayuda.dashboard.helprequests.HelpRequestsAdapter
import org.tuvecinoteayuda.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels { ViewModelFactory.getInstance() }
    private val adapter: HelpRequestsAdapter by lazy {
        HelpRequestsAdapter(
            viewLifecycleOwner
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        FragmentDashboardBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            initViews()
            return root
        }
    }

    private fun initViews() {
        binding.requestList.apply {
            adapter = this@DashboardFragment.adapter
            addItemDecoration(
                VerticalItemDecorator(resources.getDimension(R.dimen.spacing_2x_large).toInt())
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModelData()
    }

    private fun observeViewModelData() {
        viewModel.requests.observe(viewLifecycleOwner, Observer { requestList ->
            adapter.setData(requestList)
        })
    }
}
