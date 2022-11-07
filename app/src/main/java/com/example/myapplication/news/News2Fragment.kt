package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.myapplication.databinding.FragmentNews2Binding
import com.example.myapplication.databinding.FragmentNewsBinding

class News2Fragment : Fragment(R.layout.fragment_news2) {
    private lateinit var binding: FragmentNews2Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNews2Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            this.btnNext2.setOnClickListener{
                findNavController().navigate(R.id.action_news2Fragment_to_news3Fragment)
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
        fun newInstance() = News2Fragment()

    }
}