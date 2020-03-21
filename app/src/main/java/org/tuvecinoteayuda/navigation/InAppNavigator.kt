package org.tuvecinoteayuda.navigation

import android.content.Context
import android.content.Intent
import org.tuvecinoteayuda.register.RegisterFormActivity

class InAppNavigator : RouterContract {

    override fun startRegisterVoluntary(context: Context) {
        val intent = Intent(RegisterFormActivity.getIntent(context, RegisterFormActivity.REGISTER_VOLUNTARY))
        context.startActivity(intent)
    }

    override fun startRegisterNeeded(context: Context) {
        val intent = Intent(RegisterFormActivity.getIntent(context, RegisterFormActivity.REGISTER_NEEDED))
        context.startActivity(intent)
    }
}