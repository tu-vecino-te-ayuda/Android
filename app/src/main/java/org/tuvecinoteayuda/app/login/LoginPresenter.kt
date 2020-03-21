package org.tuvecinoteayuda.app.login

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tuvecinoteayuda.app.coroutines.CoroutineContextProvider
import org.tuvecinoteayuda.data.login.models.LoginResponse
import org.tuvecinoteayuda.data.login.repository.LoginRepository

class LoginPresenter(
    private val ui: LoginView,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val repository: LoginRepository
) {

    interface LoginView {
        fun showLoading()
        fun hideLoading()
        fun onLoading()
        fun onError()
        fun navigateToWantToHelp()
        fun navigateToNeedHelp()
    }

    fun doLogin(user: String, password: String) {

        CoroutineScope(coroutineContextProvider.Main).launch {

            ui.showLoading()

            try {
                val result = withContext(coroutineContextProvider.IO) {
                    repository.doLogin(user, password)
                }

                ui.hideLoading()

                onLoginSuccess(result)
            } catch (throwable: Throwable) {
                ui.hideLoading()

                ui.onError()
            }
        }
    }

    private fun onLoginSuccess(loginResponse: LoginResponse) {
        ui.onLoading()
    }

    fun onWantToHelp() {
        ui.navigateToWantToHelp()
    }

    fun onNeedHelp() {
        ui.navigateToNeedHelp()
    }

    companion object {

        fun newInstance(ui: LoginView): LoginPresenter {
            return LoginPresenter(ui, CoroutineContextProvider(), LoginRepository.newInstance())
        }
    }
}