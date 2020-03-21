package org.tuvecinoteayuda.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.tuvecinoteayuda.R

class RegisterFormActivity : AppCompatActivity(), RegisterFormPresenter.RegisterFormView {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)

        var presenter: RegisterFormPresenter = RegisterFormPresenter.newInstance(this)
        presenter.initialize()
    }

    companion object {

        const val ARG_REGISTER = "arg.register"
        const val REGISTER_VOLUNTARY = 0
        const val REGISTER_NEEDED = 1

        fun getIntent(activity: Context, registerType: Int): Intent {
            val intent: Intent = Intent(activity, RegisterFormActivity::class.java)
            intent.putExtra(ARG_REGISTER, registerType)
            return intent
        }
    }

    override fun onError() {

    }

}
