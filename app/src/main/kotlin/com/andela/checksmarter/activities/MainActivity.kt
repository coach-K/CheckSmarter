package com.andela.checksmarter.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.andela.checksmarter.R
import com.andela.checksmarter.fragments.MainActivityFragment
import com.andela.checksmarter.model.CheckSmarterJava
import com.andela.checksmarter.utilities.texttospeech.TextToSpeechManager
import kotlinx.android.synthetic.main.activity_main.*
import rx.functions.Action1
import java.util.*

class MainActivity : AppCompatActivity(), Action1<List<CheckSmarterJava>> {
    override fun call(t: List<CheckSmarterJava>?) {
        var theTasks = ArrayList<String>()
        t?.forEach {
            it.tasks.forEach {
                theTasks.add(it.title)
            }
        }
        tm?.lists = theTasks.toTypedArray()
    }

    var tm: TextToSpeechManager? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        newCheckSmarter.setOnClickListener {
            getFragment().createNewCheckSmarter()
        }

        tm = SingleTextToSpeechManager.getInstance(applicationContext);

        //populateList();
    }

    private fun populateList() {
        tm?.lists = arrayOf("mac book", "box");
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_speak) {
            if (tm != null) {
                tm!!.talk()
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun getFragment(): MainActivityFragment {
        return supportFragmentManager.fragments[0] as MainActivityFragment
    }
}
