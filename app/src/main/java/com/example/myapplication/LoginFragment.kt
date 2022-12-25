package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DatabaseRepository

import com.example.myapplication.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var emailChecked: Boolean = false
    private lateinit var binding: FragmentLoginBinding
    private lateinit var repository: DatabaseRepository
    private val profileFragment = ProfileFragment()


    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repository = DatabaseRepository(AppDatabase.getInstance(requireContext()))
        binding = FragmentLoginBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        var email = binding.loginEmail.text.toString()
        var password = binding.loginPassword.text.toString()
        with(binding) {
            loginEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.i("402", email)
                }

                override fun afterTextChanged(s: Editable?) {
                    if (checkEmail(binding.loginEmail.text.toString())) {
                        email = binding.loginEmail.text.toString()
                        emailChecked = true
                        Log.i("402", email)
                    }
                }

            })
            loginPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.i("402", password)
                }

                override fun afterTextChanged(s: Editable?) {
                    if (emailChecked && isValidPassword(binding.loginPassword.text.toString())) {
                        password = binding.loginPassword.text.toString()
                        Log.i("402", password)

                    }
                }

            })
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val user = repository.getUser(email, password)
            Log.i("401", email)
            Log.i("402", password)
            withContext(Dispatchers.Main) {
                if (user != null) {
                    val bundle = Bundle()
                    bundle.putString("email", email)
                    profileFragment.arguments = bundle
                    binding.loginButton.setOnClickListener {
                        if (checkEmail(binding.loginEmail.text.toString()) && isValidPassword(
                                binding.loginPassword.text.toString()
                            )
                        ) {
                            email = binding.loginEmail.text.toString()
                            password = binding.loginPassword.text.toString()
                            parentFragmentManager.beginTransaction().apply {
                                replace(R.id.fragment, ProfileFragment()).addToBackStack(null)
                                    .commit()
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "непрова",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "нет такого пользователя зарегистрируйтесь",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            with(binding) {
                regButton.setOnClickListener {
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.fragment, RegFragment()).addToBackStack(null)
                            .commit()
                    }
                }
            }

        }

        companion object {
            @JvmStatic
            fun newInstance() = ProfileFragment()

        }
    }
