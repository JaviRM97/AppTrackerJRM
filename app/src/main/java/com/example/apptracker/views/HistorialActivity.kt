package com.example.apptracker.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptracker.R
import com.example.apptracker.adapter.CustomAdapterHistorial
import com.example.apptracker.model.Historial
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_historial.*

class HistorialActivity : AppCompatActivity() {

    companion object {
        const val TAG = "HistorialActivity"
    }

    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var adapter: CustomAdapterHistorial
    private lateinit var historialAL:ArrayList<Historial>
    private lateinit var historial:Historial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        showHistorial()
    }

    private fun showHistorial(){
        historialAL =  ArrayList()

        db.collection("historial")
            .whereEqualTo("idUsuario", mAuth.currentUser!!.uid)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        historial = Historial() //Firestore trabaja de forma asincrona, por lo que hay que declarar un nuevo viaje cada vez que empieza el nuevo bucle
                        Log.d("javiDocument", document.id + " => " + document.data)
                        historial.nombre = document.data.getValue("nombre").toString()
                        historial.juego = document.data.getValue("juego").toString()
                        historial.tracker = document.data.getValue("tracker").toString()
                        historial.idUsuario = document.data.getValue("idUsuario").toString()
                        historialAL.add(historial)
                    }
                } else {
                    Log.d("Mal", "Error getting documents: ", task.exception)
                }
                rvHistorial.layoutManager = LinearLayoutManager(this)
                adapter = CustomAdapterHistorial(this, R.layout.row_historial, historialAL)
                rvHistorial.adapter = adapter
            }
    }

    fun onClickHistorial(v: View){
        val historial = v.tag as Historial
        val juego = historial.juego
        val tracker = historial.nombre
        Log.d(TAG, juego + "" + tracker )

        if (juego == "Warzone") {
            val intent = Intent(this, WarzoneDetail::class.java)
            intent.putExtra("tracker", tracker)
            startActivity(intent)
        } else if (juego == "Apex Legends") {
            val intent = Intent(this, ApexDetail::class.java)
            intent.putExtra("tracker", tracker)
            startActivity(intent)
        } else if (juego == "Counter Strike") {
            val intent = Intent(this, CsgoDetail::class.java)
            intent.putExtra("tracker", tracker)
            startActivity(intent)
        } else {}
    }

    override fun onRestart() {
        super.onRestart()
        historialAL.clear()
        showHistorial()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }
}



