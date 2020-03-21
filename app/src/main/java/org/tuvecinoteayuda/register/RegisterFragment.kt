package org.tuvecinoteayuda.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private val args: RegisterFragmentArgs by navArgs()
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels { ViewModelFactory.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        FragmentRegisterBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            return root
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.start(RegisterType.values()[args.registerType])
    }
}
