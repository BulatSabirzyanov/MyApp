package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentSecondBinding
import kotlin.random.Random


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null


    private val binding get() = _binding!!

    fun changeColor(num:Int): Int{
        var color : Int = 0
        if(num!=0){

            color = Color.argb(255, (0..255).random(), (0..255).random(), (0..255).random())
        }
        return color
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val textView = binding?.textviewSecond
        val imageView = binding?.imageView2
        val args =  this.arguments
        val inputData = args?.getInt("value")
        val color = args?.getInt("color_value")

        textView?.text = if (inputData != 0 ) inputData.toString() else null
        imageView?.setBackgroundColor(Color.rgb(changeColor(color!!),changeColor(color),changeColor(color)))
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentManager = activity?.supportFragmentManager
        binding.buttonSecond.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.nav_host_fragment_content_main,FirstFragment())
                commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}