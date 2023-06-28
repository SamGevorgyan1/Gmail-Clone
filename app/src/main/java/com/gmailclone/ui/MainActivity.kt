package com.gmailclone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gmailclone.R
import com.gmailclone.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_primary,
                R.id.nav_promotions,
                R.id.nav_social,
                R.id.nav_starred,
                R.id.nav_snoozed,
                R.id.nav_important,
                R.id.nav_sent,
                R.id.nav_scheduled,
                R.id.nav_outbox,
                R.id.nav_drafts,
                R.id.nav_all_mail,
                R.id.nav_spam,
                R.id.nav_trash
            ),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)


        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_primary,
                R.id.nav_promotions,
                R.id.nav_social,
                R.id.nav_starred,
                R.id.nav_snoozed,
                R.id.nav_important,
                R.id.nav_sent,
                R.id.nav_scheduled,
                R.id.nav_outbox,
                R.id.nav_drafts,
                R.id.nav_all_mail,
                R.id.nav_spam,
                R.id.nav_trash -> {
                    navController.navigate(menuItem.itemId)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
