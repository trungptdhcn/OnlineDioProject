package com.example.OnlineDio.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.OnlineDio.R;
import com.example.OnlineDio.auth.OnlineDioAccountGeneral;
import com.example.OnlineDio.auth.User;

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
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            Log.d("udinic", TAG + "> finishLogin > addAccountExplicitly");
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = mAuthTokenType;

            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            mAccountManager.addAccountExplicitly(account, accountPassword, intent.getBundleExtra(AccountManager.KEY_USERDATA));
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        } else {
            Log.d("udinic", TAG + "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountPassword);
        }
        Intent i = new Intent(this,NavigationActivity.class);
        i.putExtra(AccountManager.KEY_ACCOUNT_NAME,accountName);
        i.putExtra(AccountManager.KEY_AUTHTOKEN,intent.getStringExtra(AccountManager.KEY_AUTHTOKEN));
        i.putExtra(AccountManager.KEY_ACCOUNT_TYPE,account.type);
        startActivity(i);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
}

