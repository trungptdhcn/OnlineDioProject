package com.example.OnlineDio.controller;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.OnlineDio.activity.LoginActivity;
import com.example.OnlineDio.activity.NavigationActivity_;
import com.example.OnlineDio.auth.OnlineDioAccountGeneral;
import com.example.OnlineDio.auth.User;
import com.example.OnlineDio.network.ParseInServer;
import com.example.OnlineDio.network.model.RequestAuthModel;
import com.example.OnlineDio.syncadapter.DbHelper;
import com.example.OnlineDio.syncadapter.ProviderContract;
import com.example.OnlineDio.syncadapter.profile.ParseProfileFeedInserver;
import com.example.OnlineDio.syncadapter.profile.ProfileFeedModel;
import com.example.OnlineDio.utilities.Utilities;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.rest.RestService;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 12/11/2013
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class LoginController
{
    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
    public final static String PARAM_USER_PASS = "USER_PASS";


    @SystemService
    protected AccountManager mAccountManager;
    protected String mAuthTokenType = OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;

    @RootContext
    LoginActivity activity;
    public final String TAG = this.getClass().getSimpleName();

    @RestService
    ParseInServer parseInServer;

    //    public void submit(String userName,String userPass,String accountType)
//    {
//        final String userName = login_edEmail.getText().toString();
//        final String userPass = login_edPass.getText().toString();
//        final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
//
//        addAccountDoInBackground(userName, userPass, accountType);
//    }
    public void submit() throws UnsupportedEncodingException
    {
        final String userName = activity.getLogin_edEmail().getText().toString();
        final String userPass = Utilities.convertToMd5(activity.getLogin_edPass().getText().toString());
        final String accountType = OnlineDioAccountGeneral.ACCOUNT_TYPE;
        addAccountDoInBackground(userName, userPass, accountType);
    }

    @UiThread
    void updateAccountToAccountManager(Intent intent)
    {
        if (intent.hasExtra(KEY_ERROR_MESSAGE))
        {
            Toast.makeText(activity, intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
        }
        else
        {
            finishLogin(intent);
        }
    }

    @Background
    void addAccountDoInBackground(String userName, String userPass, String accountType)
    {
        Log.d("udinic", TAG + "> Started authenticating");


        Bundle data = new Bundle();
        try
        {
//            JSONObject json = new JSONObject();
//            json.put("username", userName);
//            json.put("password", userPass);
//            json.put("grant_type", "password");
//            json.put("client_id", "123456789");
            RequestAuthModel requestData = new RequestAuthModel();
            requestData.setUsername(userName);
            requestData.setPassword(userPass);
            requestData.setGrant_type("password");
            requestData.setClient_id("123456789");

            User user = getUserData(requestData);
//             = OnlineDioAccountGeneral.sServerAuthenticate.userSignIn(userName, userPass, accountType);

            data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
            data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
            data.putString(AccountManager.KEY_AUTHTOKEN, user.getAccess_token());
            data.putString("userID", user.getUser_id());
            Bundle userData = new Bundle();
            userData.putString(OnlineDioAccountGeneral.USERDATA_USER_OBJ_ID, user.getUser_id());
            data.putBundle(AccountManager.KEY_USERDATA, userData);
            data.putString(PARAM_USER_PASS, userPass);
        }
        catch (Exception e)
        {
            data.putString(KEY_ERROR_MESSAGE, e.getMessage());
        }

        final Intent res = new Intent();
        res.putExtras(data);
        updateAccountToAccountManager(res);
    }

    protected void finishLogin(Intent intent)
    {
        Log.d("udinic", TAG + "> finishLogin");

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        String id = intent.getStringExtra("userID");
        String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        final Account account = new Account(accountName, OnlineDioAccountGeneral.ACCOUNT_TYPE);
        if (activity.getIntent().getBooleanExtra(LoginActivity.ARG_IS_ADDING_NEW_ACCOUNT, false) || mAccountManager.getAccountsByType(OnlineDioAccountGeneral.ACCOUNT_TYPE).length == 0)
        {
            Log.d("udinic", TAG + "> finishLogin > addAccountExplicitly");
            String authtokenType = mAuthTokenType;
            mAccountManager.addAccountExplicitly(account, accountPassword, intent.getBundleExtra(AccountManager.KEY_USERDATA));
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
            mAccountManager.setUserData(account, "user_id", id);
        }
        else
        {
            Log.d("udinic", TAG + "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountPassword);
        }
        startActivityNavigation(intent, accountName, account);
        activity.setAccountAuthenticatorResult(intent.getExtras());
        activity.setResult(activity.RESULT_OK, intent);
        activity.finish();
        getInforProfileAccount(id, authtoken);
    }

    private void startActivityNavigation(Intent intent, String accountName, Account account)
    {
        Intent i = new Intent(activity, NavigationActivity_.class);
        Bundle b = new Bundle();
        b.putParcelable("account", account);
        i.putExtra(AccountManager.KEY_ACCOUNT_NAME, accountName);
        i.putExtra(AccountManager.KEY_AUTHTOKEN, intent.getStringExtra(AccountManager.KEY_AUTHTOKEN));
        i.putExtra(AccountManager.KEY_ACCOUNT_TYPE, account.type);
        i.putExtras(b);
        activity.startActivity(i);
        activity.finish();
    }

    @Background
    public void getInforProfileAccount(final String user_id, final String athToken)
    {
        final ParseProfileFeedInserver parseProfileFeedInserver = new ParseProfileFeedInserver();
        ProfileFeedModel profileFeedModel = new ProfileFeedModel();
        try
        {
            profileFeedModel = parseProfileFeedInserver.getHomeFeed(athToken, user_id);
            ContentValues values = new ContentValues();
            values.put(DbHelper.PROFILE_ID, profileFeedModel.getId());
            values.put(DbHelper.PROFILE_FACEBOOK_ID, profileFeedModel.getFacebook_id());
            values.put(DbHelper.PROFILE_USERNAME, profileFeedModel.getUsername());
            values.put(DbHelper.PROFILE_PASS, profileFeedModel.getPassword());
            values.put(DbHelper.PROFILE_AVATAR, profileFeedModel.getAvatar());
            values.put(DbHelper.PROFILE_COVER_IMAGE, profileFeedModel.getCover_image());
            values.put(DbHelper.PROFILE_DISPLAY_NAME, profileFeedModel.getDisplay_name());
            values.put(DbHelper.PROFILE_FULL_NAME, profileFeedModel.getFull_name());
            values.put(DbHelper.PROFILE_PHONE, profileFeedModel.getPhone());
            values.put(DbHelper.PROFILE_BIRTHDAY, profileFeedModel.getBirthday());
            values.put(DbHelper.PROFILE_GENDER, profileFeedModel.getGender());
            values.put(DbHelper.PROFILE_COUNTRY_ID, profileFeedModel.getCountry_id());
            values.put(DbHelper.PROFILE_STORAGE_PLAN_ID, profileFeedModel.getStorage_plan_id());
            values.put(DbHelper.PROFILE_DESCRIPTION, profileFeedModel.getDescription());
            values.put(DbHelper.PROFILE_CREATED_AT, profileFeedModel.getCreated_at());
            values.put(DbHelper.PROFILE_UPDATE_AT, profileFeedModel.getUpdated_at());
            values.put(DbHelper.PROFILE_SOUNDS, profileFeedModel.getSounds());
            values.put(DbHelper.PROFILE_FAVORITES, profileFeedModel.getFavorites());
            values.put(DbHelper.PROFILE_LIKES, profileFeedModel.getLikes());
            values.put(DbHelper.PROFILE_FOLLOWINGS, profileFeedModel.getFollowings());
            values.put(DbHelper.PROFILE_AUDIENCES, profileFeedModel.getAudiences());
            Uri deleteInfoUri = ProviderContract.PROFILE_CONTENT_URI.buildUpon().appendPath(Long.toString(Long.parseLong(user_id))).build();
            activity.getContentResolver().delete(deleteInfoUri, null, null);
            activity.getContentResolver().insert(ProviderContract.PROFILE_CONTENT_URI, values);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    User getUserData(RequestAuthModel requestData)
    {
        User user = parseInServer.userSignIn(requestData);

        return user;
    }
}
