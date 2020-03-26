package org.tuvecinoteayuda.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.tuvecinoteayuda.DashboardType
import org.tuvecinoteayuda.R
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.core.ext.hide
import org.tuvecinoteayuda.core.ext.show
import org.tuvecinoteayuda.core.ext.showSnackBarError
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.core.ui.VerticalItemDecorator
import org.tuvecinoteayuda.core.util.observeEvent
import org.tuvecinoteayuda.dashboard.helprequests.HelpRequestsAdapter
import org.tuvecinoteayuda.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private val args: DashboardFragmentArgs by navArgs()
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

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.my_request -> {
                    viewModel.getMyRequest()
                }
                R.id.pending_request -> {
                    viewModel.getAllRequest()
                }

                else -> {
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.start(DashboardType.values()[args.dashboardtype])
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModelEvents()
        observeViewModelData()
    }

    override fun onDestroy() {
        viewModel.stop()
        super.onDestroy()
    }

    private fun observeViewModelEvents() {
        viewModel.onVoluntaryLoadedEvent.observeEvent(viewLifecycleOwner, {
            binding.bottomNavigation.show()
        })

        viewModel.onRequestedLoadedEvent.observeEvent(viewLifecycleOwner, {
            binding.bottomNavigation.hide()
        })
    }

    private fun observeViewModelData() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                ScreenState.LOADING_DATA -> {
                    showLoading()
                    hideData()
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

            viewModel.allRequest.observe(viewLifecycleOwner, Observer { requestList ->
                adapter.setData(requestList)
            })

            viewModel.requesrError.observe(viewLifecycleOwner, Observer { error ->
                showSnackBarError(error)
            })
        })
    }

    private fun showLoading() {
        loading.show()
    }

    private fun hideLoading() {
        loading.hide()
    }

    private fun showData() {
        binding.requestList.show()
        binding.emptyText.hide()
    }

    private fun hideData() {
        binding.requestList.hide()
    }

    private fun showError() {
        loading.hide()
        binding.requestList.hide()
        binding.emptyText.hide()
    }

    private fun showEmptyData() {
        loading.hide()
        binding.requestList.hide()
        binding.emptyText.show()

    }

}



