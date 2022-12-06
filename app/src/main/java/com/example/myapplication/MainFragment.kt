package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View

import com.example.myapplication.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            binding.btnStart.setOnClickListener {
                Intent(context?.applicationContext, LocationService::class.java).apply {
                    action = LocationService.ACTION_START
                    context?.startService(this)
                }
            }
            binding.btnStop.setOnClickListener{
                Intent(context?.applicationContext, LocationService::class.java).apply {
                    action = LocationService.ACTION_STOP
                    context?.startService(this)
                }
            }
        }
    }

}