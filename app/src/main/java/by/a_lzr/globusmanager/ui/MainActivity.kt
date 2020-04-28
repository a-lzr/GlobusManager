package by.a_lzr.globusmanager.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentProviderOperation
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import by.a_lzr.globusmanager.MainActivityListener
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.account.AccountHelper
import by.a_lzr.globusmanager.permissions.PermissionsHelper
import by.a_lzr.globusmanager.settings.SettingsHelper
import by.a_lzr.globusmanager.deprecated.sync.BatchOperation
import by.a_lzr.globusmanager.deprecated.sync.ContactOperations
import by.a_lzr.globusmanager.toast.ToastHelper
import by.a_lzr.globusmanager.ui.main.messages.MainMessagesFragmentListener
import com.google.android.material.navigation.NavigationView


const val PERMISSION_CONTACT_REQUEST_CODE = 1
const val PERMISSION_PHONE_REQUEST_CODE = 2

class MainActivity : AppCompatActivity(), MainActivityListener {

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

    /*
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.action_sync).isVisible = false
        return true
    }
     */

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

    fun addContact2(
        context: Context?, accountName: String?,
        groupId: Long, inSync: Boolean, batchOperation: BatchOperation?
    ) {

        // Put the data in the contacts provider
        val contactOp = ContactOperations.createNewContact(
            this, 1, accountName, inSync, batchOperation!!
        )
        contactOp.addName(
            "ALEX1",
            "ALEX2",
            "ALEX3"
        )
            .addEmail("test.alexander666@gmail.com")
            .addPhone("+3756666666", Phone.TYPE_MOBILE)
//            .addPhone(rawContact.getHomePhone(), Phone.TYPE_HOME)
        //          .addPhone(rawContact.getOfficePhone(), Phone.TYPE_WORK)
//            .addGroupMembership(groupId)
//            .addAvatar(rawContact.getAvatarUrl())

        // If we have a serverId, then go ahead and create our status profile.
        // Otherwise skip it - and we'll create it after we sync-up to the
        // server later on.
        contactOp.addProfileAction(1)
//        if (rawContact.getServerContactId() > 0) {
//            contactOp.addProfileAction(rawContact.getServerContactId())
//        }
    }

    override fun addContact() {
/*        val resolver: ContentResolver = contentResolver;
        val batchOperation = BatchOperation(this, resolver)
        addContact2(
            this, AccountHelper.getAccountName(), 1, true, batchOperation
        )
        if (batchOperation.size() >= 50) {
            batchOperation.execute();
        }
        batchOperation.execute(); */

//        setAndroidAccountSyncEnabled(true);

/*        val op = ArrayList<ContentProviderOperation>()

        op.add(
            ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_NAME, AccountHelper.getAccountName())
                .withValue(RawContacts.ACCOUNT_TYPE, AccountHelper.getAccountType())
                // .withValue(RawContacts.SOURCE_ID, 12345)
                // .withValue(RawContacts.AGGREGATION_MODE,
                // RawContacts.AGGREGATION_MODE_DISABLED)
                .build()
        )

        op.add(
            ContentProviderOperation
                .newInsert(Settings.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_NAME, AccountHelper.getAccountName())
                .withValue(RawContacts.ACCOUNT_TYPE, AccountHelper.getAccountType())
                .withValue(Settings.UNGROUPED_VISIBLE, 1)
                .build()
        )

        op.add(
            ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.GIVEN_NAME, contact.getFullname())
                .withValue(StructuredName.FAMILY_NAME, contact.getFullname())
                .build()
        )

        op.add(
            ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                .withValue(
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    contact.getPhoneNumber()
                ).build()
        )

        op.add(
            ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                )
                .withValue(
                    ContactsContract.CommonDataKinds.Email.DATA,
                    contact.getEmail()
                ).build()
        )

        op.add(
            ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, MIMETYPE)
                .withValue(ContactsContract.Data.DATA1, contact.getFullname())
                .withValue(ContactsContract.Data.DATA2, contact.getEmail())
                .withValue(ContactsContract.Data.DATA3, contact.getHomeAddress()).build()
        )
        try {
//            val results: Array<ContentProviderResult> = resolver.applyBatch(
//                ContactsContract.AUTHORITY, ops
//            )

            contentResolver.applyBatch(ContactsContract.AUTHORITY, op)
            Log.e("TAG!", "SUCCESS")
//          if (results.size == 0) AppLog.d(FragmentActivity.TAG, "Failed to add.")
        } catch (e: Exception) {
            Log.e("TAG!", e.message)
//            Log.e(FragmentActivity.TAG, e.message, e)
        }
*/
        val values = ContentValues()
        contentResolver.insert(ContactsContract.Profile.CONTENT_RAW_CONTACTS_URI, values)

/*
        val values = ContentValues()
        values.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID, contactId)
        values.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, groupId)
        values.put(
            ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,
            ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
        )
        mResolver.insert(ContactsContract.Data.CONTENT_URI, values)/
 */

        val op = ArrayList<ContentProviderOperation>()

        op.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(
                    ContactsContract.RawContacts.ACCOUNT_NAME,
                    AccountHelper.getAccountName()
                )
                .withValue(
                    ContactsContract.RawContacts.ACCOUNT_TYPE,
                    AccountHelper.getAccountType()
                )
                .withValue(
                    ContactsContract.RawContacts.AGGREGATION_MODE,
                    ContactsContract.RawContacts.AGGREGATION_MODE_DEFAULT
                )
//                .withValue(ContactsContract.RawContacts.SYNC1, "Vasia")
                //       .withValue(
                //                   ContactsContract.Data.MIMETYPE,
                //                   SettingsHelper.getPreferenceString(R.string.mime_type)
                //                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                //     )
                .build()
        )

        /*
        op.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
            .withValue(RawContacts.ACCOUNT_NAME, AccountHelper.getAccountName())
            .withValue(RawContacts.ACCOUNT_TYPE, AccountHelper.getAccountType())
            .withValue(ContactsContract.RawContacts.UNGROUPED_VISIBLE, 1)
            .build());
        */

/*
        op.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    //                    SettingsHelper.getPreferenceString(R.string.mime_type)
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                )
                .withValue(
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                    "Alexander 5555"
                )
                .build()
        )
 */
        /*
        op.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
    //                    SettingsHelper.getPreferenceString(R.string.mime_type)
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "+37532323232")
                .withValue(
                    ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                )
                .build()
        )
        */

        op.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.Profile.CONTENT_RAW_CONTACTS_URI)
            .withValue(ContactsContract.Profile.IS_USER_PROFILE, 1)
            .build())

        op.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, "Alexander 5555")
            .build());

        op.add(
            ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    SettingsHelper.getPreferenceString(R.string.mime_type)
                )
//                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Data.DATA2, "Globus Name")
                .withValue(ContactsContract.Data.DATA3, "User Connected with")
//                .withValue(ContactsContract.Data.DATA1, "Vasia")
//                .withValue(ContactsContract.Data.DATA2, "Globus Manager")
//                .withValue(ContactsContract.Data.DATA3, "home address")
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
            ContactsContract.RawContacts.ACCOUNT_TYPE + " = '" + AccountHelper.getAccountType() + "'",
            null,
            null
        )

        return phoneCursor?.count ?: -1
    }
}