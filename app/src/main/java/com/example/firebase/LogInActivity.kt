package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebase.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    lateinit var loginBinding : ActivityLogInBinding

    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLogInBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        loginBinding.buttonLogIn.setOnClickListener {
            val userEmail = loginBinding.editTextEmailSignIn.text.toString()
            val userPassword = loginBinding.editTextPasswordSignIn.text.toString()

            logInWithFirebase(userEmail, userPassword)
        }

        loginBinding.buttonSignUp.setOnClickListener {
            val intent = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginBinding.buttonForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgetActivity::class.java)
            startActivity(intent)
        }

        loginBinding.buttonLogInWithPhone.setOnClickListener {

            val intent = Intent(this, PhoneLogInActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

    fun logInWithFirebase(userEmail : String, userPassword : String){

        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    Toast.makeText(applicationContext, "Login is succesful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LogInActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(applicationContext, task.exception?.toString(), Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser

        if (user != null){
            val intent = Intent(this@LogInActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}