package com.andela.checksmarter.utilities


import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast

/**
 * Created by CodeKenn on 02/05/16.
 */
object MsgBox {
    private var toast: Toast? = null
    private var snackbar: Snackbar? = null

    fun show(context: Context, message: String) {
        if (toast != null) {
            toast!!.cancel()
        }

        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast!!.show()
    }

    fun show(view: View, message: String, action: String, clickListener: View.OnClickListener) {
        if (snackbar != null) {
            snackbar!!.dismiss()
        }

        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(action, clickListener)
        snackbar!!.show()
    }
}
