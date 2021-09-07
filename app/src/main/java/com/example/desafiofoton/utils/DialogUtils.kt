package com.example.desafiofoton.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface


class DialogUtils {
    companion object {
        fun alertMessage(context: Context, message: String, onClickListener: DialogInterface.OnClickListener? = null) {
            val cancelable = onClickListener == null
            alert(context, message, cancelable, "OK", null, onClickListener)
        }

        fun alert(context: Context, message: String, cancelable: Boolean = true, positiveButtonTittle: String? = null, negativeButtonTittle: String? = null, onPositiveButtonClickListener: DialogInterface.OnClickListener? = null, onNegativeButtonClickListener: DialogInterface.OnClickListener? = null) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage(message)
            builder.setCancelable(cancelable)

            if (positiveButtonTittle != null) {
                builder.setPositiveButton(positiveButtonTittle, onPositiveButtonClickListener)
            }

            if (negativeButtonTittle != null) {
                builder.setNegativeButton(negativeButtonTittle, onNegativeButtonClickListener)
            }

            val alert: AlertDialog = builder.create()
            alert.show()
        }
    }
}