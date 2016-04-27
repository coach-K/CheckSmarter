package com.andela.checksmarter.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andela.checksmarter.R
import com.andela.checksmarter.views.CheckSmarterView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivityFragment : Fragment() {

    var checkSmarterFragmentInnerView: CheckSmarterView by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkSmarterFragmentInnerView = view.findViewById(R.id.checkSmarterFragmentInnerView) as CheckSmarterView
    }

    fun createNewCheckSmarter() {
        checkSmarterFragmentInnerView.createNewCheckSmarter()
    }
}
