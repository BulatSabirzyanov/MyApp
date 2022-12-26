package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DatabaseRepository
import com.example.myapplication.database.User
import com.example.myapplication.databinding.FragmentRegBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegFragment : Fragment(R.layout.fragment_reg) {
    private lateinit var binding: FragmentRegBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var preferences: UserPreferences
    private val profileFragment = ProfileFragment()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repository = DatabaseRepository(AppDatabase.getInstance(requireContext()))
        binding = FragmentRegBinding.bind(view)
        preferences = UserPreferences(requireContext())
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener {
            ViewUtils.hideKeyboard(view)
        }
        var email = binding.regEmail.text.toString()
        var password = binding.regPassword.text.toString()

        var emailChecked = false
        with(binding) {
            regEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (checkEmail(binding.regEmail.text.toString())) {
                        emailChecked = true
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    if (checkEmail(binding.regEmail.text.toString())) {
                        email = binding.regEmail.text.toString()
                        emailChecked = true
                    }
                }

            })
            regPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (emailChecked && isValidPassword(binding.regPassword.text.toString())
                    ) {
                        regButton.isEnabled = true
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    if (emailChecked && isValidPassword(binding.regPassword.text.toString())) {
                        password = binding.regPassword.text.toString()
                        regButton.isEnabled = true


                    }
                }
            })
            regButton.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val user = User(login = email, password = password)
                    if (user in repository.getAllUsers())
                        Toast.makeText(
                            requireContext(),
                            "такой пользователь существует попытайтесь войти",
                            Toast.LENGTH_SHORT
                        ).show()
                    else {
                        repository.insertUser(user)
                        val newUser = repository.getUser(email,password)
                        preferences.saveUserId(newUser.id)
                        val id = user.id
                        Log.i("1111111111111", "$id")
                        withContext(Dispatchers.Main) {

                            findNavController().navigate(
                                R.id.action_regFragment_to_profileFragment
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegFragment()

    }
}