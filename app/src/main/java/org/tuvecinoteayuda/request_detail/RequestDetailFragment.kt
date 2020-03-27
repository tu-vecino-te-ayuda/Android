package org.tuvecinoteayuda.request_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.dashboard.DashboardType
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
            return root
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.start(args.requestId)
    }

}