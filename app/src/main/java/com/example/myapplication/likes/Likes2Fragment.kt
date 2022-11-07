package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentLikes2Binding


class Likes2Fragment : Fragment(R.layout.fragment_likes2) {
    private var binding: FragmentLikes2Binding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLikes2Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val name_song = requireNotNull(requireArguments().getString(song_name))
        val name_author = requireNotNull(requireArguments().getString(author_name))
        binding?.tVSongName?.text = name_song
        binding?.tVAuthorName?.text = name_author



    }

    companion object {


            private const val song_name = "nameOfsong"
            private const val author_name = "authorOfsong"

            fun createArguments(nameOfsong: String,authorOfsong:String): Bundle {
                return bundleOf(song_name to nameOfsong, author_name to authorOfsong)
            }



        @JvmStatic
        fun newInstance() = LikesFragment()

    }
}