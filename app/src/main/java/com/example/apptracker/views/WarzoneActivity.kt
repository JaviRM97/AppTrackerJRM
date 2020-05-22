package com.example.apptracker.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apptracker.R
import kotlinx.android.synthetic.main.activity_warzone.*

class WarzoneActivity : AppCompatActivity() {

    companion object {
        const val TAG="javi"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warzone)

        btnWarzone.setOnClickListener {
            val intent = Intent(this, WarzoneDetail::class.java)
            intent.putExtra("tracker", tvSearch.text.toString())
            startActivity(intent)
        }
    }
}
