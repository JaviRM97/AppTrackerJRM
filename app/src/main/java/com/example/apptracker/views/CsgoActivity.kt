package com.example.apptracker.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apptracker.R
import kotlinx.android.synthetic.main.activity_csgo.*

class CsgoActivity : AppCompatActivity() {

    companion object {
        const val TAG="javi"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_csgo)

        btnCsgo.setOnClickListener {
            val intent = Intent(this, CsgoDetail::class.java)
            intent.putExtra("tracker", tvSearch.text.toString())
            startActivity(intent)
        }
    }
}
