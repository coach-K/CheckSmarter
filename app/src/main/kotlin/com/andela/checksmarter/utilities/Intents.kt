package com.andela.checksmarter.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.andela.checksmarter.R
import com.andela.checksmarter.model.CheckSmarterDup

/**
 * Created by CodeKenn on 20/04/16.
 */
class Intents {
    /**
     * Attempt to launch the supplied [Intent]. Queries on-device packages before launching and
     * will display a simple message if none are available to handle it.
     */
    fun maybeStartActivity(context: Context, intent: Intent): Boolean {
        return maybeStartActivity(context, intent, false)
    }

    /**
     * Attempt to launch Android's chooser for the supplied [Intent]. Queries on-device
     * packages before launching and will display a simple message if none are available to handle
     * it.
     */
    fun maybeStartChooser(context: Context, intent: Intent): Boolean {
        return maybeStartActivity(context, intent, true)
    }

    fun getExtra(context: Context, key: String): CheckSmarterDup? {
        val intent = (context as Activity).intent
        return intent?.extras?.getParcelable(key)?:null
    }

    private fun maybeStartActivity(context: Context, intent: Intent, chooser: Boolean): Boolean {
        var mIntent = intent
        if (hasHandler(context, mIntent)) {
            if (chooser) {
                mIntent = Intent.createChooser(mIntent, null)
            }
            context.startActivity(mIntent)
            return true
        } else {
            Toast.makeText(context, R.string.no_intent_handler, Toast.LENGTH_SHORT).show()
            return false
        }
    }

    /**
     * Queries on-device packages for a handler for the supplied [Intent].
     */
    private fun hasHandler(context: Context, intent: Intent): Boolean {
        val handlers = context.packageManager.queryIntentActivities(intent, 0)
        return !handlers.isEmpty()
    }

    private fun Intents() {
        throw AssertionError("No instances.")
    }
}