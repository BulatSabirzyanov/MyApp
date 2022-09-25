package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentBlankBinding
import java.util.regex.Pattern


class BlankFragment : Fragment() {
    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!
    private val regexStringmNumber = "^\\+[7][0-9]{10}"
    private val regexStringsText ="[^ \n]{5,}"

    private fun Validate(text: String?,string: String): Boolean {
        val p = Pattern.compile( string)
        val m = p.matcher(text!!)
        return m.matches()
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentManager = activity?.supportFragmentManager
        binding.btnFragment2.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.host_constrainLayout,BlankFragment2()).addToBackStack(null)
                        .commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        binding.etNumber.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (Validate(binding.etNumber.text.toString(),regexStringmNumber))
                {
                    binding.etSomeText.isEnabled = true
                    if (Validate(binding.etSomeText.text.toString(), regexStringsText))
                        binding.btnFragment2.isEnabled = true
                }
                else
                {
                    binding.etSomeText.isEnabled = false
                    binding.btnFragment2.isEnabled = false
                    binding.etNumber.error = "Invalid Mobile"
                }
            }
        })

        binding.etSomeText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnFragment2.isEnabled = false
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (Validate(binding.etNumber.text.toString(),regexStringmNumber)) {
                    if (Validate(binding.etSomeText.text.toString(), regexStringsText))
                        binding.btnFragment2.isEnabled = true
                    else {
                        binding.btnFragment2.isEnabled = false
                        binding.etSomeText.error = "Invalid Text"
                    }
                }
                else{
                    binding.btnFragment2.isEnabled = false
                }
            }
        })
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = BlankFragment()

    }
}