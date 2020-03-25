package org.tuvecinoteayuda.core.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: String): String {
    return try {
        val sdf = SimpleDateFormat("dd/MM/YY", Locale.getDefault())
        val netDate = Date(date)
        sdf.format(netDate)
    } catch (ex: Exception) {
        ""
    }
}