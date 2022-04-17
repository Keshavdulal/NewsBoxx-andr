package com.example.mapd726_group3_newsbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.widget.TextView
import android.widget.Button


class ForgotPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val forgotEmail = findViewById<TextView>(R.id.forgotEmail)




        btnSubmit.setOnClickListener {
        val email: String = forgotEmail.text.toString().trim { it <= ' '}
            if (email.isEmpty()) {
                Toast.makeText(
                    this@ForgotPassword,
                "Please enter email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful) {
                            Toast.makeText(
                                this@ForgotPassword,
                                "Email sent successfully to reset your password!",
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        }
                        else {
                            Toast.makeText(
                                this@ForgotPassword,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }


}