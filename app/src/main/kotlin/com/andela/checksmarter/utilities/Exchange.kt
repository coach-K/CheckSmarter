package com.andela.checksmarter.utilities

import com.andela.checksmarter.model.CheckSmarterDup
import com.andela.checksmarter.model.CheckSmarterJava
import com.andela.checksmarter.model.CheckSmarterTaskDup
import com.andela.checksmarter.model.CheckSmarterTaskJava
import io.realm.Realm

/**
 * Created by CodeKenn on 21/04/16.
 */
class Exchange {
    fun getParcelableCheckSmarter(checkSmarter: CheckSmarterJava): CheckSmarterDup {
        var newCheckSmarter = CheckSmarterDup()
        newCheckSmarter.id = checkSmarter.id
        newCheckSmarter.title = checkSmarter.title
        newCheckSmarter.isCheck = checkSmarter.isCheck
        newCheckSmarter.isAlarm = checkSmarter.isAlarm
        newCheckSmarter.alarmValue = checkSmarter.alarmValue
        newCheckSmarter.timeValue = checkSmarter.timeValue

        checkSmarter.tasks.forEach {
            var newCheckSmarterTask = CheckSmarterTaskDup()
            newCheckSmarterTask.id = it.id
            newCheckSmarterTask.title = it.title
            newCheckSmarterTask.isCheck = it.isCheck
            newCheckSmarter.tasks.add(newCheckSmarterTask)
        }

        return newCheckSmarter
    }

    fun getRealmCheckSmarter(checkSmarter: CheckSmarterDup): CheckSmarterJava {
        var newCheckSmarter = CheckSmarterJava()
        newCheckSmarter.id = checkSmarter.id
        newCheckSmarter.title = checkSmarter.title
        newCheckSmarter.isCheck = checkSmarter.isCheck
        newCheckSmarter.isAlarm = checkSmarter.isAlarm
        newCheckSmarter.alarmValue = checkSmarter.alarmValue
        newCheckSmarter.timeValue = checkSmarter.timeValue

        checkSmarter.tasks.forEach {
            var newCheckSmarterTask = CheckSmarterTaskJava()
            newCheckSmarterTask.id = it.id
            newCheckSmarterTask.title = it.title
            newCheckSmarterTask.isCheck = it.isCheck
            newCheckSmarter.tasks.add(newCheckSmarterTask)
        }

        return newCheckSmarter
    }
}