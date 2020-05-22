package com.example.apptracker.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apptracker.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity(){

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //initializing firebase auth object
        mAuth = FirebaseAuth.getInstance()

        fun register() {
            if(emailRegister.text.toString() != "" && emailConfirmar.text.toString() != "" && passwordRegister.text.toString() != "" && passwordRepeat.text.toString() != ""){
                if(emailRegister.text.toString() == emailConfirmar.text.toString() && passwordRegister.text.toString() == passwordRepeat.text.toString()){

                    this.mAuth.createUserWithEmailAndPassword(emailRegister.text.toString(), passwordRegister.text.toString()).addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            toast("Usuario registrado correctamente")
                            val firebaseUser = mAuth.currentUser!!
                            // Si se crea la cuenta correctamente, abrimos la activity de confirmacion que se loguee
                            firebaseUser.sendEmailVerification()
                            val intent = Intent(this , ConfirmationActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            toast(task.exception?.localizedMessage.toString())
                            //toast("Error al registrar el nuevo usuario")
                        }
                    }
                }else{
                    toast("Asegurese de que coinciden los campos...")
                }
            }else{
                toast("Rellene todos los campos")
            }
        }
        btnRegistro.setOnClickListener{
            register()
        }
    }
}









