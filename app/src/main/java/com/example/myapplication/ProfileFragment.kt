package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DatabaseRepository
import com.example.myapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var repository: DatabaseRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProfileBinding.bind(view)
        repository = DatabaseRepository(AppDatabase.getInstance(requireContext()))
        super.onViewCreated(view, savedInstanceState)


            val email = requireArguments().getString("email").toString()

        with(binding) {
            tVLogin.text = email
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()

    }

}