package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DatabaseRepository
import com.example.myapplication.database.Settings
import com.example.myapplication.database.User
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.databinding.FragmentSettingsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var preferences: UserPreferences
    private lateinit var user: User
    private lateinit var userSettings: Settings

    private var setting1 = true
    private var setting2 = true
    private var setting3 = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = DatabaseRepository(AppDatabase.getInstance(requireContext()))
        binding = FragmentSettingsBinding.bind(view)
        preferences = UserPreferences(requireContext())

        binding.checkboxSetting1.isChecked = preferences.getSetting1()
        binding.checkboxSetting2.isChecked = preferences.getSetting2()
        binding.checkboxSetting3.isChecked = preferences.getSetting3()

        with(binding) {
            setting1 = checkboxSetting1.isChecked
            setting2 = checkboxSetting2.isChecked
            setting3 = checkboxSetting3.isChecked
            btnSave.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    repository.updateSettings(
                        Settings(
                            setting1 = setting1,
                            setting2 = setting2,
                            setting3 = setting3,
                            userId = preferences.getUserId()
                        )
                    )
                }

            }
        }
    }


}