package com.andela.checksmarter.adapters

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andela.checksmarter.R
import com.andela.checksmarter.model.CheckSmarterJava
import com.andela.checksmarter.views.CheckSmarterItemView
import io.realm.Realm
import kotlinx.android.synthetic.main.check_smarter_item_view.view.*

import java.util.Collections

import rx.functions.Action1
import kotlin.properties.Delegates

/**
 * Created by CodeKenn on 18/04/16.
 */
class CheckSmarterAdapter(private val checkSmarterClickListener: CheckSmarterAdapter.CheckSmarterClickListener) :
        RecyclerView.Adapter<CheckSmarterAdapter.ViewHolder>(), Action1<List<CheckSmarterJava>> {
    interface CheckSmarterClickListener {
        fun onCheckSmarterClick(checkSmarter: CheckSmarterJava)
        fun onCheckSmarterLongClick(checkSmarter: CheckSmarterJava)
    }

    private val realm = Realm.getDefaultInstance()
    private var checkSmarters = emptyList<CheckSmarterJava>()
    private var context: Context by Delegates.notNull()

    init {
        setHasStableIds(true)
    }

    override fun call(checkSmarters: List<CheckSmarterJava>) {
        this.checkSmarters = checkSmarters
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        this.context = viewGroup.context
        val view = LayoutInflater.from(viewGroup.context).inflate(
                R.layout.check_smarter_item_view, viewGroup, false) as CheckSmarterItemView
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bindTo(checkSmarters[i])
    }

    override fun getItemCount(): Int {
        return checkSmarters.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun displayDialog(position: Int) {
        val view = LayoutInflater.from(this.context).inflate(R.layout.options_layout, null)

        val alertDialog = AlertDialog.Builder(this.context)
                .setView(view)
                .create()
        alertDialog.show()

        val delete = view.findViewById(R.id.delete)
        delete.setOnClickListener {
            realm.beginTransaction()
            checkSmarters.get(position).removeFromRealm()
            realm.commitTransaction()
            notifyItemRemoved(position)
            Log.d("HELLO", "DELETE ${position}")
            alertDialog.dismiss()
        }
    }

    inner class ViewHolder(val checkSmarterItemView: CheckSmarterItemView) :
            RecyclerView.ViewHolder(checkSmarterItemView) {
        init {
            this.checkSmarterItemView.setOnClickListener {
                val checkSmarter = checkSmarters[adapterPosition]
                checkSmarterClickListener.onCheckSmarterClick(checkSmarter)
            }

            this.checkSmarterItemView.setOnLongClickListener {
                val checkSmarter = checkSmarters[adapterPosition]

                realm.beginTransaction()
                checkSmarter.isCheck = !checkSmarter.isCheck
                realm.commitTransaction()

                notifyItemChanged(0)
                //TODO: Play pop sound
                checkSmarterClickListener.onCheckSmarterLongClick(checkSmarter)
                true
            }

            this.checkSmarterItemView.checkSmarterOptions.setOnClickListener {
                displayDialog(adapterPosition)
            }
        }

        fun bindTo(checkSmarter: CheckSmarterJava) {
            checkSmarterItemView.bindTo(checkSmarter)
        }
    }
}
