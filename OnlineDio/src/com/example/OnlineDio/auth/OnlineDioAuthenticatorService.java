package com.example.OnlineDio.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EService;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 20:28
 * To change this template use File | Settings | File Templates.
 */
@EService
public class OnlineDioAuthenticatorService extends Service
{
    @Bean
    OnlineDioAuthenticator authenticator;

    @Override
    public IBinder onBind(Intent intent)
    {
//        OnlineDioAuthenticator authenticator = new OnlineDioAuthenticator(this);
        return authenticator.getIBinder();
    }
}
