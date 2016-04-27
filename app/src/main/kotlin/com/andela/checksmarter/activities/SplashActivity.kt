package com.andela.checksmarter.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.andela.checksmarter.utilities.alarm.OnBootReceiver
import java.util.*

/**
 * Created by CodeKenn on 14/04/16.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sendBroadcast(Intent(this, OnBootReceiver::class.java))
        Log.i("Splash Screen", "Initialize Alarm - Date: " + Date().toString())

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }
}