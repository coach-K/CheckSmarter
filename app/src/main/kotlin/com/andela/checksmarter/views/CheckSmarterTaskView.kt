package com.andela.checksmarter.views

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.andela.checksmarter.adapters.CheckSmarterTaskAdapter
import com.andela.checksmarter.model.*
import com.andela.checksmarter.utilities.*
import com.andela.checksmarter.utilities.databaseCollection.DBCollection
import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.fragment_detail.view.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by CodeKenn on 20/04/16.
 */
class CheckSmarterTaskView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), CheckSmarterTaskAdapter.CheckSmarterTaskClickListener {
    val CHECK_SMARTER = "checksmarter"
    val checkSmarterTaskAdapter = CheckSmarterTaskAdapter(this)
    val subscriptions = CompositeSubscription()
    var currentCheckSmarter: CheckSmarterJava? = null
    var publishSubject: PublishSubject<CheckSmarterTaskJava> by Delegates.notNull()
    val dbCollection = DBCollection()

    private fun initializeComponents() {
        checkSmarterTaskView.layoutManager = CheckSmarterLinearLayoutManager(context)
        checkSmarterTaskView.adapter = checkSmarterTaskAdapter
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initializeComponents()

        setCheckSmarter()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        publishSubject = PublishSubject.create()

        val result = publishSubject
                .flatMap(checkSmarterTaskSearch)
                .observeOn(AndroidSchedulers.mainThread())
                .share()

        subscriptions.add(result
                .subscribe(checkSmarterTaskAdapter))

        post { publishSubject.onNext(CheckSmarterTaskJava(0)) }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscriptions.unsubscribe()

        updateChanges()
    }

    override fun onCheckSmarterTaskClick(checkSmarterTask: CheckSmarterTaskJava) {
        Log.d("TASK", checkSmarterTask.id.toString())
    }

    override fun onCheckSmarterTaskLongClick(checkSmarterTask: CheckSmarterTaskJava) {
        currentCheckSmarter?.tasks?.remove(checkSmarterTask)
        checkSmarterTaskAdapter.notifyDataSetChanged()
    }

    private fun setCheckSmarter() {
        var checkSmarter: CheckSmarterDup? = Intents().getExtra(context, CHECK_SMARTER)
        if (checkSmarter != null) {
            this.currentCheckSmarter = Exchange().getRealmCheckSmarter(checkSmarter)
        } else {
            this.currentCheckSmarter = CheckSmarterJava()
            this.currentCheckSmarter?.id = dbCollection.getNextInt(CheckSmarterJava::class.java)
        }
    }

    private val checkSmarterTaskSearch = Func1<CheckSmarterTaskJava, Observable<RealmList<CheckSmarterTaskJava>>> { checkSmarterTask ->
        Observable.just(currentCheckSmarter?.tasks!!).subscribeOn(Schedulers.io())
    }

    fun postCheckSmarterTask(title: String) {
        dbCollection.updateObject {
            var checkSmarterTask = CheckSmarterTaskJava()
            checkSmarterTask.id = dbCollection.getNextInt(CheckSmarterTaskJava::class.java)
            checkSmarterTask.title = title
            currentCheckSmarter?.tasks?.add(checkSmarterTask)
        }

        checkSmarterTaskAdapter.notifyItemInserted(0)
    }

    private fun updateChanges() {
        dbCollection.createOrUpdate(this.currentCheckSmarter!!)
    }
}