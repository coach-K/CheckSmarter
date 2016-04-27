package com.andela.checksmarter.Extensions

import com.andela.checksmarter.model.CheckSmarterJava
import io.realm.Realm

/**
 * Created by CodeKenn on 19/04/16.
 */

fun Realm.createCheckSmarter(checkSmarterJava: CheckSmarterJava) {
    beginTransaction()
    copyToRealmOrUpdate(checkSmarterJava)
    commitTransaction()
}