package com.example.OnlineDio.auth;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class User
{
    //    @JsonProperty("user_id")
    private String code;

    private String status ;

    ArrayList<String>  message;

    private String user_id;
//    @JsonProperty("access_token")
    private String access_token;

//    @JsonProperty("client_id")
    private String client_id;

//    @JsonProperty("expires")
    private String expires;

//    @JsonProperty("scope")
    private String scope;

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getAccess_token()
    {
        return access_token;
    }

    public void setAccess_token(String access_token)
    {
        this.access_token = access_token;
    }

    public String getClient_id()
    {
        return client_id;
    }

    public void setClient_id(String client_id)
    {
        this.client_id = client_id;
    }

    public String getExpires()
    {
        return expires;
    }

    public void setExpires(String expires)
    {
        this.expires = expires;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public ArrayList<String> getMessage()
    {
        return message;
    }

    public void setMessage(ArrayList<String> message)
    {
        this.message = message;
    }
}
