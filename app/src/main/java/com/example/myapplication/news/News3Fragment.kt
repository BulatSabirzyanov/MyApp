package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.navOptions

class News3Fragment : Fragment(R.layout.fragment_news3) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navOptions {
            anim {
                enter = androidx.navigation.ui.R.anim.nav_default_enter_anim
                popEnter = androidx.navigation.ui.R.anim.nav_default_pop_enter_anim
                popExit = androidx.navigation.ui.R.anim.nav_default_pop_exit_anim
                exit = androidx.navigation.ui.R.anim.nav_default_exit_anim
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = News3Fragment()

    }
}