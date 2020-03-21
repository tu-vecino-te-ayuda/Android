package org.tuvecinoteayuda.wanttohelp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.tuvecinoteayuda.databinding.FragmentWantToHelpBinding

class WantToHelpFragment : Fragment() {

    private lateinit var binding: FragmentWantToHelpBinding
    private val viewModel: WantToHelpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        FragmentWantToHelpBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            setupListeners()
            return root
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    private fun setupListeners() {
       binding.wantToHelpButton.setOnClickListener {
           // TODO startRegisterVoluntary
       }
    }
}
