package com.example.OnlineDio.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.example.OnlineDio.R;
import com.example.OnlineDio.auth.OnlineDioAccountGeneral;
import com.example.OnlineDio.controller.LauchController;
import com.example.OnlineDio.network.HomeParseFeedInService;
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

    @RestService
    HomeParseFeedInService homeParseFeedInService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Click(R.id.lauch_btloginNormal)
    void buttonClicked()
    {
        lauchController.getTokenForAccountCreateIfNeeded(OnlineDioAccountGeneral.ACCOUNT_TYPE
                , OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
        test();

    }

    @Background
    void test()
    {
        String a = homeParseFeedInService.getHomeFeeds("0cf74624cd3fda89b7c45e8a67853905767746de").toString();
        Log.e("asdfasd",a);
    }
}