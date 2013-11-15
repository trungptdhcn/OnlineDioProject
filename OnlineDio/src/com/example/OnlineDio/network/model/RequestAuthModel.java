package com.example.OnlineDio.network.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 14/11/2013
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class RequestAuthModel implements Serializable
{
    @JsonProperty
    String username;
    @JsonProperty
    String password;
    @JsonProperty
    String grant_type;
    @JsonProperty
    String client_id;

    public RequestAuthModel(String username, String password, String grant_type, String client_id)
    {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
        this.client_id = client_id;
    }
    public RequestAuthModel()
    {

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

    public String getGrant_type()
    {
        return grant_type;
    }

    public void setGrant_type(String grant_type)
    {
        this.grant_type = grant_type;
    }

    public String getClient_id()
    {
        return client_id;
    }

    public void setClient_id(String client_id)
    {
        this.client_id = client_id;
    }
}
