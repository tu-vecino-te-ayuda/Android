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
import org.tuvecinoteayuda.core.ext.hide
import org.tuvecinoteayuda.core.ext.show
import org.tuvecinoteayuda.core.ext.showOrHide
import org.tuvecinoteayuda.core.ext.showSnackBarError
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.core.ui.VerticalItemDecorator
import org.tuvecinoteayuda.core.util.observeEvent
import org.tuvecinoteayuda.dashboard.DashboardFragmentDirections.*
import org.tuvecinoteayuda.dashboard.helprequests.HelpRequestsAdapter
import org.tuvecinoteayuda.data.helprequests.models.HelpRequest
import org.tuvecinoteayuda.databinding.FragmentDashboardBinding

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
        observeViewModelScreen()
        observeViewModelEvents()
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

    private fun observeViewModelScreen() {

        viewModel.showButton.observe(viewLifecycleOwner, Observer { show ->
            binding.newHelpRequestButton.showOrHide(show)
        })

        viewModel.screenState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                ScreenState.LOADING_DATA -> {
                    showLoading()
                    hideData()
                    hideEmptyData()
                }
                ScreenState.DATA_LOADED -> {
                    hideLoading()
                    showData()
                }
                ScreenState.ERROR -> {
                    hideLoading()
                    showError()
                }
                ScreenState.EMPTY_DATA -> {
                    hideLoading()
                    showEmptyData()
                }
                else -> hideLoading()
            }
        })

    }

    private fun observeViewModelEvents() {
        viewModel.onVoluntaryLoadedEvent.observeEvent(viewLifecycleOwner, {
            binding.bottomNavigation.show()
            binding.newHelpRequestButton.hide()
        })

        viewModel.onRequestedLoadedEvent.observeEvent(viewLifecycleOwner, {
            binding.bottomNavigation.hide()
        })

        viewModel.onAllRequestEmpty.observeEvent(viewLifecycleOwner, {
            binding.emptyText.text = getString(R.string.dashboard_all_requests_empty)
        })

        viewModel.onMyRequestEmpty.observeEvent(viewLifecycleOwner, {
            binding.emptyText.text = getString(R.string.dashboard_my_requests_empty)
        })
    }

    private fun observeViewModelData() {
        viewModel.allRequest.observe(viewLifecycleOwner, Observer { requestList ->
            adapter.setData(requestList)
        })

        viewModel.requestError.observe(viewLifecycleOwner, Observer { error ->
            showSnackBarError(error)
        })
    }

    private fun showLoading() {
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.loading.hide()
    }

    private fun showData() {
        binding.requestList.show()
        binding.emptyText.hide()
    }

    private fun hideData() {
        binding.requestList.hide()
    }

    private fun hideEmptyData() {
        binding.emptyText.hide()
    }

    private fun showError() {
        binding.loading.hide()
        binding.requestList.hide()
        binding.emptyText.hide()
    }

    private fun showEmptyData() {
        binding.loading.hide()
        binding.requestList.hide()
        binding.emptyText.show()
    }

    private fun onItemClicked(item: HelpRequest) {
        findNavController().navigate(
            actionDashboardFragmentToRequestDetailFragment().setRequestId(item.id)
        )
    }
}
