package org.tuvecinoteayuda.requesthelp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.tuvecinoteayuda.R
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.core.ext.clearOnFocus
import org.tuvecinoteayuda.core.ext.removeErrorOnTyping
import org.tuvecinoteayuda.core.ext.showSnackBarError
import org.tuvecinoteayuda.core.ext.showSuccesSnackbar
import org.tuvecinoteayuda.core.ui.AutoCompleteAdapter
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.core.util.observeEvent
import org.tuvecinoteayuda.data.helprequests.models.HelpRequestType
import org.tuvecinoteayuda.databinding.FragmentRequestHelpBinding

class RequestHelpFragment : Fragment() {

    private lateinit var binding: FragmentRequestHelpBinding
    private val viewModel: RequestHelpViewModel by viewModels { ViewModelFactory.getInstance() }

    private val helpRequestTypeAdapter by lazy { AutoCompleteAdapter<HelpRequestType>(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        FragmentRequestHelpBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            configureViews()
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
        viewModel.start()
    }

    private fun configureViews() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        binding.toolbar.setTitle(R.string.request_help_title)
        binding.helpRequestType.setAdapter(helpRequestTypeAdapter)
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.createRequestButton.setOnButtonClickListener {
            viewModel.createRequest()
        }
        binding.helpRequestType.setOnItemClickListener { _, _, position, _ ->
            viewModel.helpRequestType.value = helpRequestTypeAdapter.getItem(position)
        }
        binding.helpRequestType.clearOnFocus()
        binding.messageContainer.removeErrorOnTyping()
    }

    private fun observeViewModelData() {
        observeScreenState()
        observeSpinnersData()
        observeFieldsErrors()
        observeEvents()
    }

    private fun observeScreenState() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                ScreenState.LOADING_DATA -> binding.createRequestButton.showLoading()
                else -> binding.createRequestButton.hideLoading()
            }
        })
    }

    private fun observeSpinnersData() {
        viewModel.helpRequestTypes.observe(viewLifecycleOwner, Observer { types ->
            helpRequestTypeAdapter.setData(types)
        })
    }

    private fun observeFieldsErrors() {
        viewModel.helpRequestTypeError.observe(viewLifecycleOwner, Observer { error ->
            binding.helpRequestTypeContainer.error =
                if (error) getString(R.string.request_help_help_request_type_invalid) else null
        })
        viewModel.messageError.observe(viewLifecycleOwner, Observer { error ->
            binding.messageContainer.error =
                if (error) getString(R.string.request_help_message_invalid) else null
        })
    }

    private fun observeEvents() {
        viewModel.onHelpRequestCreatedEvent.observeEvent(viewLifecycleOwner) {
            showSuccesSnackbar(R.string.request_help_request_created)
            findNavController().popBackStack()
        }
        viewModel.onHelpRequestFailedEvent.observeEvent(viewLifecycleOwner) {
            showSnackBarError(R.string.request_help_request_failed)
        }
    }
}
