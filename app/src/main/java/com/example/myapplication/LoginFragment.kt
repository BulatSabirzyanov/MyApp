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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DatabaseRepository

import com.example.myapplication.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        view.setOnClickListener{
            ViewUtils.hideKeyboard(view)
        }
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

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (checkEmail(binding.loginEmail.text.toString())) {
                        email = binding.loginEmail.text.toString()
                        emailChecked = true
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

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (emailChecked && isValidPassword(binding.loginPassword.text.toString())) {
                        password = binding.loginPassword.text.toString()

                    }
                }

            })
        }
        binding.loginButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val user = repository.getUser(email, password)

                withContext(Dispatchers.Main) {
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
