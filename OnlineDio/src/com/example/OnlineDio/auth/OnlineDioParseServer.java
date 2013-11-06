package com.example.OnlineDio.auth;

import android.util.Log;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 20:26
 * To change this template use File | Settings | File Templates.
 */
public class OnlineDioParseServer implements OnlineDioServerAuthenticate
{
    public static final String URL_SINGIN = "http://113.160.50.84:1009/testing/ica467/trunk/public/auth-rest";

    @Override
    public User userSignIn(String name, String pass, String authTokenType) throws JSONException, IOException
    {
        Log.e("OnlineDio", "Singin Server");
        String authtoken = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject json = new JSONObject();
        json.put("username", name);
        json.put("password", pass);
        json.put("grant_type", "password");
        json.put("client_id", "123456789");

        HttpPost httpPost = new HttpPost(URL_SINGIN);
        httpPost.setHeader("Content-Type", "application/json");
        HttpEntity entity = new StringEntity(json.toString());
        httpPost.setEntity(entity);
        ParseComError parseComError;
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            String reponseString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 200)
            {
                parseComError = new Gson().fromJson(reponseString, ParseComError.class);
                throw new Exception("Error signing-in[" + parseComError.getCode() + "] - " + parseComError.getError());

            }
            parseComError = new Gson().fromJson(reponseString, ParseComError.class);
            if (parseComError.getCode() == 400)
            {
                throw new Exception("Invalid username or password");
            }
            User loginUser = new Gson().fromJson(reponseString, User.class);
            return loginUser;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
