package com.example.OnlineDio.controller;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.OnlineDio.activity.NavigationActivity_;
import com.example.OnlineDio.auth.OnlineDioAccountGeneral;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.UiThread;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 12/11/2013
 * Time: 11:42
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class LauchController
{
    @SystemService
    AccountManager mAccountManager;

    String authToken = null;
    Account mConnectedAccount;

    @RootContext
    Activity activity;

    public void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType)
    {

        Account[] accounts = mAccountManager.getAccountsByType(accountType);
        if (accounts.length == 0)
        {
            final AccountManagerFuture<Bundle> future = mAccountManager.addAccount(accountType, authToken, null, null, activity, new AccountManagerCallback<Bundle>()
            {
                @Override
                public void run(AccountManagerFuture<Bundle> future)
                {
                    try
                    {
                        Bundle bnd = future.getResult();
                        Log.d("udinic", "AddNewAccount Bundle is " + bnd);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        showMessage(e.getMessage());
                    }
                }
            }, null);
        }
        else
        {
            int lenght = accounts.length - 1;
            if (lenght > 0)
            {
                for (int i = 0; i < lenght; i++)
                {
                    String authToken = mAccountManager.peekAuthToken(accounts[i], OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
                    mAccountManager.clearPassword(accounts[i]);
                }
            }
            final AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(accounts[lenght], authTokenType, null, activity,
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
                                    Intent i = new Intent(activity, NavigationActivity_.class);
                                    String user_id = mAccountManager.getUserData(mConnectedAccount, "user_id");
                                    i.putExtra(AccountManager.KEY_AUTHTOKEN, authToken);
                                    i.putExtra(AccountManager.KEY_ACCOUNT_NAME, accountName);
                                    i.putExtra(AccountManager.KEY_ACCOUNT_TYPE, mConnectedAccount.type);
                                    i.putExtra("user_id", user_id);
                                    Bundle b = new Bundle();
                                    b.putParcelable("account", mConnectedAccount);
                                    i.putExtras(b);
                                    activity.startActivity(i);
                                    activity.finish();
                                }
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
    }

    @UiThread
    void showMessage(final String msg)
    {
        if (msg == null || msg.trim().equals(""))
        {
            return;
        }
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
