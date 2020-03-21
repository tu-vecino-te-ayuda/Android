package org.tuvecinoteayuda.app.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.tuvecinoteayuda.app.R
import org.tuvecinoteayuda.app.navigation.InAppNavigator

class LoginActivity : AppCompatActivity(), LoginPresenter.LoginView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val presenter: LoginPresenter = LoginPresenter.newInstance(this)

        login_button.setOnButtonClickListener {
            val user = login_user.text.toString()
            val password = login_password.text.toString()

            presenter.doLogin(user, password)
        }

        login_user.setText("necesitado@necesitado.com")
        login_password.setText("1234567890")

        login_need_help.setOnClickListener { presenter.onNeedHelp() }
        login_want_to_help.setOnClickListener { presenter.onWantToHelp() }

    }

    override fun showLoading() {
        login_button.showLoading()
    }

    override fun hideLoading() {
        login_button.hideLoading()
    }

    override fun onLoading() {
        Log.d("LoginActivity", "onLoading")
    }

    override fun onError() {
        Log.d("LoginActivity", "onError")
    }

    override fun navigateToWantToHelp() {
        InAppNavigator().startWantToHelp(this)
    }

    override fun navigateToNeedHelp() {
        InAppNavigator().startNeedHelp(this)
    }
}
