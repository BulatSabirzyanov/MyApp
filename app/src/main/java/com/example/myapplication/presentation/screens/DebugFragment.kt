package com.example.myapplication.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.R


class DebugFragment : Fragment(R.layout.fragment_debug) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    companion object {
        @JvmStatic
        fun newInstance() = DebugFragment()
    }
}