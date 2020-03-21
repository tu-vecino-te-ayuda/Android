package org.tuvecinoteayuda.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.databinding.FragmentLoginBinding
import org.tuvecinoteayuda.login.LoginFragmentDirections.actionLoginFragmentToWantToHelpFragment
import org.tuvecinoteayuda.utils.ScreenState

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel
            by viewModels { ViewModelFactory.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        FragmentLoginBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = viewLifecycleOwner
            vm = loginViewModel
            setupListeners()
            return root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeScreenState()
    }

    override fun onResume() {
        super.onResume()
        loginViewModel.start()
    }

    private fun setupListeners() {
        binding.loginButton.setOnButtonClickListener{ loginViewModel.login() }
        binding.loginWantToHelp.setOnClickListener {
            findNavController().navigate(actionLoginFragmentToWantToHelpFragment())
        }
    }

    private fun observeScreenState() {
        loginViewModel.screenState.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                ScreenState.LOADING_DATA -> binding.loginButton.showLoading()
                else -> binding.loginButton.hideLoading()
            }
        })
    }
}
