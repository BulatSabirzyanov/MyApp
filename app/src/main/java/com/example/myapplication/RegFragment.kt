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
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DatabaseRepository
import com.example.myapplication.database.User
import com.example.myapplication.databinding.FragmentRegBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegFragment : Fragment(R.layout.fragment_reg) {
    private lateinit var binding: FragmentRegBinding
    private lateinit var repository: DatabaseRepository
    private val profileFragment = ProfileFragment()





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repository = DatabaseRepository(AppDatabase.getInstance(requireContext()))
        binding = FragmentRegBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener{
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
                    if (emailChecked && isValidPassword(binding.regPassword.text.toString()).also { Log.i("123","$password  ${isValidPassword(password)}") }) {
                        regButton.isEnabled = true
                    }
                }
                override fun afterTextChanged(s: Editable?) {
                    if (emailChecked && isValidPassword(binding.regPassword.text.toString())) {
                        password = binding.regPassword.text.toString()
                        regButton.isEnabled = true
                        Log.i("123","$password  ${isValidPassword(password)}")

                    }
                }
            })
            regButton.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO){
                    val user = User(login = email, password = password)
                    repository.insertUser(user)
                    val bundle = Bundle()
                    bundle.putLong("id", user.id)
                    profileFragment.arguments = bundle
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.fragment, profileFragment).addToBackStack(null)
                            .commit()
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