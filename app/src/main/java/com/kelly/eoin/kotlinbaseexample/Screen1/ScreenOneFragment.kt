package com.kelly.eoin.kotlinbaseexample.Screen1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.kelly.eoin.kotlinbaseexample.R
import com.kelly.eoin.kotlinbaseexample.ui.BaseFragment

class ScreenOneFragment : BaseFragment() {

    lateinit var mView: View

    var mButton: Button? = null
    var mText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.screen_one_fragment, container, false)
        baseActivity.showToolbar()
        mButton = mView.findViewById(R.id.test_btn) as Button
        mText = mView.findViewById(R.id.textView) as TextView
        mButton?.setOnClickListener {
            mText?.text = "Hello"
            baseActivity.loadScreenTwoFragment(baseArguments!!, false)
        }

        baseActivity.mivToolbarPrimary?.setOnClickListener {
            baseActivity.showNavDrawer()
        }

        return mView
    }
}
