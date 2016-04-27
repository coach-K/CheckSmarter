package com.andela.checksmarter.activities

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.andela.checksmarter.R
import com.andela.checksmarter.fragments.AlarmActivityFragment
import com.andela.checksmarter.fragments.DetailActivityFragment
import com.andela.checksmarter.model.AutoIncrement
import com.andela.checksmarter.model.CheckSmarterDup
import com.andela.checksmarter.model.CheckSmarterTaskDup
import com.andela.checksmarter.utilities.Intents
import com.andela.checksmarter.views.CheckSmarterTaskView
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * Created by CodeKenn on 20/04/16.
 */
class DetailActivity : AppCompatActivity() {
    val CHECK_SMARTER = "checksmarter"
    private val realm = Realm.getDefaultInstance()

    object callback : AlarmActivityFragment.AlarmCallback {
        override fun onCancelled() {
        }

        private var mSelectedDate: SelectedDate? = null
        private var mHour: Int = 0
        private var mMinute: Int = 0
        private var mRecurrenceOption: String = ""
        private var mRecurrenceRule: String = ""

        override fun onDateTimeRecurrenceSet(selectedDate: SelectedDate?,
                                             hourOfDay: Int, minute: Int,
                                             recurrenceOption: SublimeRecurrencePicker.RecurrenceOption?,
                                             recurrenceRule: String?) {
            mSelectedDate = selectedDate
            mHour = hourOfDay
            mMinute = minute
            mRecurrenceOption = if (recurrenceOption != null)
                recurrenceOption.name
            else
                "n/a"
            mRecurrenceRule = recurrenceRule ?: "n/a"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var checkSmarter: CheckSmarterDup? = Intents().getExtra(this, CHECK_SMARTER)
        if (checkSmarter != null) {
            helloTitle.setText(checkSmarter.title)
        }

        taskSend.setOnClickListener {
            var task = taskCompose.text.toString()
            if (task.isNotEmpty()) {
                getDetailFragment().postCheckSmarterTask(task)
                taskCompose.text.clear()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_alarm) {
            setAlarm()
        } else if (id == android.R.id.home) {
            setCheckSmarterTitle()
        }

        return super.onOptionsItemSelected(item)
    }

    internal fun getOptions(): Pair<Boolean, SublimeOptions> {
        val options = SublimeOptions()
        var displayOptions = 0

        displayOptions = displayOptions or SublimeOptions.ACTIVATE_RECURRENCE_PICKER
        displayOptions = displayOptions or SublimeOptions.ACTIVATE_TIME_PICKER
        displayOptions = displayOptions or SublimeOptions.ACTIVATE_DATE_PICKER

        options.pickerToShow = SublimeOptions.Picker.REPEAT_OPTION_PICKER
        options.pickerToShow = SublimeOptions.Picker.TIME_PICKER
        options.pickerToShow = SublimeOptions.Picker.DATE_PICKER
        options.setDisplayOptions(displayOptions)

        options.setCanPickDateRange(false)
        return Pair(if (displayOptions != 0) java.lang.Boolean.TRUE else java.lang.Boolean.FALSE, options)
    }

    private fun setAlarm() {
        val pickerFrag = AlarmActivityFragment()
        pickerFrag.setCallback(callback)

        val optionsPair = getOptions()

        if (!optionsPair.first) {
            Toast.makeText(this, "No pickers activated", Toast.LENGTH_SHORT).show()
            return
        }

        val bundle = Bundle()
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second)
        pickerFrag.arguments = bundle

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0)
        pickerFrag.show(supportFragmentManager, "SUBLIME_PICKER")
    }

    private fun setCheckSmarterTitle() {
        realm.beginTransaction()
        getDetailFragment().getCheckSmarter().title = helloTitle.text.toString()
        realm.commitTransaction()
    }

    fun getDetailFragment(): DetailActivityFragment {
        return supportFragmentManager.fragments[0] as DetailActivityFragment
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setCheckSmarterTitle()
    }
}
