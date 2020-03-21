package org.tuvecinoteayuda.app.navigation

import android.content.Context
import android.content.Intent
import org.tuvecinoteayuda.app.help.WantToHelpActivity
import org.tuvecinoteayuda.app.needhelp.NeedHelpActivity
import org.tuvecinoteayuda.app.register.RegisterFormActivity

class InAppNavigator : RouterContract {

    override fun startNeedHelp(context: Context) {
        val intent = Intent(NeedHelpActivity.getIntent(context))
        context.startActivity(intent)
    }

    override fun startWantToHelp(context: Context) {
        val intent = Intent(WantToHelpActivity.getIntent(context))
        context.startActivity(intent)
    }

    override fun startRegisterVoluntary(context: Context) {
        val intent = Intent(RegisterFormActivity.getIntent(context, RegisterFormActivity.REGISTER_VOLUNTARY))
        context.startActivity(intent)
    }

    override fun startRegisterNeeded(context: Context) {
        val intent = Intent(RegisterFormActivity.getIntent(context, RegisterFormActivity.REGISTER_NEEDED))
        context.startActivity(intent)
    }
}