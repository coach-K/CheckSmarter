package com.andela.checksmarter.views

import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Toast
import com.andela.checksmarter.Extensions.createCheckSmarter
import com.andela.checksmarter.activities.DetailActivity
import com.andela.checksmarter.activities.MainActivity
import com.andela.checksmarter.adapters.CheckSmarterAdapter
import com.andela.checksmarter.model.CheckSmarterJava
import com.andela.checksmarter.model.CheckSmarterTaskJava
import com.andela.checksmarter.utilities.Exchange
import com.andela.checksmarter.utilities.MsgBox
import com.andela.checksmarter.utilities.databaseCollection.DBCollection
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_main.view.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription
import kotlin.properties.Delegates

/**
 * Created by CodeKenn on 18/04/16.
 */
class CheckSmarterView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), CheckSmarterAdapter.CheckSmarterClickListener {
    val CHECK_SMARTER = "checksmarter"
    private val checkSmarterAdapter = CheckSmarterAdapter(this)
    private val subscriptions = CompositeSubscription()
    private val dbCollection = DBCollection()

    override fun onCheckSmarterClick(checkSmarter: CheckSmarterJava) {
        var newCheckSmarter = Exchange().getParcelableCheckSmarter(checkSmarter)

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(CHECK_SMARTER, newCheckSmarter)
        context.startActivity(intent)

        MsgBox.show(context, "ID: ${checkSmarter.id}")
    }

    fun createNewCheckSmarter() {
        val intent = Intent(context, DetailActivity::class.java)
        context.startActivity(intent)
    }

    override fun onCheckSmarterLongClick(checkSmarter: CheckSmarterJava) {
        MsgBox.show(context, "LONG CLICK ID: ${checkSmarter.id}")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        checkSmarterView.layoutManager = GridLayoutManager(context, 2)
        checkSmarterView.adapter = checkSmarterAdapter
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val publishSubject: PublishSubject<CheckSmarterJava>
        publishSubject = PublishSubject.create()

        val result = publishSubject
                .flatMap(checkSmarterSearch)
                .observeOn(AndroidSchedulers.mainThread())
                .share()

        subscriptions.add(result
                .subscribe(checkSmarterAdapter))

        var result2 = publishSubject
                .flatMap(checkSmarterChecked)
                .observeOn(AndroidSchedulers.mainThread())
                .share()


        subscriptions.add(result2
                .subscribe(context as MainActivity))

        post { publishSubject.onNext(CheckSmarterJava(0)) }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscriptions.unsubscribe()
    }

    private val checkSmarterSearch = Func1<CheckSmarterJava, Observable<RealmResults<CheckSmarterJava>>> { checkSmarter ->
        dbCollection.findAll(CheckSmarterJava::class.java)
    }

    private val checkSmarterChecked = Func1<CheckSmarterJava, Observable<RealmResults<CheckSmarterJava>>> { checkSmarter ->
        dbCollection.findByChecked(CheckSmarterJava::class.java, "check", true)
    }
}