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
import com.example.mapd726_group3_newsbox.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment :Fragment(){

    private var _binding:FragmentSignInBinding?=null
    val binding get() = _binding !!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (Firebase.auth.currentUser !=null){
            navigateToHome()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //handle click open register activity
        binding.SignUpBtnTxt.setOnClickListener {
            val action=SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        //handle click, begin login
        binding.SignInBtn.setOnClickListener {
            val emailValidation = binding.emailEt.text.isNotBlank()
            val passwordValidation = binding.passwordEt.text.isNotEmpty()
            if (!emailValidation) binding.emailEt.error="Please enter your email"
            if (!passwordValidation) binding.passwordEt.error = "Please enter your password"
            if (emailValidation && passwordValidation) Firebase.auth.signInWithEmailAndPassword(binding.emailEt.text.toString(),binding.passwordEt.text.toString()).addOnSuccessListener {
                navigateToHome()
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Signing in failed due to ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvForgotPassword.setOnClickListener {
//            startActivity(Intent(this, ForgotPassword::class.java))
        }


    }
    fun navigateToHome(){
        val action= SignInFragmentDirections.actionSignInFragmentToHomeFragment()
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }




}