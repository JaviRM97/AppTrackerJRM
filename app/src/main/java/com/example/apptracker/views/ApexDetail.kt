package com.example.apptracker.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import com.example.apptracker.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.detail_apex.*
import kotlinx.android.synthetic.main.detail_apex.fab
import kotlinx.android.synthetic.main.detail_apex.ivNombre
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class ApexDetail : AppCompatActivity() {

    companion object {
        const val TAG="javi"
    }
    private lateinit var result: com.example.apptracker.model.ResultA
    private lateinit var tracker : String

    //historial
    var db = FirebaseFirestore.getInstance()
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_apex)

        mAuth = FirebaseAuth.getInstance()

        tracker = intent.getStringExtra("tracker") as String
        Log.d(TAG,tracker)

        //historial
        if(mAuth.currentUser != null) {
            fab.visibility = VISIBLE
            fab.setOnClickListener { addData() }
        }else{
            fab.visibility = INVISIBLE
        }
        loadData()
    }

    private fun addData() {
        val historial: MutableMap<String, Any> = HashMap() // diccionario key value
        historial["nombre"] = tracker
        historial["juego"] = "Apex Legends"
        historial["tracker"] = "https://public-api.tracker.gg/v2/apex/standard/profile/psn/$tracker/?TRN-Api-Key=4a2ef0bd-f6fa-4a41-b8f3-955ebf581bbd"
        historial["idUsuario"] = mAuth.currentUser!!.uid
        db.collection("historial")
            .add(historial)
            .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                Log.d(TAG,"DocumentSnapshot added with ID: " + documentReference.id)
            })
            .addOnFailureListener(OnFailureListener { e ->
                Log.w(TAG,"Error adding document", e)
            })
    }

    private fun loadData() {
        doAsync {
            //doinbackground
            val json = URL("https://public-api.tracker.gg/v2/apex/standard/profile/psn/$tracker/?TRN-Api-Key=4a2ef0bd-f6fa-4a41-b8f3-955ebf581bbd").readText()
            result = Gson().fromJson(json, com.example.apptracker.model.ResultA::class.java)
            Log.d(TAG,result.toString())
            uiThread {
                // postexecute
                ui()
            }
        }
    }

    private fun ui() {
        Log.d(TAG,result.data.platformInfo.platformUserIdentifier.toString())
        Log.d(TAG, result.data.segments[0].stats.kills.toString())
        showCurrent()
    }

    private fun showCurrent() {
        tvNombre.text = "${result.data.platformInfo.platformUserIdentifier}"
        Glide.with(this).load(result.data.platformInfo.avatarUrl).placeholder(R.drawable.ic_account).into(ivNombre)
        tvKills.text = "${result.data.segments[0].stats.kills.value} kills"
        tvLev.text = "${result.data.segments[0].stats.level.value} level"
        //Error en algunas busquedas
        //tvGames.text = "${result.data.segments[0].stats.seasonWins.displayvalue} wins"
        Glide.with(this).load(result.data.segments[0].stats.rankScore.metadata.iconUrl).placeholder(R.drawable.ic_account).into(ivRank)
        tvRank.text = "${result.data.segments[0].stats.rankScore.metadata.rankName}"
    }
}
