package com.bismark.onlineticket

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View

class Utils {

    companion object {
        fun convertIntToDp(context: Context, pixel: Int): Int {
            val density = context.resources.displayMetrics.density
            return (density * pixel).toInt()
        }

        fun createDialog(
            context: Context,
            view: View,
            title: String,
            confirmClosure: (dialog: DialogInterface) -> Unit,
            closeClosure: (dialog: DialogInterface) -> Unit
        ) {

            val alertDialog = AlertDialog.Builder(context)
                .setView(view)
                .setTitle(title)
                .setPositiveButton("YES") { dialogInterface, i ->
                    confirmClosure(dialogInterface)
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    closeClosure(dialog)
                }.create()
                .show()
        }
    }
}
