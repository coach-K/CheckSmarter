package com.andela.checksmarter.views

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.andela.checksmarter.R
import com.andela.checksmarter.model.CheckSmarterJava
import com.andela.checksmarter.utilities.DateFormatter
import kotlinx.android.synthetic.main.check_smarter_item_view.view.*
import java.util.*

/**
 * Created by CodeKenn on 18/04/16.
 */
class CheckSmarterItemView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    fun bindTo(checkSmarter: CheckSmarterJava) {
        checkTotal.text = checkSmarter.tasks.size.toString()
        checkTitle.text = checkSmarter.title
        checkTime.text = Date(checkSmarter.timeValue).time.toString()
        checkTime.visibility = GONE

        if (checkSmarter.isAlarm) {
            checkAlarm.visibility = VISIBLE
            checkAlarm.text = DateFormatter.getReadableTime(checkSmarter.alarmValue)
        } else {
            checkAlarm.visibility = INVISIBLE
        }

        if (checkSmarter.isCheck) {
            checkImageView.setImageResource(R.drawable.check)
            checkTotal.setBackgroundResource(R.drawable.circle)
        } else {
            checkImageView.setImageResource(R.drawable.check_pressed)
            checkTotal.setBackgroundResource(R.drawable.circle_grey)
        }
    }
}