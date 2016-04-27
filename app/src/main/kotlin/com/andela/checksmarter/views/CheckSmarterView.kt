package com.andela.checksmarter.views

import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Toast
import com.andela.checksmarter.Extensions.createCheckSmarter
import com.andela.checksmarter.activities.DetailActivity
import com.andela.checksmarter.adapters.CheckSmarterAdapter
import com.andela.checksmarter.model.CheckSmarterJava
import com.andela.checksmarter.model.CheckSmarterTaskJava
import com.andela.checksmarter.utilities.Exchange
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

    private var realm: Realm by Delegates.notNull()

    override fun onCheckSmarterClick(checkSmarter: CheckSmarterJava) {
        var newCheckSmarter = Exchange().getParcelableCheckSmarter(checkSmarter)

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(CHECK_SMARTER, newCheckSmarter)
        context.startActivity(intent)

        Toast.makeText(context, "ID: ${checkSmarter.id}", Toast.LENGTH_SHORT).show()
    }

    fun createNewCheckSmarter() {
        val intent = Intent(context, DetailActivity::class.java)
        context.startActivity(intent)
    }

    override fun onCheckSmarterLongClick(checkSmarter: CheckSmarterJava) {
        Toast.makeText(context, "LONG CLICK ID: ${checkSmarter.id}", Toast.LENGTH_SHORT).show()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        checkSmarterView.layoutManager = GridLayoutManager(context, 2)
        checkSmarterView.adapter = checkSmarterAdapter
    }

    /*private fun populateRealm() {
        realm.createCheckSmarter(CheckSmarterJava(1, "Office CheckSmarter", true, true, 454, 231, createList(0, "Laptop")))
        realm.createCheckSmarter(CheckSmarterJava(2, "Church CheckSmarter", false, false, 234, 643, createList(0, "Laptop")))
        realm.createCheckSmarter(CheckSmarterJava(3, "Swimming CheckSmarter", false, true, 345, 234, createList(0, "Laptop")))
    }

    private fun createList(id: Int, title: String): RealmList<CheckSmarterTaskJava> {
        val one = CheckSmarterTaskJava(id, "${title}", true)
        val two = CheckSmarterTaskJava(id+1, "${title + 1}", false)
        val three = CheckSmarterTaskJava(id+2, "${title + 2}", false)

        return RealmList(one, two, three)
    }*/

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        realm = Realm.getDefaultInstance()

        //populateRealm()

        val publishSubject: PublishSubject<CheckSmarterJava>
        publishSubject = PublishSubject.create()

        val result = publishSubject
                .flatMap(checkSmarterSearch)
                .observeOn(AndroidSchedulers.mainThread())
                .share()

        subscriptions.add(result
                .subscribe(checkSmarterAdapter))

        post { publishSubject.onNext(CheckSmarterJava(12)) }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscriptions.unsubscribe()
        realm.close()
    }

    private val checkSmarterSearch = Func1<CheckSmarterJava, Observable<RealmResults<CheckSmarterJava>>> { checkSmarter ->
        realm.where(CheckSmarterJava::class.java).findAllAsync().asObservable()
        //val result = realm.where(CheckSmarterJava::class.java).findAllAsync()
    }
}