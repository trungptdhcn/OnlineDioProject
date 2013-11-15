package com.example.OnlineDio.network.impl;

import com.example.OnlineDio.network.ParseInServer;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.rest.RestService;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 15/11/2013
 * Time: 09:02
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class AuthCheckInServer
{
    @RestService
    ParseInServer parseInServer;

    void checkAuth()
    {

    }
}
