package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.FragmentNewsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsFragment : Fragment(R.layout.fragment_news) {
    private  lateinit var binding: FragmentNewsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            this.btnNext.setOnClickListener{
                findNavController().navigate(R.id.action_newsFragment_to_news2Fragment)
                navOptions {
                    anim {
                        enter = androidx.navigation.ui.R.anim.nav_default_enter_anim
                        popEnter = androidx.navigation.ui.R.anim.nav_default_pop_enter_anim
                        popExit = androidx.navigation.ui.R.anim.nav_default_pop_exit_anim
                        exit = androidx.navigation.ui.R.anim.nav_default_exit_anim
                    }
                }

            }

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = NewsFragment()

    }
}