package org.tuvecinoteayuda.register

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
import org.tuvecinoteayuda.data.commons.models.NearByAreaTypeId
import org.tuvecinoteayuda.data.regions.models.City
import org.tuvecinoteayuda.data.regions.models.Region
import org.tuvecinoteayuda.databinding.FragmentRegisterBinding
import org.tuvecinoteayuda.utils.AutoCompleteAdapter
import org.tuvecinoteayuda.utils.ScreenState
import org.tuvecinoteayuda.utils.observeEvent
import org.tuvecinoteayuda.view.removeErrorOnTyping
import org.tuvecinoteayuda.view.setMaxLenght
import org.tuvecinoteayuda.view.showOrHide
import org.tuvecinoteayuda.view.showSnackBarError

class RegisterFragment : Fragment() {

    private val args: RegisterFragmentArgs by navArgs()
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels { ViewModelFactory.getInstance() }

    private val areaAdapter by lazy { AutoCompleteAdapter<NearByAreaTypeId>(requireContext()) }
    private val regionsAdapter by lazy { AutoCompleteAdapter<Region>(requireContext()) }
    private val citiesAdapter by lazy { AutoCompleteAdapter<City>(requireContext()) }

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
            area.setAdapter(areaAdapter)
            region.setAdapter(regionsAdapter)
            city.setAdapter(citiesAdapter)
            setupListeners()
            configureViews()
            return root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModelData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.start(RegisterType.values()[args.registerType])
    }

    private fun setupListeners() {
        binding.registerButton.setOnButtonClickListener {
            viewModel.register()
        }

        binding.nameContainer.removeErrorOnTyping()
        binding.emailContainer.removeErrorOnTyping()
        binding.phoneContainer.removeErrorOnTyping()
        binding.passwordContainer.removeErrorOnTyping()
        binding.repeatedPasswordContainer.removeErrorOnTyping()
        binding.postalCodeContainer.removeErrorOnTyping()
        binding.addressContainer.removeErrorOnTyping()
    }

    private fun configureViews(){
        binding.phone.setMaxLenght(RegisterViewModel.MAX_PHONE_LENGTH)
        binding.postalCode.setMaxLenght(RegisterViewModel.MAX_ZIP_CODE_LENGTH)
    }

    private fun observeViewModelData() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                ScreenState.LOADING_DATA -> binding.registerButton.showLoading()
                else -> binding.registerButton.hideLoading()
            }
        })
        viewModel.areas.observe(viewLifecycleOwner, Observer { areas ->
            areaAdapter.setData(areas)
        })
        viewModel.regions.observe(viewLifecycleOwner, Observer { regions ->
            regionsAdapter.setData(regions)
        })
        viewModel.cities.observe(viewLifecycleOwner, Observer { cities ->
            citiesAdapter.setData(cities)
        })
        // Errors
        viewModel.nameError.observe(viewLifecycleOwner, Observer { error ->
            binding.nameContainer.error =
                if (error) getString(R.string.register_name_invalid) else null
        })
        viewModel.emailError.observe(viewLifecycleOwner, Observer { error ->
            binding.emailContainer.error =
                if (error) getString(R.string.register_email_invalid) else null
        })
        viewModel.phoneError.observe(viewLifecycleOwner, Observer { error ->
            binding.phoneContainer.error =
                if (error) getString(R.string.register_phone_invalid) else null
        })
        viewModel.passwordError.observe(viewLifecycleOwner, Observer { error ->
            binding.passwordContainer.error =
                if (error) getString(R.string.register_password_invalid) else null
        })
        viewModel.repeatedPasswordError.observe(viewLifecycleOwner, Observer { error ->
            binding.repeatedPasswordContainer.error =
                if (error) getString(R.string.register_repeat_password_invalid) else null
        })
        viewModel.areaError.observe(viewLifecycleOwner, Observer { error ->
            binding.cityContainer.error =
                if (error) getString(R.string.register_area_invalid) else null
        })
        viewModel.regionError.observe(viewLifecycleOwner, Observer { error ->
            binding.regionContainer.error =
                if (error) getString(R.string.register_region_invalid) else null
        })
        viewModel.cityError.observe(viewLifecycleOwner, Observer { error ->
            binding.cityContainer.error =
                if (error) getString(R.string.register_city_invalid) else null
        })
        viewModel.addressError.observe(viewLifecycleOwner, Observer { error ->
            binding.addressContainer.error =
                if (error) getString(R.string.register_address_invalid) else null
        })
        viewModel.postalCodeError.observe(viewLifecycleOwner, Observer { error ->
            binding.postalCodeContainer.error =
                if (error) getString(R.string.register_postal_code_invalid) else null
        })
        // Navigation
        viewModel.onRegisterSuccessEvent.observeEvent(viewLifecycleOwner) {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFormFragmentToDashboardFragment())
        }
        viewModel.onRegisterFailedEvent.observeEvent(viewLifecycleOwner) {
            showSnackBarError(R.string.register_error)
        }
    }
}
