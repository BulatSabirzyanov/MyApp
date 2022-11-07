package com.example.myapplication.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSettings2Binding
import com.example.myapplication.databinding.FragmentSettingsBinding


class Settings2Fragment : Fragment(R.layout.fragment_settings2) {
    private lateinit var binding : FragmentSettings2Binding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSettings2Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        with(binding){
        this.btnNextToSettings3.setOnClickListener {
            findNavController().navigate(R.id.action_settings2Fragment_to_settings3Fragment)
        }
        }

    }
}

