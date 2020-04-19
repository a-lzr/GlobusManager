package by.tms.globusmanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewParent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*


private const val PERMISSION_CONTACT_REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {

    private lateinit var navView: NavigationView
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_context_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_bar_main,
                R.id.nav_bar_instructions,
                R.id.nav_bar_contacts,
                R.id.nav_bar_tools,
                R.id.nav_bar_settings,
                R.id.nav_bar_info
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_bar_contacts -> {
                    if (!addPermissions(
                            arrayOf(Manifest.permission.READ_CONTACTS),
                            PERMISSION_CONTACT_REQUEST_CODE
                        )
                    )
                        return@setNavigationItemSelectedListener false
                }
            }

            val handled = NavigationUI.onNavDestinationSelected(it, navController)
            if (handled)
                finishNavigate()
            return@setNavigationItemSelectedListener handled
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_context_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CONTACT_REQUEST_CODE -> {
                if (checkPermissionsResult(grantResults)) {
                    customNavigate(R.id.nav_bar_contacts)
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun customNavigate(itemId: Int) {
        val options = NavOptions.Builder()
            .setPopUpTo(navController.currentDestination!!.id, true)
            .setLaunchSingleTop(true)
            .build()
        navController.navigate(itemId, null, options)

        finishNavigate()
    }

    private fun finishNavigate() {
        val parent = navView.parent
        if (parent is DrawerLayout) {
            parent.closeDrawer(navView)
        }
    }

//    private fun getBarMenuIndexById(id: Int): Int {
//        return appBarConfiguration.topLevelDestinations.indexOf(id)
//    }

    private fun addPermissions(permissions: Array<String>, requestCode: Int): Boolean {
        if (checkPermissions(permissions)) return true
        Log.e("TAG1", "START REQUEST")
        ActivityCompat.requestPermissions(this, permissions, requestCode)
        Log.e("TAG1", "END REQUEST")
        return false
    }

    private fun checkPermissions(permissions: Array<String>): Boolean {
        for (i in permissions.indices)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permissions[i]
                ) != PackageManager.PERMISSION_GRANTED
            ) return false
        return true
    }

    private fun checkPermissionsResult(permissions: IntArray): Boolean {
        for (i in permissions.indices)
            if (permissions[i] != PackageManager.PERMISSION_GRANTED) return false
        return true
    }
}