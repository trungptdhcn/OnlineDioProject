package com.example.OnlineDio.network;

import com.googlecode.androidannotations.annotations.EBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 14/11/2013
 * Time: 15:53
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class CustomHeaderInterceptor implements ClientHttpRequestInterceptor
{
    protected String username;
    protected String password;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] data, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException
    {
        httpRequest.getHeaders().add("username", username);
        httpRequest.getHeaders().add("password",password);
        return clientHttpRequestExecution.execute(httpRequest, data);
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
