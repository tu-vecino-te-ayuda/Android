package org.tuvecinoteayuda.core.ui

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


fun showDialog(
    context: Context,
    title: String,
    message: String,
    positiveButton: String,
    positiveButtonAction: () -> Unit,
    negativeButton: String,
    negativeButtonAction: () -> Unit = {}
) {

    val builder: AlertDialog.Builder = AlertDialog.Builder(context)

    builder.setMessage(message)
        .setTitle(title)
        .setPositiveButton(
            positiveButton,
            DialogInterface.OnClickListener { _, i -> positiveButtonAction() })
        .setNegativeButton(
            negativeButton,
            DialogInterface.OnClickListener { _, i -> negativeButtonAction() }
        )

    val dialog: AlertDialog? = builder.create()

    dialog?.run {
        show()
    }
}