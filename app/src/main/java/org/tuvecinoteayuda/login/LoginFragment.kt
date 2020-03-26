package org.tuvecinoteayuda.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.tuvecinoteayuda.dashboard.DashboardType
import org.tuvecinoteayuda.R
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.core.ext.showSnackBarError
import org.tuvecinoteayuda.core.ui.ScreenState
import org.tuvecinoteayuda.core.util.observeEvent
import org.tuvecinoteayuda.data.commons.models.UserTypeId
import org.tuvecinoteayuda.databinding.FragmentLoginBinding
import org.tuvecinoteayuda.login.LoginFragmentDirections.*

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels { ViewModelFactory.getInstance() }

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
        binding.loginButton.setOnButtonClickListener { loginViewModel.login() }
        binding.loginWantToHelp.setOnClickListener {
            findNavController().navigate(actionLoginFragmentToWantToHelpFragment())
        }
        binding.loginNeedHelp.setOnClickListener {
            findNavController().navigate(actionLoginFragmentToNeedHelpFragment())
        }
    }

    private fun observeScreenState() {
        loginViewModel.screenState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                ScreenState.LOADING_DATA -> binding.loginButton.showLoading()
                else -> binding.loginButton.hideLoading()
            }
        })
        loginViewModel.userError.observe(viewLifecycleOwner, Observer { error ->
            binding.loginUserContainer.error =
                if (error) getString(R.string.login_login_error_invalid_user) else null
        })
        loginViewModel.passwordError.observe(viewLifecycleOwner, Observer { error ->
            binding.loginPasswordContainer.error =
                if (error) getString(R.string.login_login_error_invalid_password) else null
        })
        loginViewModel.onLoginSuccessEvent.observeEvent(viewLifecycleOwner) { userType ->

            val type = when (userType.id) {
                UserTypeId.VOLUNTARIO_ID -> {
                    DashboardType.VOLUNTARY
                }
                UserTypeId.SOLICITANTE_ID -> {
                    DashboardType.REQUESTER
                }
                else -> {
                    DashboardType.VOLUNTARY
                }
            }
            findNavController().navigate(
                actionLoginFragmentToDashboardFragment().setDashboardtype(
                    type.ordinal
                )
            )
        }
        loginViewModel.onLoginFailedEvent.observeEvent(viewLifecycleOwner) {
            showSnackBarError(it)
        }
        loginViewModel.onLoginFailedGenericEvent.observeEvent(viewLifecycleOwner) {
            showSnackBarError(R.string.login_login_error)
        }
    }
}
