package com.example.OnlineDio.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.OnlineDio.R;
import com.example.OnlineDio.auth.OnlineDioAccountGeneral;
import com.example.OnlineDio.auth.User;
import com.example.OnlineDio.syncadapter.DbHelper;
import com.example.OnlineDio.syncadapter.ProviderContract;
import com.example.OnlineDio.syncadapter.profile.ParseProfileFeedInserver;
import com.example.OnlineDio.syncadapter.profile.ProfileFeedModel;
import org.json.JSONException;

public class LoginActivity extends AccountAuthenticatorActivity
{
    /**
     * Called when the activity is first created.
     */
    private Button login_btDone;
    private Button login_btBack;
    private EditText login_edEmail;
    private EditText login_edPass;
    private ImageButton login_iv_cancelOfEmail;
    private ImageButton login_iv_cancelOfPass;
    private TextView login_tvForgotPass;
    private boolean typedEmail = false;
    private boolean typedPass = false;
    private String dumEmail = "quachtest@gmail.com";
    private String dumPass = "e10adc3949ba59abbe56e057f20f883e";

    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";

    public final static String PARAM_USER_PASS = "USER_PASS";

    private final int REQ_SIGNUP = 1;

    private final String TAG = this.getClass().getSimpleName();

    private AccountManager mAccountManager;
    private String mAuthTokenType;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAccountManager = AccountManager.get(getBaseContext());
        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;

        login_btDone = (Button) findViewById(R.id.login_btDone);
        login_btBack = (Button) findViewById(R.id.login_btBack);
        login_edEmail = (EditText) findViewById(R.id.login_et_email);
        login_edPass = (EditText) findViewById(R.id.login_et_Pass);

        login_iv_cancelOfEmail = (ImageButton) findViewById(R.id.login_ib_cancelOfEmail);
        login_iv_cancelOfPass = (ImageButton) findViewById(R.id.login_ib_cancelOfPass);
        login_btDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                submit();
            }
        });

    }

    public void submit() {

        final String userName = login_edEmail.getText().toString();
        final String userPass = login_edPass.getText().toString();
        final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);

        new AsyncTask<String, Void, Intent>() {

            @Override
            protected Intent doInBackground(String... params) {

                Log.d("udinic", TAG + "> Started authenticating");

                Bundle data = new Bundle();
                try {
                    User user = OnlineDioAccountGeneral.sServerAuthenticate.userSignIn(userName, userPass, mAuthTokenType);

                    data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                    data.putString(AccountManager.KEY_AUTHTOKEN, user.getAccess_token());
                    data.putString("userID",user.getUser_id());

                    // We keep the user's object id as an extra data on the account.
                    // It's used later for determine ACL for the data we send to the Parse.com service
                    Bundle userData = new Bundle();
                    userData.putString(OnlineDioAccountGeneral.USERDATA_USER_OBJ_ID, user.getUser_id());
                    data.putBundle(AccountManager.KEY_USERDATA, userData);

                    data.putString(PARAM_USER_PASS, userPass);

                } catch (Exception e) {
                    data.putString(KEY_ERROR_MESSAGE, e.getMessage());
                }

                final Intent res = new Intent();
                res.putExtras(data);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    finishLogin(intent);
                }
            }
        }.execute();
    }
    private void finishLogin(Intent intent) {
        Log.d("udinic", TAG + "> finishLogin");

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        String id = intent.getStringExtra("userID");
        String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        getInforProfileAccount(id,authtoken);
        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            Log.d("udinic", TAG + "> finishLogin > addAccountExplicitly");

            String authtokenType = mAuthTokenType;

            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            mAccountManager.addAccountExplicitly(account, accountPassword, intent.getBundleExtra(AccountManager.KEY_USERDATA));
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
            mAccountManager.setUserData(account,"user_id",id);
        } else {
            Log.d("udinic", TAG + "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountPassword);
        }
        Intent i = new Intent(this,NavigationActivity.class);
        Bundle b = new Bundle();
        b.putParcelable("account", account);
        i.putExtra(AccountManager.KEY_ACCOUNT_NAME,accountName);
        i.putExtra(AccountManager.KEY_AUTHTOKEN,intent.getStringExtra(AccountManager.KEY_AUTHTOKEN));
        i.putExtra(AccountManager.KEY_ACCOUNT_TYPE,account.type);
        i.putExtras(b);
        startActivity(i);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
    public void getInforProfileAccount(final String user_id, final String athToken)
    {
        final ParseProfileFeedInserver parseProfileFeedInserver = new ParseProfileFeedInserver();
        new AsyncTask<String, String, ProfileFeedModel>()
        {
            ProfileFeedModel profileFeedModel = new ProfileFeedModel();
            @Override
            protected ProfileFeedModel doInBackground(String... params)
            {
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
//                    getContentResolver().delete(ProviderContract.PROFILE_CONTENT_URI,null,null);
                    getContentResolver().insert(ProviderContract.PROFILE_CONTENT_URI,values);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return profileFeedModel;
            }
        }.execute();

    }
}

