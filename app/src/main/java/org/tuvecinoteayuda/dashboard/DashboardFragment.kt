package org.tuvecinoteayuda.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.tuvecinoteayuda.R
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.core.ext.showSnackBarError
import org.tuvecinoteayuda.core.ui.VerticalItemDecorator
import org.tuvecinoteayuda.dashboard.DashboardFragmentDirections.*
import org.tuvecinoteayuda.dashboard.helprequests.HelpRequestsAdapter
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.databinding.FragmentDashboardBinding
import org.tuvecinoteayuda.requestdetail.HelpRequestType

class DashboardFragment : Fragment() {

    private val args: DashboardFragmentArgs by navArgs()
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels { ViewModelFactory.getInstance() }
    private val adapter: HelpRequestsAdapter =
        HelpRequestsAdapter(onItemClick = { item -> onItemClicked(item) })

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
            setupListeners()
            return root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModelData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.start(DashboardType.values()[args.dashboardtype])
    }

    private fun initViews() {
        binding.requestList.apply {
            adapter = this@DashboardFragment.adapter
            addItemDecoration(
                VerticalItemDecorator(resources.getDimension(R.dimen.spacing_2x_large).toInt())
            )
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.my_request -> {
                    viewModel.getMyRequest()
                }
                R.id.pending_request -> {
                    viewModel.getPendingRequest()
                }
                else -> { /* do nothing */
                }
            }
            true
        }
    }

    private fun setupListeners() {
        binding.profile.setOnClickListener {
            findNavController().navigate(actionDashboardFragmentToProfileFragment())
        }
        binding.newHelpRequestButton.setOnClickListener {
            findNavController().navigate(actionDashboardFragmentToRequestHelpFragment())
        }
    }

    private fun observeViewModelData() {
        viewModel.requests.observe(viewLifecycleOwner, Observer { requests ->
            adapter.setData(requests)
        })
        viewModel.requestError.observe(viewLifecycleOwner, Observer { error ->
            showSnackBarError(error)
        })
    }

    private fun onItemClicked(item: HelpRequest) {
        val type = when (DashboardType.values()[args.dashboardtype]) {
            DashboardType.REQUESTER -> HelpRequestType.REQUESTER
            DashboardType.VOLUNTARY -> HelpRequestType.VOLUNTARY
        }
        findNavController().navigate(
            actionDashboardFragmentToRequestDetailFragment().setRequestId(item.id)
                .setUserType(type.ordinal)
        )
    }
}
