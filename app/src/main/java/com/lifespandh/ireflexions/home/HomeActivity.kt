package com.lifespandh.ireflexions.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseActivity
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.ui.toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    private val TIME_INTERVAL = 2000
    private var mBackPressed: Long = 0

    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun init() {
        setListeners()
    }

    private fun setListeners() {
        back_arrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupFragment(fragment: Fragment = HomeFragment.newInstance()) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.add(R.id.frameContainer, fragment)
//        transaction.commit()
    }

    override fun onBackPressed() {
        logE("called ${navController.currentDestination}")

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                finishAffinity()
                super.onBackPressed();
                return;
            } else {
                toast("Tap back button in order to exit")
            }
            mBackPressed = System.currentTimeMillis();
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, HomeActivity::class.java)
    }
}