package com.example.OnlineDio.syncadapter.home;

import android.util.Log;
import com.example.OnlineDio.auth.ParseComError;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * User: khiemvx
 * Date: 10/31/13
 */
public class ParseHomeFeedInServer
{
    public Object getShows(String auth) throws Exception
    {

        Log.d("onlinedio", "getShows auth[" + auth + "]");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/home-rest?access_token=" + auth;

        HttpGet httpGet = new HttpGet(url);

        try
        {
            HttpResponse response = httpClient.execute(httpGet);

            String responseString = EntityUtils.toString(response.getEntity());
            Log.d("onlinedio", "getShows> Response= " + responseString);
            if (responseString.equals("cannot access my apis"))
            {
                // Todo refresh token
                ParseComError error = new ParseComError();
                error.setError(responseString);
                return error;
            }
            if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK)
            {
                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                throw new Exception("Error retrieving home feeds [" + error.getCode() + "] - " + error.getError());
            }

            HomeModels homeModels = new Gson().fromJson(responseString, HomeModels.class);
            return homeModels.data;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<HomeFeedModel>();
    }

//    public void putShow(String authtoken, String userId, HomeModel homeFeedAdd) throws Exception {
//
//        Log.d("udinic", "putShow ["+homeFeedAdd.getDisplay_name()+"]");
//
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        String url = "http://192.168.1.222/testing/ica467/trunk/public/home-rest";
//
//        HttpPost httpPost = new HttpPost(url);
//
//        for (Header header : getAppParseComHeaders()) {
//            httpPost.addHeader(header);
//        }
//        httpPost.addHeader("X-Parse-Session-Token", authtoken); // taken from https://parse.com/questions/how-long-before-the-sessiontoken-expires
//        httpPost.addHeader("Content-Type", "application/json");
//
//        JSONObject homeFeed = new JSONObject();
//        homeFeed.put("title", homeFeedAdd.title);
//        homeFeed.put("username", homeFeedAdd.username);
//        homeFeed.put("likes", homeFeedAdd.likes);
//        homeFeed.put("comments", homeFeedAdd.comments);
//        homeFeed.put("created_at", homeFeedAdd.created_at);
//
//        // Creating ACL JSON object for the current user
//        JSONObject acl = new JSONObject();
//        JSONObject aclEveryone = new JSONObject();
//        JSONObject aclMe = new JSONObject();
//        aclMe.put("read", true);
//        aclMe.put("write", true);
//        acl.put(userId, aclMe);
//        acl.put("*", aclEveryone);
//        homeFeed.put("ACL", acl);
//
//        String request = homeFeed.toString();
//        Log.d("onlinedio", "Request = " + request);
//        httpPost.setEntity(new StringEntity(request,"UTF-8"));
//
//        try {
//            HttpResponse response = httpClient.execute(httpPost);
//            String responseString = EntityUtils.toString(response.getEntity());
//            if (response.getStatusLine().getStatusCode() != 201) {
//                ParseComServerAuthenticate.ParseComError error = new Gson().fromJson(responseString, ParseComServerAuthenticate.ParseComError.class);
//                throw new Exception("Error posting home feeds ["+error.code+"] - " + error.error);
//            } else {
////                Log.d("udini", "Response string = " + responseString);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private class HomeModels implements Serializable
    {
        int code;
        String status;
        ArrayList<HomeFeedModel> data;
    }
}