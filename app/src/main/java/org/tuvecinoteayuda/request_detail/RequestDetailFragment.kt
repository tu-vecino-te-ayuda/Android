package org.tuvecinoteayuda.request_detail

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
import org.tuvecinoteayuda.core.ext.showSuccesSnackbar
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.core.util.observeEvent
import org.tuvecinoteayuda.databinding.FragmentRequestDetailBinding

class RequestDetailFragment : Fragment() {

    private val args: RequestDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentRequestDetailBinding
    private val viewModel: RequestDetailViewModel by viewModels { ViewModelFactory.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        FragmentRequestDetailBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            configureViews()
            setUpListeners()
            return root
        }
    }

    private fun configureViews() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        binding.toolbar.setTitle(R.string.profile_title)
    }

    private fun setUpListeners() {
        binding.acceptRequestButton.setOnButtonClickListener { viewModel.acceptRequest() }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.cancel_request -> {viewModel.cancelRequest()}
                else-> {/*Do nothing*/}
            }
            true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeScreenState()
        observeEvents()
    }

    override fun onResume() {
        super.onResume()
        viewModel.start(args.requestId)
    }

    private fun observeScreenState() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                ScreenState.LOADING_DATA -> binding.acceptRequestButton.showLoading()
                else -> binding.acceptRequestButton.hideLoading()
            }
        })
    }

    private fun observeEvents() {
        viewModel.onAcceptRequestSuccessEvent.observeEvent(viewLifecycleOwner, {
            showSuccesSnackbar("Aceptado!")
        })

        viewModel.onCancelRequestSuccessEvent.observeEvent(viewLifecycleOwner, {
            showSuccesSnackbar("Cancelada!")
        })

        viewModel.onAcceptRequestErrorEvent.observeEvent(viewLifecycleOwner, { error ->
            showSnackBarError(error)
        })
    }


}