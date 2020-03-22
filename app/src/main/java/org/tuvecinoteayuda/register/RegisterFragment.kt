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
import org.tuvecinoteayuda.ViewModelFactory
import org.tuvecinoteayuda.data.regions.models.City
import org.tuvecinoteayuda.data.regions.models.Region
import org.tuvecinoteayuda.databinding.FragmentRegisterBinding
import org.tuvecinoteayuda.register.RegisterFragmentDirections.actionRegisterFragmentToRegionsFragment
import org.tuvecinoteayuda.utils.AutoCompleteAdapter

class RegisterFragment : Fragment() {

    private val args: RegisterFragmentArgs by navArgs()
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels { ViewModelFactory.getInstance() }

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
        viewModel.start(RegisterType.values()[args.registerType])
    }

    private fun setupListeners() {
        binding.loginButton.setOnButtonClickListener {
            findNavController().navigate(actionRegisterFragmentToRegionsFragment())
        }
        binding.region.setAdapter(regionsAdapter)
        binding.city.setAdapter(citiesAdapter)
    }

    private fun observeScreenState() {
        viewModel.regions.observe(viewLifecycleOwner, Observer { regions ->
            regionsAdapter.setData(regions)
        })
        viewModel.cities.observe(viewLifecycleOwner, Observer { cities ->
            citiesAdapter.setData(cities)
        })
    }

}
