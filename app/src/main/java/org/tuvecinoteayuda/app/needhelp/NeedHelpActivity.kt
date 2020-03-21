package org.tuvecinoteayuda.app.needhelp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_need_help.*
import org.tuvecinoteayuda.app.R
import org.tuvecinoteayuda.app.navigation.InAppNavigator

class NeedHelpActivity : AppCompatActivity(), NeedHelpPresenter.NeedHelpView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_need_help)

        val presenter = NeedHelpPresenter.newIntance(this)

        need_help_button.setOnClickListener { presenter.onWantToHelp() }
    }

    companion object {
        fun getIntent(activity: Context): Intent {
            val intent: Intent = Intent(activity, NeedHelpActivity::class.java)
            return intent
        }
    }

    override fun onNeedHelp() {
        InAppNavigator().startRegisterNeeded(this)
        this.finish()
    }
}
