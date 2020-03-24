package org.tuvecinoteayuda.core.util

import android.content.Context
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    return try {
        context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
}
