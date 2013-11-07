package com.example.OnlineDio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.OnlineDio.R;
import com.example.OnlineDio.auth.OnlineDioAccountGeneral;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 15/10/2013
 * Time: 08:56
 * To change this template use File | Settings | File Templates.
 */
public class LauchActivity extends Activity
{
    private Button lauch_btloginNormal;
    private AccountManager mAccountManager;
    private String authToken = null;
    private Account mConnectedAccount;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lauch);
        mAccountManager = AccountManager.get(this);
        lauch_btloginNormal = (Button) findViewById(R.id.lauch_btloginNormal);
        lauch_btloginNormal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getTokenForAccountCreateIfNeeded(OnlineDioAccountGeneral.ACCOUNT_TYPE, OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        });
    }

    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType)
    {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>()
                {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future)
                    {
                        Bundle bnd = null;
                        try
                        {
                            bnd = future.getResult();
                            authToken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            if (authToken != null)
                            {
                                String accountName = bnd.getString(AccountManager.KEY_ACCOUNT_NAME);
                                mConnectedAccount = new Account(accountName, OnlineDioAccountGeneral.ACCOUNT_TYPE);
                                Intent i = new Intent(getApplicationContext(),NavigationActivity.class);
                                String user_id = mAccountManager.getUserData(mConnectedAccount,"user_id");
                                i.putExtra(AccountManager.KEY_AUTHTOKEN,authToken);
                                i.putExtra(AccountManager.KEY_ACCOUNT_NAME,accountName);
                                i.putExtra(AccountManager.KEY_ACCOUNT_TYPE,mConnectedAccount.type);
                                i.putExtra("user_id",user_id);
                                Bundle b = new Bundle();
                                b.putParcelable("account",mConnectedAccount);
                                i.putExtras(b);
                                startActivity(i);
                            }
                            showMessage(((authToken != null) ? "SUCCESS!\ntoken: " + authToken : "FAIL"));
                            Log.d("udinic", "GetTokenForAccount Bundle is " + bnd);

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                }
                , null);
    }
    private void showMessage(final String msg) {
        if (msg == null || msg.trim().equals(""))
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}