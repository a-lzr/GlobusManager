package by.tms.globusmanager

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

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
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_context_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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

//        tabLayout.setupWithViewPager(viewPager)

/*        viewPager = findViewById<ViewPager>(R.id.main_view_pager)
        viewPager!!.adapter = MainFragmentPagerAdapter(supportFragmentManager) // , this@MainActivity

        tabLayout = findViewById<TabLayout>(R.id.main_tab_layout)
        addTab(tabLayout!!, R.string.main_menu_control, R.drawable.ic_menu_control)
        addTab(tabLayout!!, R.string.main_menu_messages, R.drawable.ic_menu_messages)
        tabLayout!!.visibility = TabLayout.VISIBLE
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        tabLayout!!.setupWithViewPager(viewPager)

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Toast.makeText(tabLayout!!.context, "onTabSelected", Toast.LENGTH_SHORT)
//                Log.i("TextStats","NEW TAB SELECTED: " + tab.position)
//                viewPagerReference.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                Toast.makeText(tabLayout!!.context, "onTabUnselected", Toast.LENGTH_SHORT)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                Toast.makeText(tabLayout!!.context, "onTabReselected", Toast.LENGTH_SHORT)
            }
        }) */

/*        val navMenuView: BottomNavigationView = findViewById(R.id.nav_menu_view)

        val navMenuController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appMenuBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_menu_main_control, R.id.nav_menu_main_messages
            )
        )
        setupActionBarWithNavController(navMenuController, appMenuBarConfiguration)
        navMenuView.setupWithNavController(navMenuController) */
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_context_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
