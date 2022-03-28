package com.example.mapd726_group3_newsbox.onboarding

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mapd726_group3_newsbox.ProfileActivity
import com.example.mapd726_group3_newsbox.databinding.FragmentSignUpBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding?=null
    val binding get() = _binding !!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.SignUpBtn.setOnClickListener { validateData() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


    private fun validateData() {
        //get data
        val email = binding.emailEt.text.toString().trim()
        val password = binding.passwordEt.text.toString().trim()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        }

        else if (TextUtils.isEmpty(password)) {
            //password isn't entered
            binding.passwordEt.error = "Please enter password"
        }
        else if (password.length < 6) {
            //password length is less than 6
            binding.passwordEt.error = "Password must be at least 6 characters long"
        }

        else {
            //data is valid, continue signup
            firebaseSignUp(email,password)
        }
    }

    private fun firebaseSignUp(email:String,password:String) {

        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val action=SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
                findNavController().navigate(action)
            }

            .addOnFailureListener { e->
                //signup failed

                Toast.makeText(requireContext(), "SignUp Failed due to ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

}