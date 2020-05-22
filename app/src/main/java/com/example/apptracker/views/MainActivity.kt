package com.example.apptracker.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.apptracker.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val TAG="TrackerJRM"
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var logUsuario:String
    private lateinit var nickAvatar: TextView
    private lateinit var viewMenu:NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Authentication
        mAuth = FirebaseAuth.getInstance()
        logUsuario =  mAuth.currentUser?.email.toString()
        var verificado = mAuth.currentUser?.isEmailVerified
        viewMenu = findViewById<View>(R.id.nav_view) as NavigationView
        val nav_Menu = viewMenu.getMenu()

        if(mAuth.currentUser != null){
            nav_Menu.findItem(R.id.nav_login).setVisible(false)
            nav_Menu.findItem(R.id.nav_register).setVisible(false)
        }else if(mAuth.currentUser == null){
            nav_Menu.findItem(R.id.nav_historial).setVisible(false)
            nav_Menu.findItem(R.id.nav_logout).setVisible(false)
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        // Accedemos a los elementos de la header
        nickAvatar = nav_view.getHeaderView(0).nickAvatar

        //Botones del content main
        iv1.setOnClickListener {
            val intent = Intent(this, WarzoneActivity::class.java)
            startActivity(intent)
        }
        iv2.setOnClickListener {
            val intent = Intent(this, ApexActivity::class.java)
            startActivity(intent)
        }
        iv3.setOnClickListener {
            val intent = Intent(this, CsgoActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        if(mAuth.currentUser != null){
            nickAvatar.text = logUsuario
        }
        if(mAuth.currentUser == null){
            nickAvatar.text = "Inicia Sesion o Registrate"
        }
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_historial -> {
                historial()
            }
            R.id.nav_login -> {
                login()
            }
            R.id.nav_register -> {
                register()
            }
            R.id.nav_logout -> {
                logout()
            }
            R.id.nav_info -> {
                acercade()
            }
            R.id.nav_salir -> {
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun historial() {
        if(mAuth.currentUser != null){
            val intent = Intent(this , HistorialActivity::class.java)
            startActivity(intent)
        }
        else{toast("Para poder ver tu historial debes estar registrado")}
    }

    private fun login() {
        if(mAuth.currentUser == null){
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
        }
        else{toast("Ya has iniciado sesion con una cuenta")}
    }

    private fun register() {
        if(mAuth.currentUser == null){
            val intent = Intent(this , RegisterActivity::class.java)
            startActivity(intent)
        }else{toast("Debe cerrar sesion antes de crear una cuenta")}
    }

    private fun logout() {
        if(mAuth.currentUser != null){
            alert("Cerrar sesion?", "Cerrar Sesion") {
                positiveButton("SI") {
                    recreate()
                    mAuth.signOut()
                }
                negativeButton("NO") { }
            }.show()

        } else {toast("Para cerrar sesion antes debes estar en una")}
    }

    private fun acercade() {
        alert("TrackerJRM","Has visto que aplicacion mas cutre?") {
            yesButton {}
        }.show()
    }

}
