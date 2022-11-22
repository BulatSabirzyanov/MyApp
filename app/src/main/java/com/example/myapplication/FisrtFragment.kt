package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.myapplication.adapter.CityAdapter
import com.example.myapplication.databinding.FragmentFisrtBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class FisrtFragment : Fragment(R.layout.fragment_fisrt), ItemClickListener {
    lateinit var binding: FragmentFisrtBinding
    lateinit var adapter: CityAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFisrtBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        binding.btnGoToCamera.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            navOptions {
                anim {
                    enter = androidx.navigation.ui.R.anim.nav_default_enter_anim
                    popEnter = androidx.navigation.ui.R.anim.nav_default_pop_enter_anim
                    popExit = androidx.navigation.ui.R.anim.nav_default_pop_exit_anim
                    exit = androidx.navigation.ui.R.anim.nav_default_exit_anim
                }
            }
        }
        binding.btnBottomDialogSheet.setOnClickListener {
//            val view: View = layoutInflater.inflate(R.layout.dialog_sheet,null)
//            val dialog = context?.let { it1 -> BottomSheetDialog(it1) }
//            dialog?.setContentView(view)
//            dialog?.show()


            ActionBottomDialogFragment(adapter).show(
                parentFragmentManager,
                null
            )
        }
    }

    private fun initRcView() = with(binding) {
        adapter = CityAdapter()
        println(CityRepo.citys)
        recycleView.adapter = adapter
        adapter.submitList(CityRepo.citys)
    }

    override fun onItemClick(item: String?) {

    }

}