package org.tuvecinoteayuda.navigation

import android.content.Context


interface RouterContract {

    fun startNeedHelp(context: Context)

    fun startWantToHelp(context: Context)

    fun startRegisterVoluntary(context: Context)

    fun startRegisterNeeded(context: Context)


}