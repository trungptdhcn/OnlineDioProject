package com.example.OnlineDio.auth;

import android.accounts.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.example.OnlineDio.activity.LoginActivity;
import com.example.OnlineDio.activity.LoginActivity_;
import com.example.OnlineDio.network.ParseInServer;
import com.example.OnlineDio.network.model.RequestAuthModel;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.rest.RestService;

import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 20:28
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class OnlineDioAuthenticator extends AbstractAccountAuthenticator
{

    @RestService
    ParseInServer parseInServer;

    protected String TAG = "UdinicAuthenticator";
    protected final Context mContext;

    public OnlineDioAuthenticator(Context context)
    {
        super(context);
        this.mContext = context;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException
    {
        Log.d("udinic", TAG + "> addAccount");

        final Intent intent = new Intent(mContext, LoginActivity_.class);
        intent.putExtra(LoginActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(LoginActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(LoginActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException
    {

        Log.d("udinic", TAG + "> getAuthToken");

        // If the caller requested an authToken type we don't support, then
        // return an error
        if (!authTokenType.equals(OnlineDioAccountGeneral.AUTHTOKEN_TYPE_READ_ONLY) && !authTokenType.equals(OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS))
        {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager am = AccountManager.get(mContext);

        String authToken = am.peekAuthToken(account, authTokenType);
        String userId = null; //User identifier, needed for creating ACL on our server-side

        Log.d("udinic", TAG + "> peekAuthToken returned - " + authToken);

        // Lets give another try to authenticate the user
        if (TextUtils.isEmpty(authToken))
        {
            final String password = am.getPassword(account);
            if (password != null)
            {
                try
                {
//                    JSONObject json = new JSONObject();
//                    json.put("username", account.name);
//                    json.put("password", password);
//                    json.put("grant_type", "password");
//                    json.put("client_id", "123456789");
                    Log.d("udinic", TAG + "> re-authenticating with the existing password");
//                    User user = OnlineDioAccountGeneral.sServerAuthenticate.userSignIn(account.name, password, authTokenType);
//                    RequestAuthModel requestData = new RequestAuthModel();
//                    requestData.setUsername(account.name);
//                    requestData.setPassword(password);
//                    requestData.setGrant_type("password");
//                    requestData.setClient_id("123456789");
                    RequestAuthModel requestData = new RequestAuthModel(account.name,password,"password","123456789");
                    User user = getUserData(requestData);
                    if (user != null)
                    {
                        authToken = user.getAccess_token();
                        userId = user.getUser_id();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken))
        {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        final Intent intent = new Intent(mContext, LoginActivity_.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(LoginActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(LoginActivity.ARG_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }


    @Override
    public String getAuthTokenLabel(String authTokenType)
    {
        if (OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType))
        {
            return OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
        }
        else if (OnlineDioAccountGeneral.AUTHTOKEN_TYPE_READ_ONLY.equals(authTokenType))
        {
            return OnlineDioAccountGeneral.AUTHTOKEN_TYPE_READ_ONLY_LABEL;
        }
        else
        {
            return authTokenType + " (Label)";
        }
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException
    {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType)
    {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException
    {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException
    {
        return null;
    }

    User getUserData(RequestAuthModel requestData)
    {
       User user = parseInServer.userSignIn(requestData);
       return user;
    }
}
