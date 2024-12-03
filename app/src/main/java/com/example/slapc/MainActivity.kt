package com.example.slapc

import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.example.slapc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)

        configurarMenu()
    }

    private fun configurarMenu() {
        val menu = navView.menu
        menu.findItem(R.id.nav_cerrar_sesion).isVisible = false
        menu.findItem(R.id.nav_admin_menu).isVisible = false
    }

    fun actualizarMenuParaCliente() {
        val menu = findViewById<NavigationView>(R.id.nav_view).menu
        menu.findItem(R.id.nav_login).isVisible = false
        menu.findItem(R.id.nav_cerrar_sesion).isVisible = true
        menu.findItem(R.id.nav_admin_menu).isVisible = false
    }

    fun actualizarMenuParaAdmin() {
        val menu = navView.menu
        menu.findItem(R.id.nav_login).isVisible = false
        menu.findItem(R.id.nav_cerrar_sesion).isVisible = true
        menu.findItem(R.id.nav_admin_menu).isVisible = true
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }
}