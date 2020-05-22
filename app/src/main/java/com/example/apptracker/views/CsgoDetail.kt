package com.example.apptracker.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.apptracker.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.detail_csgo.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class CsgoDetail : AppCompatActivity() {

    companion object {
        const val TAG="javi"
    }

    private lateinit var result: com.example.apptracker.model.ResultC
    private lateinit var tracker : String
    //historial
    var db = FirebaseFirestore.getInstance()
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_csgo)

        tracker = intent.getStringExtra("tracker") as String
        Log.d(TAG,tracker)
        //historial
        mAuth = FirebaseAuth.getInstance()
        if(mAuth.currentUser != null) {
            fab.visibility = View.VISIBLE
            fab.setOnClickListener { addData() }
        }else{
            fab.visibility = View.INVISIBLE
        }
        loadData()
    }

    private fun addData() {
        val historial: MutableMap<String, Any> = HashMap() // diccionario key value
        historial["nombre"] = tracker
        historial["juego"] = "Counter Strike"
        historial["tracker"] = "https://public-api.tracker.gg/v2/csgo/standard/profile/steam/$tracker/?TRN-Api-Key=4a2ef0bd-f6fa-4a41-b8f3-955ebf581bbd"
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
            val json = URL("https://public-api.tracker.gg/v2/csgo/standard/profile/steam/$tracker/?TRN-Api-Key=4a2ef0bd-f6fa-4a41-b8f3-955ebf581bbd").readText()
            result = Gson().fromJson(json, com.example.apptracker.model.ResultC::class.java)
            Log.d(TAG,result.toString())
            uiThread {
                // postexecute
                ui()
            }
        }
    }
    private fun ui() {
        Log.d(TAG,result.data.platformInfo.platformUserHandle.toString())
        Log.d(TAG, result.data.segments[0].stats.kills.toString())
        showCurrent()
    }

    private fun showCurrent() {
        tvNombre.text = "${result.data.platformInfo.platformUserHandle}"
        tvKills.text = "${result.data.segments[0].stats.kills.displayValue} kills"
        Glide.with(this).load(result.data.platformInfo.avatarUrl).placeholder(R.drawable.ic_account).into(ivNombre)
        tvBombP.text = "${result.data.segments[0].stats.bombsPlanted.displayValue} P"
        tvBombD.text = "${result.data.segments[0].stats.bombsDefused.displayValue} D"
        tvKdRatio.text = "${result.data.segments[0].stats.kd.displayValue}k/d"
        tvGames.text = "${result.data.segments[0].stats.timePlayed.displayValue}"
        tvWins.text = "${result.data.segments[0].stats.wlPercentage.displayValue}"
        tvPreci.text = "${result.data.segments[0].stats.shotsAccuracy.displayValue}"
        tvHeadshot.text = "${result.data.segments[0].stats.headshotPct.displayValue}"
        tvMvp.text = "${result.data.segments[0].stats.mvp.value}"
    }
}
