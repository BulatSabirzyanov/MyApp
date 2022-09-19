package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {



    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private var counter = 0
    private var color = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        val secondFragment = SecondFragment()
        val fragmentManager = activity?.supportFragmentManager
        secondFragment.arguments = bundle

        binding.buttonFirst.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.nav_host_fragment_content_main,secondFragment).addToBackStack(null)
                commit()
            }


        }
        binding.buttonThird.setOnClickListener {
            counter += 1
            bundle.putInt("value",counter)


        }

        binding.buttonFourth.setOnClickListener {
            color += 1
            bundle.putInt("color_value",color)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}