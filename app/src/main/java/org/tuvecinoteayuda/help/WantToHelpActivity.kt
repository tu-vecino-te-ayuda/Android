package org.tuvecinoteayuda.help

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_want_to_help.*
import org.tuvecinoteayuda.R
import org.tuvecinoteayuda.navigation.InAppNavigator

class WantToHelpActivity : AppCompatActivity(), WantToHelpPresenter.WantToHelpView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_want_to_help)

        val presenter = WantToHelpPresenter.newIntance(this)

        want_to_help_button.setOnClickListener { presenter.onWantToHelp() }
    }


    companion object {
        fun getIntent(activity: Context): Intent {
            return Intent(activity, WantToHelpActivity::class.java)
        }
    }

    override fun onWantToHelp() {
        InAppNavigator().startRegisterVoluntary(this)
        this.finish()
    }
}
