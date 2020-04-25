package by.a_lzr.globusmanager.account

import android.Manifest
import android.accounts.Account
import android.accounts.AccountManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import by.a_lzr.globusmanager.GlobusApplication
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.permissions.PermissionsHelper
import by.a_lzr.globusmanager.settings.SettingsHelper

object AccountHelper {

    private var account: Account? = null
    private var token: String? = null
//    const val TOKEN_FULL_ACCESS = "com.github.elegion.TOKEN_FULL_ACCESS"
//    const val KEY_PASSWORD = "com.github.elegion.KEY_PASSWORD"

    fun initAccount(name: String?): Account? {
        account =
            if (name == null || name.isEmpty())
                null
            else {
                Log.e("TAG1", name)
                Account(name, GlobusApplication.appContext.getString(R.string.account_type))
            }
        return account
    }

    fun registry(login: String): Bundle? {
        val account = AccountHelper.initAccount(login) ?: return null

        val am = AccountManager.get(GlobusApplication.appContext)
        val password = "123"
        token = "C9D3E678-3036-40B4-9670-140AEDEBAAFF"
        val result = Bundle()

        if (am.addAccountExplicitly(account, password, Bundle())) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            result.putString(AccountManager.KEY_AUTHTOKEN, token)
            am.setAuthToken(account, account.type, token)
        } else {
            result.putString(
                AccountManager.KEY_ERROR_MESSAGE,
                SettingsHelper.getPreferenceString(R.string.register_account_already_exists)
            )
        }
        SettingsHelper.saveAccount(account.name)
        return result
    }

    fun checkRegistry(): Boolean {
        return if (account != null && PermissionsHelper.checkPermissions(
                arrayOf(
                    Manifest.permission.READ_PHONE_STATE
                )
            )
        ) {
            val am = AccountManager.get(GlobusApplication.appContext)
            token = am.peekAuthToken(account, account!!.type)
            !TextUtils.isEmpty(token)
        } else
            false
    }

    fun getAccountName(): String = account!!.name
    fun getAccountType(): String = account!!.type
}