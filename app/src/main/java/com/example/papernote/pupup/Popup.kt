package com.example.papernote.pupup

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.papernote.R
class Popup : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.fragment_popup, null))
                // Add action buttons

                .setPositiveButton("add",
                    DialogInterface.OnClickListener { dialog, id ->
                        // sign in the user ...

                    })
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog().cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}