package com.kelly.eoin.kotlinbaseexample.ui

import android.content.Context
import android.graphics.Color
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.annotation.StringDef
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import com.kelly.eoin.kotlinbaseexample.R
import com.kelly.eoin.kotlinbaseexample.Screen1.ScreenOneFragment
import com.kelly.eoin.kotlinbaseexample.screen2.ScreenTwoFragment
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

open class BaseActivity : AppCompatActivity(), MvpView {
    /**
     * Get current fragment key
     *
     * @return String Current Fragment
     */
    /**
     * Set current fragment
     */
    @get:MainFragments
    var currentFragmentKey: String? = SCREEN1
    /**
     * Get a fragment
     * @return fragment
     */
    var fragment: BaseFragment? = null
        private set
    /**
     * Track Bundle on BackPressed
     *
     * @return Bundle
     */
    var bundle: Bundle? = null

    var mFragmentContainer: FrameLayout? = null
    var mDrawerLayout: DrawerLayout? = null
    var mNavView: NavigationView? = null
    var mMainContent: RelativeLayout? = null
    var mToolbar: Toolbar? = null
    var mivToolbarPrimary: ImageView? = null

    override
    val context: Context
        get() = applicationContext

    override
    val baseActivity: BaseActivity
        get() = this

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_layout)
        mToolbar = findViewById(R.id.Toolbar)
        mFragmentContainer = findViewById(R.id.fragment_container)
        mDrawerLayout = findViewById(R.id.layout_drawer)
        mNavView = findViewById(R.id.left_drawer)
        mMainContent = findViewById(R.id.layout_main_content)
        mivToolbarPrimary = findViewById(R.id.home)
        initNavDrawer()
    }

    public override fun onResume() {
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    private fun initNavDrawer() {
        //TODO This needs to be done dynamically
        //        setPairedDeviceLabel("Tablet 1234");
        //        setPairedDeviceStatus(getString(R.string.label_status_connected));
        //        setLastSyncTimeLabel("2 Mins");

        mDrawerLayout?.setScrimColor(Color.TRANSPARENT)
        mNavView?.outlineProvider = null
        mDrawerLayout?.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val moveFactor = mNavView!!.width * slideOffset
                mMainContent!!.translationX = moveFactor
                mMainContent!!.translationY = 80f * slideOffset
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })
    }

    fun showNavDrawer() {
        closeSoftKeyboard()
        mDrawerLayout!!.openDrawer(Gravity.LEFT)
    }

    protected fun closeNavDrawer() {
        mDrawerLayout!!.closeDrawer(Gravity.LEFT)
    }

    fun closeSoftKeyboard() {
        // Check if no view has focus:
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Lock Nav Drawer for Fragments that do not allow Nav Drawer access.
     */
    fun lockNavDrawer() {
        mDrawerLayout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    /**
     * Unlock Nav Drawer for Fragments that allow Nav Drawer access.
     */
    fun unlockNavDrawer() {
        mDrawerLayout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    /**
     * Hide Toolbar for Fragments that do not require it.
     */
    fun hideToolbar() {
        mToolbar!!.visibility = View.GONE
    }

    /**
     * Show Toolbar when required.
     */
    fun showToolbar() {
        mToolbar!!.visibility = View.VISIBLE
    }

    fun getFragment(
        args: Bundle, addToBackStack: Boolean, fragment: BaseFragment,
        fragmentKey: String
    ): FragmentTransaction {
        return getFragmentAnim(
            args, addToBackStack, fragment, fragmentKey, android.R.anim.fade_in,
            android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out
        )
    }

    fun getFragmentAnim(
        args: Bundle?, addToBackStack: Boolean, fragment: BaseFragment,
        fragmentKey: String, animEnter: Int, animExit: Int, animBackEnter: Int, animBackExit: Int
    ): FragmentTransaction {
        this.fragment = fragment
        val ft = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        ft.setCustomAnimations(animEnter, animExit, animBackEnter, animBackExit)
        if (args != null) fragment.arguments = args
        ft.replace(R.id.fragment_container, fragment, fragmentKey)
        if (addToBackStack && currentFragmentKey != fragmentKey) ft.addToBackStack(fragment.tag())
        currentFragmentKey = fragmentKey
        return ft
    }

    /**
     * Load fragment based on Tag
     */
    fun loadFragment(args: Bundle, fragment: String, addToBackStack: Boolean) {
        when (fragment) {
            SCREEN1 -> {
                currentFragmentKey = SCREEN1
                loadScreenOneFragment(args, addToBackStack)
            }
            SCREEN2 -> {
                currentFragmentKey = SCREEN2
                loadScreenTwoFragment(args, addToBackStack)
            }
        }
    }

    /**
     * Load Menu Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadScreenOneFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, ScreenOneFragment(), SCREEN1).commit()
    }

    /**
     * Load Card Fragment
     *
     * @param args           Bundle
     * @param addToBackStack Boolean
     */
    fun loadScreenTwoFragment(args: Bundle, addToBackStack: Boolean) {
        getFragment(args, addToBackStack, ScreenTwoFragment(), SCREEN2).commit()
    }

    /**
     * Definition of fragments supported
     */
    @StringDef(SCREEN1, SCREEN2)
    @Retention(RetentionPolicy.SOURCE)
    annotation class MainFragments

    override fun onUserInteraction() {
        super.onUserInteraction()
        if (fragment != null) {
            fragment!!.onUserInteraction()
        }
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    fun setToolbarButton(drawable: Int) {
        mivToolbarPrimary!!.setImageDrawable(this.resources.getDrawable(drawable, applicationContext.theme))
    }

    companion object {
        const val SCREEN1 = "SCREEN1"
        const val SCREEN2 = "SCREEN2"

        const val ADD_FRAGMENT_TO_BACKSTACK = true
        const val NOT_ADD_TO_BACKSTACK = false
    }
}
