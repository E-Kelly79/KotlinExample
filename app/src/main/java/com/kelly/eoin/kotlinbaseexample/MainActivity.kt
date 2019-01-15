package com.kelly.eoin.kotlinbaseexample

import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.kelly.eoin.kotlinbaseexample.ui.BaseActivity

class MainActivity : BaseActivity() {

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = Bundle()
        baseActivity.loadScreenOneFragment(args, false)
    }
}
