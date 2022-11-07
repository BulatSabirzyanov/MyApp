package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentLikesBinding


class LikesFragment : Fragment(R.layout.fragment_likes) {
    private var binding: FragmentLikesBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLikesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            btnNextToLikes2.setOnClickListener{
                routeToSecondFragment(binding?.etSongName?.text.toString())
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() = LikesFragment()

    }
    fun routeToSecondFragment(name_of_song: String) {
        findNavController().navigate(
            R.id.action_likesFragment_to_likes2Fragment,
            Likes2Fragment.createArguments(nameOfsong = binding?.etSongName?.text.toString(), authorOfsong = binding?.etAuthorName?.text.toString())
        )
    }
}
