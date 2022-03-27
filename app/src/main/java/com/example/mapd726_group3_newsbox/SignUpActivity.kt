package com.example.mapd726_group3_newsbox

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.mapd726_group3_newsbox.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivitySignUpBinding

    //ActionBar
    private lateinit var actionBar: ActionBar


    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure Actionbar, //enable back button
        actionBar = supportActionBar!!
        actionBar.title = "Sign Up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //configure progress dialog
        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating account In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click open register activity
        binding.SignInBtnTxt.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        //handle click, begin signup
        binding.SignUpBtn.setOnClickListener {
            //validate data

            validateData()
        }

//        val signUp = findViewById<Button>(R.id.SignUpBtn)
//        val signInTxt = findViewById<TextView>(R.id.SignInBtnTxt)
//
//        signUp.setOnClickListener {
//            val intent = Intent(this@SignUp, Explore::class.java)
//            startActivity(intent)
//        }
//
//        signInTxt.setOnClickListener {
//            val intent = Intent(this@SignUp, SignIn::class.java)
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
            //password isn't entered
            binding.passwordEt.error = "Please enter password"
        }
        else if (password.length < 6) {
            //password length is less than 6
            binding.passwordEt.error = "Password must be at least 6 characters long"
        }

        else {
            //data is valid, continue signup
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()

                //get current user
                var firebaseUser = firebaseAuth.currentUser
                var email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()

                //open explore
                startActivity(Intent( this, ProfileActivity::class.java))
                finish()
            }

            .addOnFailureListener { e->
                //signup failed
                progressDialog.dismiss()
                Toast.makeText(this, "SignUp Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //go back to previous activity, when back button of actionbar clicked
        return super.onSupportNavigateUp()
    }
}