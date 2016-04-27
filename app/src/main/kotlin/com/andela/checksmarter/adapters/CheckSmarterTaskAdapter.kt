package com.andela.checksmarter.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andela.checksmarter.R
import com.andela.checksmarter.model.CheckSmarterTaskDup
import com.andela.checksmarter.model.CheckSmarterTaskJava
import com.andela.checksmarter.utilities.ItemTouchHelperCallback
import com.andela.checksmarter.views.CheckSmarterTaskItemView
import io.realm.Realm
import io.realm.RealmList
import rx.functions.Action1
import java.util.*

/**
 * Created by CodeKenn on 20/04/16.
 */
class CheckSmarterTaskAdapter(val checkSmarterTaskClickListener: CheckSmarterTaskAdapter.CheckSmarterTaskClickListener) :
        RecyclerView.Adapter<CheckSmarterTaskAdapter.ViewHolder>(), Action1<RealmList<CheckSmarterTaskJava>> {
    interface CheckSmarterTaskClickListener {
        fun onCheckSmarterTaskClick(checkSmarterTask: CheckSmarterTaskJava)
        fun onCheckSmarterTaskLongClick(checkSmarterTask: CheckSmarterTaskJava)
    }

    private val realm = Realm.getDefaultInstance()
    private var checkSmarterTasks = emptyList<CheckSmarterTaskJava>()

    init {
        setHasStableIds(true)
    }

    override fun call(checkSmarterTasks: RealmList<CheckSmarterTaskJava>) {
        this.checkSmarterTasks = checkSmarterTasks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.check_smarter_task_item_view, parent, false) as CheckSmarterTaskItemView
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(checkSmarterTasks[position])
    }

    override fun getItemCount(): Int {
        return checkSmarterTasks.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class ViewHolder(val checkSmarterTaskItemView: CheckSmarterTaskItemView) :
            RecyclerView.ViewHolder(checkSmarterTaskItemView) {
        init {
            this.checkSmarterTaskItemView.setOnClickListener {
                val checkSmarterTask = checkSmarterTasks[adapterPosition]

                realm.beginTransaction()
                checkSmarterTask.isCheck = !checkSmarterTask.isCheck
                realm.commitTransaction()

                notifyItemChanged(adapterPosition)
                //TODO: Play pop sound
                checkSmarterTaskClickListener.onCheckSmarterTaskClick(checkSmarterTask)
            }

            this.checkSmarterTaskItemView.setOnLongClickListener {
                val checkSmarterTask = checkSmarterTasks[adapterPosition]
                checkSmarterTaskClickListener.onCheckSmarterTaskLongClick(checkSmarterTask)
                true
            }
        }

        fun bindTo(checkSmarterTask: CheckSmarterTaskJava) {
            checkSmarterTaskItemView.bindTo(checkSmarterTask)
            //checkSmarterTaskItemView.visibility = View.VISIBLE
        }
    }
}