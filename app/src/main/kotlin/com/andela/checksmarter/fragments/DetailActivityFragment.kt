package com.andela.checksmarter.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andela.checksmarter.R
import com.andela.checksmarter.model.CheckSmarterJava
import com.andela.checksmarter.views.CheckSmarterTaskView
import kotlin.properties.Delegates

/**
 * Created by CodeKenn on 20/04/16.
 */
class DetailActivityFragment : Fragment() {

    var csd: CheckSmarterTaskView by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        csd = view.findViewById(R.id.csDetail) as CheckSmarterTaskView
    }

    fun postCheckSmarterTask(title: String) {
        csd.postCheckSmarterTask(title)
    }

    fun getCheckSmarter(): CheckSmarterJava {
        return csd.currentCheckSmarter!!
    }
}