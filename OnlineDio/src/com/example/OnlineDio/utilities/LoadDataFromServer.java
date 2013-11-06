package com.example.OnlineDio.utilities;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 06/11/2013
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
 */
public class LoadDataFromServer
{
    public static String loadDataWithGetMethod(String url)
    {
        String result = null;
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            result = EntityUtils.toString(response.getEntity());

        }
        catch (Exception e)
        {
            result = e.getMessage();
            e.printStackTrace();
        }
        return result;
    }
}
