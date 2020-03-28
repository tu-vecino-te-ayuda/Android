package org.tuvecinoteayuda.request_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.core.ext.showSnackBarError
import org.tuvecinoteayuda.core.ext.showToast
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
            setUpListeners()
            return root
        }
    }

    private fun setUpListeners() {
        binding.acceptRequestButton.setOnClickListener { viewModel.acceptRequest() }
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

    }

    private fun observeEvents() {
        viewModel.onAcceptRequestSuccessEvent.observeEvent(viewLifecycleOwner, {
            showToast("Aceptado!")
        })

        viewModel.onAcceptRequestErrorEvent.observeEvent(viewLifecycleOwner, { error ->
            showSnackBarError(error)
        })
    }


}