package com.example.desafiofoton.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.desafiofoton.R

class DialogUtils {
    companion object {
        fun showDialogWarning(context: Context) {
            val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_warning, null)
            showDialog(context, contentView, false) { dialog: AlertDialog, id: Int ->
                when (id) {
                    R.id.ok_button -> {
                        Toast.makeText(context, "All Right!", Toast.LENGTH_SHORT).show()
                    }
                    R.id.cancel_button -> {
                        Toast.makeText(context, "Canceled!", Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.dismiss()
            }
        }

        private fun showDialog(
            context: Context,
            contentView: View,
            showCancel: Boolean,
            callback: (dialog: AlertDialog, buttonId: Int) -> Unit
        ): AlertDialog {
            val builder = AlertDialog.Builder(context)
            val dialogView =
                LayoutInflater.from(context)
                    .inflate(R.layout.dialog_container, null) as ConstraintLayout

            // Content
            val contentContainer =
                dialogView.findViewById<ConstraintLayout>(R.id.dialog_content_container) as LinearLayout
            contentContainer.addView(contentView)

            // Dialog
            lateinit var dialog: AlertDialog

            dialogView.findViewById<View>(R.id.cancel_button).isVisible = showCancel
            dialogView.findViewById<View>(R.id.cancel_button).setOnClickListener {
                callback(dialog, R.id.cancel_button)
            }

            dialogView.findViewById<View>(R.id.ok_button).setOnClickListener {
                callback(dialog, R.id.ok_button)
            }

            builder.setView(dialogView)
            dialog = builder.create()

            // Display and return
            dialog.show()
            return dialog
        }
    }
}