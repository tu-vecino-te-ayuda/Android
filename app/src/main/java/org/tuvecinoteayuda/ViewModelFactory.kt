package org.tuvecinoteayuda

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.tuvecinoteayuda.dashboard.DashboardViewModel
import org.tuvecinoteayuda.dashboard.helprequests.HelpRequestsViewModel
import org.tuvecinoteayuda.data.helprequests.repository.HelpRequestRepository
import org.tuvecinoteayuda.data.login.repository.LoginRepository
import org.tuvecinoteayuda.data.profile.repository.ProfileRepository
import org.tuvecinoteayuda.data.regions.repository.RegionRepository
import org.tuvecinoteayuda.data.register.repository.RegisterRepository
import org.tuvecinoteayuda.login.LoginViewModel
import org.tuvecinoteayuda.profile.ProfileViewModel
import org.tuvecinoteayuda.register.RegisterViewModel

class ViewModelFactory private constructor(
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(LoginRepository.newInstance())

                isAssignableFrom(RegisterViewModel::class.java) ->
                    RegisterViewModel(
                        RegionRepository.newInstance(),
                        RegisterRepository.newInstance()
                    )
                isAssignableFrom(DashboardViewModel::class.java) ->
                    DashboardViewModel(HelpRequestRepository.newInstance(), ProfileRepository.newInstance())
                isAssignableFrom(HelpRequestsViewModel::class.java) ->
                    HelpRequestsViewModel(
                        RegionRepository.newInstance()
                    )
                isAssignableFrom(ProfileViewModel::class.java) ->
                    ProfileViewModel(
                        RegionRepository.newInstance(),
                        RegisterRepository.newInstance(),
                        ProfileRepository.newInstance()
                    )
                else -> error("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance() =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory()
                    .also { INSTANCE = it }
            }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
