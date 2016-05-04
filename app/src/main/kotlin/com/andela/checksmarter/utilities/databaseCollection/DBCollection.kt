package com.andela.checksmarter.utilities.databaseCollection

import com.andela.checksmarter.model.AutoIncrement
import com.andela.checksmarter.utilities.alarm.ReminderSet
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import java.util.*

/**
 * Created by CodeKenn on 29/04/16.
 */
class DBCollection {

    fun <T : RealmObject> createOrUpdate(`realmObject`: T) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(realmObject)
        realm.commitTransaction()
        realm.close()
    }

    fun updateObject(realmFunc: () -> Unit) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realmFunc()
        realm.commitTransaction()
        realm.close()
    }

    operator fun <M : RealmObject> get(inst: Class<M>): RealmResults<M> {
        val realm = Realm.getDefaultInstance()
        return realm.where(inst).findAll()
    }

    fun <M : RealmObject> add(inst: M) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(inst)
        realm.commitTransaction()
        realm.close()
    }

    fun <M : RealmObject> remove(inst: M) {
        this.updateObject {
            inst.removeFromRealm()
        }
    }

    fun <M : RealmResults<RealmObject>> remove(inst: M) {
        this.updateObject {
            inst.clear()
        }
    }

    fun <M : RealmObject> findById(clazz: Class<M>, key: String, value: Int): M {
        val realm = Realm.getDefaultInstance()
        return realm.where(clazz).equalTo(key, value).findFirst()
    }

    fun <M : RealmObject> findAll(clazz: Class<M>): rx.Observable<RealmResults<M>> {
        val realm = Realm.getDefaultInstance()
        return realm.where(clazz).findAllAsync().asObservable()
    }

    fun getNextInt(clazz: Class<out RealmObject>) = AutoIncrement().initialize(clazz).nextId.toInt()


}
