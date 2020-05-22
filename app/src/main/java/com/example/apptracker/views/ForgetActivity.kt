package com.example.apptracker.views

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.apptracker.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget.*
import org.jetbrains.anko.toast

class ForgetActivity : AppCompatActivity() {

    lateinit var direccion:String
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget)

        mAuth = FirebaseAuth.getInstance()

        btnRestablecimiento.setOnClickListener{
            if(tvEmailForget.text.toString() == null) {
                toast("Introduzca una direccion de correo")
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(tvEmailForget.text.toString()).matches()){
                toast("Formato de correo no valido")
            }
            else{
                direccion = tvEmailForget.text.toString()
                mAuth.sendPasswordResetEmail(direccion)
                toast("En breve recibira un correo para reestablecer")
                finish()
            }
        }
    }
}
