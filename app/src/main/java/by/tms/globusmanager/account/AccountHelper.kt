package by.tms.globusmanager.account

import android.accounts.Account
import android.util.Log
import by.tms.globusmanager.MainApplication
import by.tms.globusmanager.R

object AccountHelper {

    private var account: Account? = null
    const val TOKEN_FULL_ACCESS = "com.github.elegion.TOKEN_FULL_ACCESS"
    const val KEY_PASSWORD = "com.github.elegion.KEY_PASSWORD"

    fun initAccount(name: String?): Account? {
        if (name == null || name.isEmpty())
            account = null
        else {
            Log.e("TAG1", name)
            account = Account(name, MainApplication.appContext.getString(R.string.account_type))
        }
        return  account
    }

    fun getAccount() = account
}