package com.andela.checksmarter.views

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Toast
import com.andela.checksmarter.adapters.CheckSmarterTaskAdapter
import com.andela.checksmarter.model.*
import com.andela.checksmarter.utilities.*
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
class CheckSmarterTaskView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), CheckSmarterTaskAdapter.CheckSmarterTaskClickListener,
        ItemTouchHelperCallback {
    val CHECK_SMARTER = "checksmarter"
    private val checkSmarterTaskAdapter = CheckSmarterTaskAdapter(this)
    private val subscriptions = CompositeSubscription()
    var currentCheckSmarter: CheckSmarterJava? = null
    var publishSubject: PublishSubject<CheckSmarterTaskJava> by Delegates.notNull()
    var autoInc = 0
    private val realm = Realm.getDefaultInstance()

    override fun onCheckSmarterTaskClick(checkSmarterTask: CheckSmarterTaskJava) {
        Toast.makeText(context, "ID: ${checkSmarterTask.id}", Toast.LENGTH_SHORT).show()
    }

    override fun onCheckSmarterTaskLongClick(checkSmarterTask: CheckSmarterTaskJava) {
        Toast.makeText(context, "LONG CLICK ID: ${checkSmarterTask.id}", Toast.LENGTH_SHORT).show()
        currentCheckSmarter?.tasks?.remove(checkSmarterTask)
        //Toast.makeText(context, "POSITION: ${checkSmarterTask.id}", Toast.LENGTH_SHORT).show()
        checkSmarterTaskAdapter.notifyDataSetChanged()
    }

    private fun setCheckSmarter() {
        var checkSmarter: CheckSmarterDup? = Intents().getExtra(context, CHECK_SMARTER)
        if (checkSmarter != null) {
            this.currentCheckSmarter = Exchange().getRealmCheckSmarter(checkSmarter)
        } else {
            autoInc = getNextInt()
            this.currentCheckSmarter = CheckSmarterJava()
            this.currentCheckSmarter?.id = autoInc
        }
    }

    private fun initializeComponents() {
        checkSmarterTaskView.layoutManager = CheckSmarterLinearLayoutManager(context)
        checkSmarterTaskView.adapter = checkSmarterTaskAdapter

        val itemTouchHelperCallback = SimpleItemTouchHelper(this)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(checkSmarterTaskView)
    }

    override fun onItemSwipedOffScreen(position: Int) {
        currentCheckSmarter?.tasks?.removeAt(position)
        Toast.makeText(context, "POSITION: ${position}", Toast.LENGTH_SHORT).show()
        checkSmarterTaskAdapter.notifyDataSetChanged()
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

        post { publishSubject.onNext(CheckSmarterTaskJava(12)) }
    }

    fun postCheckSmarterTask(title: String) {
        realm.beginTransaction()

        var checkSmarterTask = realm.createObject(CheckSmarterTaskJava::class.java)
        checkSmarterTask.id = getNextTaskInt()
        checkSmarterTask.title = title
        currentCheckSmarter?.tasks?.add(checkSmarterTask)

        realm.commitTransaction()

        checkSmarterTaskAdapter.notifyItemInserted(0)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscriptions.unsubscribe()

        updateChanges()
    }

    private val checkSmarterTaskSearch = Func1<CheckSmarterTaskJava, Observable<RealmList<CheckSmarterTaskJava>>> { checkSmarterTask ->
        Observable.just(currentCheckSmarter?.tasks!!).subscribeOn(Schedulers.io())
    }

    private fun updateChanges() {
        if (this.currentCheckSmarter != null) {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(currentCheckSmarter)
            realm.commitTransaction()
            realm.close()
        }
    }

    fun getNextInt(): Int {
        return AutoIncrement().initialize(CheckSmarterJava::class.java).nextId.toInt()
    }

    fun getNextTaskInt(): Int {
        return AutoIncrement().initialize(CheckSmarterTaskJava::class.java).nextId.toInt()
    }
}