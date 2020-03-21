package org.tuvecinoteayuda.app.register

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tuvecinoteayuda.app.coroutines.CoroutineContextProvider
import org.tuvecinoteayuda.data.regions.repository.RegionRepository

class RegisterFormPresenter constructor(
    private val ui: RegisterFormView,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val repository: RegionRepository
) {

    interface RegisterFormView {

    }

    fun initialize() {


    }

    private fun getRegions() {

        CoroutineScope(coroutineContextProvider.Main).launch {

            try {
                val result = withContext(coroutineContextProvider.IO) {
//                    repository.doLogin(user, password)
                }


                onRegionsLoaded(result)
            } catch (throwable: Throwable) {


//                ui.onError()
            }
        }
    }

    private fun onRegionsLoaded(result: Any) {

    }

    companion object {

        fun newInstance(ui: RegisterFormView) {
//            return RegisterFormPresenter(ui)
        }
    }
}