package by.a_lzr.globusmanager.ui.system

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.account.AccountHelper
import by.a_lzr.globusmanager.activities.ActivityHelper
import by.a_lzr.globusmanager.permissions.PermissionsHelper
import by.a_lzr.globusmanager.open_classes.CustomAccountAuthenticatorActivity
import by.a_lzr.globusmanager.ui.PERMISSION_PHONE_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : CustomAccountAuthenticatorActivity() { //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        if (PermissionsHelper.addPermissions(
                this, arrayOf(
                    Manifest.permission.READ_PHONE_STATE
                ),
                PERMISSION_PHONE_REQUEST_CODE
            )
        )
            updateLoginEdit()
        else
            loginEdit.setText(getString(R.string.register_login_text_default))

//        toolbar.title = getString(R.string.register_title)

//        val telephonyManager =  getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        telephonyManager.getImei()

//        ContextCompat.getSystemService(Context.TELEPHONY_SERVICE)
//        LoaderManager.getInstance(this)

        registryBtn.setOnClickListener {
            if (loginEdit.text!!.isNotEmpty()) {
                onTokenReceived(loginEdit.text.toString())
            }
//            onTokenReceived("Alexander", "123", "E013B7E7-3869-4574-ACC6-ADD01D65CCB4")
//            loaderManager.restartLoader(R.id.auth_token_loader, null, this);
        }

        loginEdit.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(text: Editable?) {
                if (text.toString().isEmpty())
                    loginField.error = getString(R.string.register_login_empty)
                else
                    loginField.error = ""
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_PHONE_REQUEST_CODE -> {
                if (PermissionsHelper.checkPermissionsResult(grantResults)) {
                    updateLoginEdit()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    private fun updateLoginEdit() {
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.line1Number
        loginEdit.setText(telephonyManager.line1Number)
    }

    private fun onTokenReceived(
        login: String
    ) {
        val result = AccountHelper.registry(login) ?: return
        setAccountAuthenticatorResult(result)
//        setResult(Activity.RESULT_OK)

        ActivityHelper.startActivity(this, SynchronizeActivity::class.java)
        finish()
    }
}
