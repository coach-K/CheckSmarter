package com.andela.checksmarter.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.andela.checksmarter.R
import com.andela.checksmarter.fragments.MainActivityFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        newCheckSmarter.setOnClickListener {
            getFragment().createNewCheckSmarter()
            Log.d("HELLO", "CREATE}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_speak) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun getFragment(): MainActivityFragment {
        return supportFragmentManager.fragments[0] as MainActivityFragment
    }
}
