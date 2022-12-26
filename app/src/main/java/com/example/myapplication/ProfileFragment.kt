package com.example.myapplication


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DatabaseRepository
import com.example.myapplication.database.User
import com.example.myapplication.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var email: String
    private var emailChecked: Boolean = false
    private lateinit var binding: FragmentProfileBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var user: User
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProfileBinding.bind(view)
        repository = DatabaseRepository(AppDatabase.getInstance(requireContext()))
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener{
            ViewUtils.hideKeyboard(view)
        }
        val id = requireArguments().getLong("id")
        Log.i("1111111111111", "$id")


        with(binding) {
            lifecycleScope.launch(Dispatchers.IO) {
                user = repository.getUserById(id)
                tVLogin.text = user.login
            }

            btnChange.setOnClickListener {
                tVLogin.visibility = View.INVISIBLE
                eTEmail.visibility = View.VISIBLE
                btnSave.visibility = View.VISIBLE
            }
            eTEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (checkEmail(binding.eTEmail.text.toString())) {
                        email = binding.eTEmail.text.toString()
                        emailChecked = true
                    }
                }

            })
            btnSave.setOnClickListener {
                if (emailChecked) {
                    user.login = email
                    lifecycleScope.launch(Dispatchers.Main) {
                        repository.updateUser(user)
                        eTEmail.visibility = View.INVISIBLE
                        btnSave.visibility = View.INVISIBLE
                        tVLogin.text = user.login
                        tVLogin.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()

    }

}