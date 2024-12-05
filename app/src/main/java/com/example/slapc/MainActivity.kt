package com.example.slapc

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
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

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_login,
                R.id.nav_catalogo,
            ),
            drawerLayout
        )

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)

        configurarMenu()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_cerrar_sesion -> {
                    cerrarSesion()
                    true
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(menuItem, navController)
                    drawerLayout.closeDrawers()
                    true
                }
            }
        }
    }

    // Este sería el menú default, oculta el cerrar sesión y el menú del administrador
    // dejando solo visible el login y por defecto el catálogo de productos siempre es visible
    private fun configurarMenu() {
        Log.d("DEBUG", "ID nav_login: ${R.id.nav_login}")
        val menu = navView.menu
        menu.findItem(R.id.nav_cerrar_sesion).isVisible = false
        menu.findItem(R.id.nav_admin_menu).isVisible = false
        menu.findItem(R.id.nav_pedido).isVisible = false
        menu.findItem(R.id.nav_login).isVisible = true
    }

    // Cuando se inicia sesión se usa este, oculta la opción del login y muestra el cerrar sesión
    fun actualizarMenuParaCliente() {
        val menu = navView.menu
        menu.findItem(R.id.nav_login).isVisible = false
        menu.findItem(R.id.nav_cerrar_sesion).isVisible = true
        menu.findItem(R.id.nav_admin_menu).isVisible = false
        menu.findItem(R.id.nav_pedido).isVisible = true
    }

    fun cerrarSesion() {
        try{
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
            // Se deja el menú default
            configurarMenu()
            // Actualizamos el nav header a usuario no registrado porque se cerró sesión
            actualizarNavHeader(null, null)
            // Cerramos el menú
            drawerLayout.closeDrawers()
        } catch (e: Exception) {
            // Por si falla :c
            Log.e("ERROR", "Error en cerrarSesion: ${e.message}")
        }

    }

    // Estoes para actualizar el nav header con los datos del usuario que inició sesión
    fun actualizarNavHeader(nombre: String?, correo: String?) {
        //Se obtiene el navHeader
        val headerView = navView.getHeaderView(0)

        // Se obtienen el username y el email
        val usernameTextView = headerView.findViewById<TextView>(R.id.nav_header_username)
        val emailTextView = headerView.findViewById<TextView>(R.id.nav_header_email)

        // Se actualiza el nav header con los datos del usuario si es que no es null
        if(nombre != null && correo != null) {
            usernameTextView.text = nombre
            emailTextView.text = correo
        } else{
            usernameTextView.text = "Usuario no registrado"
            emailTextView.text = ""
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }
}