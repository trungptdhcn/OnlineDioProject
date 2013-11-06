package com.example.OnlineDio.auth;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 20:27
 * To change this template use File | Settings | File Templates.
 */
public interface OnlineDioServerAuthenticate
{

    User userSignIn(String name, String password, String authTokenType) throws JSONException, IOException;
}
