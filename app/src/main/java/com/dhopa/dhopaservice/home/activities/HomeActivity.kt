package com.dhopa.dhopaservice.home.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.beaute.user.SharePrefrence.App
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.cart.MyCartActivity
import com.dhopa.dhopaservice.databinding.ActivityHomeBinding
import com.dhopa.dhopaservice.profile.activity.NotificationActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var appBarConfigurationBottom: AppBarConfiguration
    private lateinit var activity: HomeActivity

    companion object{
        lateinit var bottomNavView: BottomNavigationView
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity=this@HomeActivity
        init()

    }

    private fun init() {
        val navController = findNavController(R.id.nav_host_fragment_home)
        bottomNavView = findViewById(R.id.bottom_nav_view)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        appBarConfigurationBottom = AppBarConfiguration(
            setOf(
                R.id.mainFragment,
//                R.id.postCodeFragment,
                R.id.cartFragment,
                R.id.bookingServicesFragment,
                R.id.offersFragment,
                R.id.profileFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfigurationBottom)
        bottomNavView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mainActionNotification -> {
                val intent = Intent(activity, NotificationActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.mainActionCamera ->{
//                val intent = Intent(activity, MyCartActivity::class.java)
//                startActivity(intent)
                return true
            }

            R.id.search ->{
                Toast.makeText(activity,"coming soon...in development....",Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}