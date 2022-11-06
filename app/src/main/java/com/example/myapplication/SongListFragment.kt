package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.adapter.SongAdapter
import com.example.myapplication.databinding.FragmentSongsBinding

class SongListFragment : Fragment() {
    private var binding: FragmentSongsBinding? = null
    private var filmAdapter: SongAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        filmAdapter = SongAdapter(SongRepository.songs) {
            bundle.putInt("Id", it)
            findNavController().navigate(R.id.action_songListFragment_to_songFragment, bundle)
        }
        binding?.listView?.adapter = filmAdapter
    }
}