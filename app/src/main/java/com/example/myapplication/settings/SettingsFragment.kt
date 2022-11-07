package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentNewsBinding
import com.example.myapplication.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var  binding : FragmentSettingsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSettingsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            this.btnNextToSettings2.setOnClickListener {
            findNavController().navigate (R.id.action_settingsFragment_to_settings2Fragment )
        }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() = SettingsFragment()

    }
}