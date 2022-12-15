package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import com.example.myapplication.databinding.FragmentMainBinding
import kotlinx.coroutines.*

const val ARG_ID = "first"

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        var flag = false
        arguments?.takeIf { it.containsKey(ARG_ID) }?.apply {
            val id = arguments?.getInt(ARG_ID)
            if (id != null) {
                val image = ImgRepo.imgList[id]
                with(binding) {

                    lifecycleScope.launch {
                        var preloaded = async(Dispatchers.IO) {
                            delay(4000)
                            Glide.with(view)
                                .load(image.url)
                                .placeholder(R.drawable.gif)
                                .preload()

                        }
                        preloaded.await()
                        Glide.with(view)
                            .load(image.url)
                            .into(iV)
                    }


                }
            }
        }
    }


    suspend fun loadImg(view: View, image: Image, imageView: ImageView) {
        delay(5000)
        Glide.with(view).load(image.url).into(imageView)
    }
}