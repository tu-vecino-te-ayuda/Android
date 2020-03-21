package org.tuvecinoteayuda.needhelp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.tuvecinoteayuda.databinding.FragmentNeedHelpBinding
import org.tuvecinoteayuda.needhelp.NeedHelpFragmentDirections.actionNeedHelpFragmentToRegisterFormFragment
import org.tuvecinoteayuda.register.RegisterType
import org.tuvecinoteayuda.view.showToast

class NeedHelpFragment : Fragment() {

    private lateinit var binding: FragmentNeedHelpBinding
    private val viewModel: NeedHelpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        FragmentNeedHelpBinding.inflate(inflater, container, false).apply {
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
        binding.needHelpButton.setOnClickListener {
            findNavController().navigate(
                actionNeedHelpFragmentToRegisterFormFragment()
                    .setRegisterType(RegisterType.Requester.ordinal)
            )
        }
    }
}
