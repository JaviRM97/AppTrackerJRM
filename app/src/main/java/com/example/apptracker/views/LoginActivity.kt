package com.example.apptracker.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.apptracker.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

        fun login(){
            if(tvEmailLogin.text.toString() != "" && tvPasswordLogin.text.toString() != ""){
                mAuth.signInWithEmailAndPassword(tvEmailLogin.text.toString(), tvPasswordLogin.text.toString()).addOnCompleteListener { task: Task<AuthResult> ->

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        toast("Ha iniciado sesion correctamente")
                        val firebaseUser = mAuth.currentUser
                        val intent = Intent(this , MainActivity::class.java)
                        intent.putExtra("firebaseUser",  firebaseUser.toString())
                        startActivity(intent)
                        finish()
                    } else {
                        toast(task.exception?.localizedMessage.toString())
                    }
                }
            }else{toast("Rellene todos los campos")}
        }

        btnForget.setOnClickListener{
            val intent = Intent(this , ForgetActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            login()
        }
    }
}
