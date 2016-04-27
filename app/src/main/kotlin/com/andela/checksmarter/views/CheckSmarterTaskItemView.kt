package com.andela.checksmarter.views

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.andela.checksmarter.R
import com.andela.checksmarter.model.CheckSmarterTaskJava
import kotlinx.android.synthetic.main.check_smarter_task_item_view.view.*

/**
 * Created by CodeKenn on 20/04/16.
 */
class CheckSmarterTaskItemView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    fun bindTo(checkSmarterTask: CheckSmarterTaskJava) {
        taskLabel.text = checkSmarterTask.title.first().toString()
        taskTitle.text = checkSmarterTask.title

        if (checkSmarterTask.isCheck) {
            taskCheck.setImageResource(R.drawable.checked)
            taskLabel.background = context.getDrawable(R.drawable.circle)
        } else {
            taskCheck.setImageResource(R.drawable.uncheck)
            taskLabel.background = context.getDrawable(R.drawable.circle_grey)
        }
    }
}