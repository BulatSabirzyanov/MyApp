package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import androidx.viewpager2.adapter.FragmentStateAdapter

class Adapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment = MainFragment()
        fragment.arguments =  Bundle().apply {
            putInt(ARG_ID,position)
        }
        return fragment

    }

}