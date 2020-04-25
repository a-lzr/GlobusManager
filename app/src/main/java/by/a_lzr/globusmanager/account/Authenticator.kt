package by.a_lzr.globusmanager.account

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import by.a_lzr.globusmanager.ui.system.RegistrationActivity


class Authenticator(val context: Context) : AbstractAccountAuthenticator(context) {

    override fun getAuthTokenLabel(authTokenType: String?): String? {
//        if (AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType))
//            return AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
//        else if (AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY.equals(authTokenType))
//            return AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY_LABEL;
//        else
//            return authTokenType + " (Label)";
        return null
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        options: Bundle?
    ): Bundle? {
        return null
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle? {
        return null
    }

    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: Account,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        // If the caller requested an authToken type we don't support, then
        // return an error
/*        if (!authTokenType.equals(AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY) && !authTokenType.equals(
                AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS
            )
        ) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType")
            return result
        } */

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        val am = AccountManager.get(context)
        val authToken = am.peekAuthToken(account, authTokenType)

        // Lets give another try to authenticate the user

/*        if (TextUtils.isEmpty(authToken)) {
            val password = am.getPassword(account)
            if (password != null) {
                try {
                    authToken =
                        sServerAuthenticate.userSignIn(account.name, password, authTokenType)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } */

        // If we get an authToken - we return it

        if (!TextUtils.isEmpty(authToken)) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
            return result
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.

        return registry()

/*        val result = Bundle()
        val am = AccountManager.get(context.applicationContext)
        var authToken = am.peekAuthToken(account, authTokenType)
        if (TextUtils.isEmpty(authToken)) {
            val password = am.getPassword(account)
            if (!TextUtils.isEmpty(password)) {
                authToken = AuthTokenLoader.signIn(context, account!!.name, password)
            }
        }
        if (!TextUtils.isEmpty(authToken)) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
        } else {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
            intent.putExtra(LoginActivity.EXTRA_TOKEN_TYPE, authTokenType)
            val bundle = Bundle()
            bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        }
        return result */
    }

    override fun hasFeatures(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        features: Array<out String>?
    ): Bundle? {
//        val result = Bundle()
//        result.putBoolean(KEY_BOOLEAN_RESULT, false)
//        return result

        return null
    }

    override fun editProperties(
        response: AccountAuthenticatorResponse?,
        accountType: String?
    ): Bundle? {
        return null
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle {
        return registry()
    }

    private fun registry(): Bundle {
        val intent = Intent(context, RegistrationActivity::class.java)
//        intent.putExtra(RegistrationActivity.EXTRA_TOKEN_TYPE, accountType)
//        intent.putExtra(RegistrationActivity.ARG_AUTH_TYPE, authTokenType);
//        intent.putExtra(RegistrationActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
//        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)

//        if (options != null) {
//            bundle.putAll(options)
//        }
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }
}