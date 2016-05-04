package com.andela.checksmarter.activities

import android.content.Intent
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
import com.andela.checksmarter.model.CheckSmarterJava
import com.andela.checksmarter.model.CheckSmarterTaskDup
import com.andela.checksmarter.utilities.DateFormatter
import com.andela.checksmarter.utilities.Intents
import com.andela.checksmarter.utilities.MsgBox
import com.andela.checksmarter.utilities.alarm.OnBootReceiver
import com.andela.checksmarter.utilities.alarm.Reminder
import com.andela.checksmarter.utilities.alarm.ReminderSet
import com.andela.checksmarter.utilities.alarm.ReminderManager
import com.andela.checksmarter.utilities.databaseCollection.DBCollection
import com.andela.checksmarter.views.CheckSmarterTaskView
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by CodeKenn on 20/04/16.
 */
class DetailActivity : AppCompatActivity() {
    private val dbCollection = DBCollection()
    private var currentCheckSmarter: CheckSmarterJava? = null
    private var mSelectedDate: SelectedDate? = null
    private var mHour: Int = 0
    private var mMinute: Int = 0
    private var mRecurrenceOption: String = ""
    private var mRecurrenceRule: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.currentCheckSmarter = getDetailFragment().getCheckSmarter()

        updateAlarmView()

        helloTitle.setText(this.currentCheckSmarter!!.title)

        cancelAlarm.setOnClickListener {
            this.currentCheckSmarter!!.isAlarm = false
            cancelReminder()
        }

        taskSend.setOnClickListener {
            var task = taskCompose.text.toString()
            if (task.isNotEmpty()) {
                getDetailFragment().postCheckSmarterTask(task)
                taskCompose.text.clear()
            }
        }
    }

    private fun updateAlarmView() {
        if (this.currentCheckSmarter!!.isAlarm) {
            displayAlarm.visibility = View.VISIBLE
            textAlarm.text = DateFormatter.getReadableDate(currentCheckSmarter!!.alarmValue)
        } else {
            displayAlarm.visibility = View.GONE
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

    fun setReminder(time: Long) {
        if (time > DateFormatter.getCurrentTime()) {
            currentCheckSmarter!!.isAlarm = true
            currentCheckSmarter!!.alarmValue = time

            var reminderSet = ReminderSet(this.currentCheckSmarter!!.id, this.currentCheckSmarter!!.alarmValue)
            dbCollection.add(reminderSet)

            postReminder(ReminderManager.SET_REMINDER, getReminders())

            MsgBox.show(this, "Will remind you at " + DateFormatter.getReadableTime(time))
        } else {
            MsgBox.show(this, "" + DateFormatter.getReadableTime(time) + " is past")
        }
    }

    fun cancelReminder() {
        var rs = dbCollection.findById<ReminderSet>(ReminderSet::class.java, "id", this.currentCheckSmarter!!.id)
        dbCollection.remove(rs)

        postReminder(ReminderManager.CANCEL_REMINDER, Reminder(this.currentCheckSmarter!!.id))

        MsgBox.show(this, "Alarm  " + DateFormatter.getReadableTime(this.currentCheckSmarter!!.alarmValue) + " has been canceled.")
    }

    private fun postReminder(KEY: String, reminder: Reminder) {
        val intent = Intent(this, OnBootReceiver::class.java)
        intent.putExtra(ReminderManager.KEY, KEY)
        intent.putExtra(ReminderManager.VALUE, reminder)
        sendBroadcast(intent)

        updateAlarmView()
    }

    private fun getReminders(): Reminder {
        var reminderSet = dbCollection.get(ReminderSet::class.java)

        val ids = IntArray(reminderSet.size)
        val times = LongArray(reminderSet.size)

        for (i in 0..reminderSet.size - 1) {
            val rs = reminderSet.get(i);
            ids[i] = rs.id
            times[i] = rs.time
        }
        return Reminder(ids, times)
    }

    private fun setAlarm() {
        val pickerFrag = AlarmActivityFragment()
        pickerFrag.setCallback(object : AlarmActivityFragment.AlarmCallback {
            override fun onCancelled() {
            }

            override fun onDateTimeRecurrenceSet(selectedDate: SelectedDate?, hourOfDay: Int, minute: Int, recurrenceOption: SublimeRecurrencePicker.RecurrenceOption?, recurrenceRule: String?) {
                mSelectedDate = selectedDate
                mHour = hourOfDay
                mMinute = minute
                mRecurrenceOption = if (recurrenceOption != null)
                    recurrenceOption.name
                else
                    "n/a"
                mRecurrenceRule = recurrenceRule ?: "n/a"

                mSelectedDate!!.startDate.set(Calendar.HOUR_OF_DAY, mHour)
                mSelectedDate!!.startDate.set(Calendar.MINUTE, mMinute)
                mSelectedDate!!.startDate.set(Calendar.SECOND, 0)
                mSelectedDate!!.startDate.set(Calendar.MILLISECOND, 0)
                var time = mSelectedDate!!.startDate.timeInMillis

                setReminder(time)
                return
            }
        })

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

    private fun setCheckSmarterTitle() {
        dbCollection.updateObject {
            getDetailFragment().getCheckSmarter().title = helloTitle.text.toString()
        }
    }

    fun getDetailFragment(): DetailActivityFragment {
        return supportFragmentManager.fragments[0] as DetailActivityFragment
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setCheckSmarterTitle()
    }
}
