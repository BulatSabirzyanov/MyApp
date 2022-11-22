package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.adapter.CityAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_sheet.*

class ActionBottomDialogFragment(
    private val adapter: CityAdapter
) : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sort_num.setOnClickListener {
            if (CityRepo.sortedByIdAscending)
                adapter.submitList(CityRepo.citys.sortedBy { it.id }.toMutableList())
            else
                adapter.submitList(CityRepo.citys.sortedByDescending { it.id }.toMutableList())
            CityRepo.sortedByIdAscending = !CityRepo.sortedByIdAscending
            dismiss()
        }
        sort_letter.setOnClickListener {
            if (CityRepo.sortedByNameAscending)
                adapter.submitList(CityRepo.citys.sortedBy { it.name }.toMutableList())
            else
                adapter.submitList(CityRepo.citys.sortedByDescending { it.name }.toMutableList())
            CityRepo.sortedByNameAscending = !CityRepo.sortedByNameAscending
            dismiss()

        }
    }
}