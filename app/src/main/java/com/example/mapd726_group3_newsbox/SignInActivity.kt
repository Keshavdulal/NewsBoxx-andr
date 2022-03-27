package com.example.mapd726_group3_newsbox


import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.mapd726_group3_newsbox.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.ActionBar

class SignInActivity : AppCompatActivity() {

    //ViewBinding
        private lateinit var binding:ActivitySignInBinding

    //ActionBar
    private lateinit var actionBar: ActionBar


    //ProgressDialog
    private lateinit var progressDialog:ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Sign In"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)


        //init FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click open register activity
        binding.SignUpBtnTxt.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        //handle click, begin login
        binding.SignInBtn.setOnClickListener {
            //before logging in, validate data
            validateData()
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }


//        val signIn = findViewById<Button>(R.id.SignInBtn)
//        val signUpTxt = findViewById<TextView>(R.id.SignUpBtnTxt)
//
//        signIn.setOnClickListener {
//            val intent = Intent(this@SignIn, Explore::class.java)
//            startActivity(intent)
//        }
//
//        signUpTxt.setOnClickListener {
//            val intent = Intent(this@SignIn, SignUp::class.java)
//            startActivity(intent)
//        }
    }


    private fun validateData() {
        //get data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate data

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        }

        else if (TextUtils.isEmpty(password)) {
            //no password entered
            binding.passwordEt.error = "Please enter password"
        }
        else {
            //data is valdiated, begin login
            firebaseLogin()
        }

    }


    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login success
                val firebaseUser = firebaseAuth.currentUser
                var email = firebaseUser!!.email
                Toast.makeText(this, "LoggedIn as  $email", Toast.LENGTH_SHORT).show()

                //open explore
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }

            .addOnFailureListener { e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //if user is already logged in go to explore
        //get current user

        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null) {
            //user is already logged in
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

}