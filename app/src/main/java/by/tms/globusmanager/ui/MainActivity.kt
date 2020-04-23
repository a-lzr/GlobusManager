package by.tms.globusmanager.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentProviderOperation
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import by.tms.globusmanager.MainActivityListener
import by.tms.globusmanager.R
import by.tms.globusmanager.permissions.PermissionsHelper
import com.google.android.material.navigation.NavigationView


const val PERMISSION_CONTACT_REQUEST_CODE = 1
const val PERMISSION_PHONE_REQUEST_CODE = 2

class MainActivity : AppCompatActivity(),
    MainActivityListener {

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
                    if (!PermissionsHelper.addPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_CONTACTS
                            ),
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
                if (PermissionsHelper.checkPermissionsResult(grantResults)) {
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

/*    private fun addPermissions(permissions: Array<String>, requestCode: Int): Boolean {
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
    } */

    override fun addContact() {
        val op = ArrayList<ContentProviderOperation>()

        op.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "com.globus")
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "0001A")
                .build()
        )

        op.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                )
                .withValue(
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                    "Alexander XXXX"
                )
                .build()
        )
        op.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "+37533333333333")
                .withValue(
                    ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                )
                .build()
        )

        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, op)
            Log.e("TAG!", "SUCCESS")
        } catch (e: Exception) {
            Log.e("TAG!", e.message)
        }
    }

    @SuppressLint("Recycle")
    override fun getContacts(): Int {
        /*val c: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf<String>(ContactsContract.Contacts._ID),
            ContactsContract.Contacts.DISPLAY_NAME + " = 'Alexander Lazerko'",
            null,
            null
        ) */

        val phoneCursor = contentResolver.query(
            ContactsContract.RawContacts.CONTENT_URI,
            null,
            ContactsContract.RawContacts.ACCOUNT_TYPE + " = 'com.globus'",
            null,
            null
        )

        return phoneCursor?.count ?: -1
    }
}