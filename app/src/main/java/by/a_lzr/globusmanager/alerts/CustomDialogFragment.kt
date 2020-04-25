package by.a_lzr.globusmanager.alerts

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import by.a_lzr.globusmanager.R

class CustomDialogFragment : DialogFragment() {

    private lateinit var listener: DialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.synchronize_break_info))
                .setMessage(getString(R.string.synchronize_break_confirm))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.alert_action_yes)) { _, _ ->
                    listener.onDialogPositiveClick(this)
                }
                .setNegativeButton(getString(R.string.alert_action_no)) { _, _ ->
                    listener.onDialogNegativeClick(this)
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = activity as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString() + " must implement NoticeDialogListener"
            )
        }
    }

    interface DialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment?)
        fun onDialogNegativeClick(dialog: DialogFragment?)
    }
}