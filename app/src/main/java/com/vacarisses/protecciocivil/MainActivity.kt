package com.vacarisses.protecciocivil

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.vacarisses.protecciocivil.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    companion object {
        var usernameGlobal = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also { viewBinding ->
            setContentView(viewBinding.root)
        }

        // funcion navigationview
        binding.navView.setNavigationItemSelectedListener(this)
        replaceFragment(HomeFragment(), "Inici")


        // funcion setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val username = bundle?.getString("username")
        if (username != null) {
            usernameGlobal = username
        }
        setup(email ?: "", username ?: "")

        setSupportActionBar(binding.appBarLayout.toolbarMain)

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarLayout.toolbarMain,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
    }


    /* funcion donde decimos que hacer cuando pulsamos los elementos del menu, en este caso tenemos un when y cada fragment está
        * marcado por un ID, buscamos el id mediante r.id y le mandamos la funcion de replacefragment y el titulo propio de cada
        * elemento, una vez hecho eso cerramos el drawer(menu) */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_home -> replaceFragment(HomeFragment(), "Inici")
            R.id.nav_item_vehicles -> replaceFragment(VehiclesFragment(), "Vehicles")
            R.id.nav_item_camaresforestals -> replaceFragment(CameresForestalsFragment(), "Cameres Forestals")
            R.id.nav_item_voluntaris -> replaceFragment(VoluntarisFragment(), "Voluntaris")
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /* Funcion para hacer override de onBackPressed, cuando le des botón atrás comprobará si
    * estás en el menú, si estás con el menú abierto lo cerrará y si estás con el menú cerrado
    * te enviará un dialogo que te pregunta si quieres salir, en caso de negativo se cierra el
    * dialogo en caso de positivo vuelves a la loginactivity */
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val mBuilder = AlertDialog.Builder(this)
                .setTitle("Atenció")
                .setMessage("Estas segur de que vols sortir?")
                .setPositiveButton("Si", null)
                .setNegativeButton("No", null)
                .show()
            val mPositiveButton = mBuilder.getButton(AlertDialog.BUTTON_POSITIVE)
            mPositiveButton.setOnClickListener {
                super.onBackPressed()
            }
        }
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    //funcion setup de la mainactivity.kt
    private fun setup(email: String, username: String) {

    }

    //funcion replacefragment (reemplazar el nav_host_fragment a diferentes fragmentos)
    private fun replaceFragment(fragment: Fragment, title: String) {  //le pasamos el fragmento a reemplazar y el titulo

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.nav_host_fragment,
            fragment
        ) //le decimos cual es el host a remplazar en este caso es el que está en activity_main
        //fragmentTransaction.addToBackStack(null) a implementar más adelante, es para que cuando pulses atrás se vaya al anterior fragment pero entra en conflicto con onbackpressed
        fragmentTransaction.commit() //hacemos un commit si no no se ven los cambios
        drawer_layout.closeDrawers()
        setTitle(title) // ponemos el titulo que le pasamos a la ventana
    }
}
