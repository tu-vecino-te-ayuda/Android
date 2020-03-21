package org.tuvecinoteayuda

import android.app.Application
import org.tuvecinoteayuda.data.DataContextWrapper

class AppAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataContextWrapper.context = applicationContext
    }
}