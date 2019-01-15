package com.kelly.eoin.kotlinbaseexample.screen2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.kelly.eoin.kotlinbaseexample.R
import com.kelly.eoin.kotlinbaseexample.ui.BaseFragment

class ScreenTwoFragment : BaseFragment() {

    lateinit var mView: View
    var mButton: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(R.layout.screen_two_fragment, container, false)
        baseActivity.hideToolbar()
        return mView
    }

}
