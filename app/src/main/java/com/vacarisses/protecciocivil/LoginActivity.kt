package com.vacarisses.protecciocivil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //setup
        setup()

    }

    private fun setup() {
        loginButton.setOnClickListener{
            if (usernameEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword("V"+usernameEditText.text.toString()+"@pcvacarisses.org",passwordEditText.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful) {
                        showMain(it.result?.user?.email ?:"null", usernameEditText.text.toString())
                    } else {
                        showAlert()
                    }
                }
            } else {
                Toast.makeText(this, "Posa el teu indicatiu Victor i la contrasenya que se t'ha proporcionat", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAlert() {
        Toast.makeText(this, "No s'ha pogut iniciar sessió", Toast.LENGTH_SHORT).show()
    }

    private fun showMain(email: String, username: String){
        val mainIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("username", username)
        }
    startActivity(mainIntent)
    }
}