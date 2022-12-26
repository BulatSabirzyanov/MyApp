package com.example.myapplication


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DatabaseRepository
import com.example.myapplication.databinding.FragmentLoginBinding
import kotlinx.coroutines.*

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var emailChecked: Boolean = false
    private lateinit var binding: FragmentLoginBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var preferences: UserPreferences



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repository = DatabaseRepository(AppDatabase.getInstance(requireContext()))
        binding = FragmentLoginBinding.bind(view)
        preferences = UserPreferences(requireContext())
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener {
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
                    preferences.saveUserId(user.id)
                    findNavController().navigate(
                        R.id.action_loginFragment2_to_profileFragment
                    )
                }
            }
        }
        with(binding) {
            regButton.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment2_to_regFragment
                )
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()

    }
}
