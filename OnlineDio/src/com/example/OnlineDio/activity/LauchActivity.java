package com.example.OnlineDio.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.example.OnlineDio.R;
import com.example.OnlineDio.auth.OnlineDioAccountGeneral;
import com.example.OnlineDio.auth.User;
import com.example.OnlineDio.controller.LauchController;
import com.example.OnlineDio.network.ParseInServer;
import com.example.OnlineDio.network.model.RequestAuthModel;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.rest.RestService;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 15/10/2013
 * Time: 08:56
 * To change this template use File | Settings | File Templates.
 */
@EActivity(R.layout.lauch)
public class LauchActivity extends Activity
{
    @ViewById(R.id.lauch_btloginNormal)
    Button lauch_btLoginNomarl;

    @Bean
    LauchController lauchController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @RestService
    ParseInServer parseInServer;

    @Click(R.id.lauch_btloginNormal)
    void buttonClicked()
    {
        lauchController.getTokenForAccountCreateIfNeeded(OnlineDioAccountGeneral.ACCOUNT_TYPE
                , OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
//        test();
    }

    @Background
    void test()
    {
        RequestAuthModel requestAuthModel = new RequestAuthModel("testing@gmail.com", "25f9e794323b453885f5181f1b624d0b", "password", "123456");
        User user = parseInServer.userSignIn(requestAuthModel);
        Log.e("Sdfasd",user.getAccess_token());
    }
}