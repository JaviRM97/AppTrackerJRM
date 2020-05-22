package com.example.apptracker.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apptracker.R
import kotlinx.android.synthetic.main.activity_confirmation.*

class ConfirmationActivity : AppCompatActivity() {

    override fun onBackPressed() {
    // Deshabilitamos el boton de atrás, para que el usuario tenga que pulsar el boton de aceptar si quiere continuar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        btnAceptar.setOnClickListener{
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

