package com.kelly.eoin.kotlinbaseexample.ui

interface Presenter<V : MvpView> {

    fun attachView(mvpView: V)
    fun detachView()
}
