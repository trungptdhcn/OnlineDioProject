package com.example.OnlineDio.activity;

import android.accounts.AccountAuthenticatorActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.OnlineDio.R;
import com.example.OnlineDio.controller.LoginController;
import com.googlecode.androidannotations.annotations.*;

import java.io.UnsupportedEncodingException;

@NoTitle
@EActivity(R.layout.login)
public class LoginActivity extends AccountAuthenticatorActivity
{
    @ViewById(R.id.login_btDone)
    protected ImageButton login_btDone;

    @ViewById(R.id.login_btBack)
    protected ImageButton login_btBack;

    @ViewById(R.id.login_et_email)
    protected EditText login_edEmail;

    @ViewById(R.id.login_et_Pass)
    protected EditText login_edPass;

    @ViewById(R.id.login_ib_cancelOfEmail)
    protected ImageButton login_iv_cancelOfEmail;

    @ViewById(R.id.login_ib_cancelOfPass)
    protected ImageButton login_iv_cancelOfPass;

    @Bean
    LoginController loginController;


    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    @Click({R.id.login_btDone})
    void buttonClick()
    {
        try
        {
            loginController.submit();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public EditText getLogin_edEmail()
    {
        return login_edEmail;
    }

    public void setLogin_edEmail(EditText login_edEmail)
    {
        this.login_edEmail = login_edEmail;
    }

    public EditText getLogin_edPass()
    {
        return login_edPass;
    }

    public void setLogin_edPass(EditText login_edPass)
    {
        this.login_edPass = login_edPass;
    }

}

