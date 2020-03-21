package org.tuvecinoteayuda.app.register

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tuvecinoteayuda.app.coroutines.CoroutineContextProvider
import org.tuvecinoteayuda.data.regions.models.Region
import org.tuvecinoteayuda.data.regions.repository.RegionRepository

class RegisterFormPresenter constructor(
    private val ui: RegisterFormView,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val repository: RegionRepository
) {

    interface RegisterFormView {
        fun onError()

    }

    fun initialize() {
        getRegions()
    }

    private fun getRegions() {

        CoroutineScope(coroutineContextProvider.Main).launch {

            try {
                val result = withContext(coroutineContextProvider.IO) {
                    repository.getRegions()
                }

                onRegionsLoaded(result)
            } catch (throwable: Throwable) {
                ui.onError()
            }
        }
    }

    private fun onRegionsLoaded(result: List<Region>) {
        Log.d("REGIONS",result.toString())
    }

    companion object {

        fun newInstance(ui: RegisterFormView): RegisterFormPresenter {
            return RegisterFormPresenter(ui, CoroutineContextProvider(), RegionRepository.newInstance())
        }
    }
}