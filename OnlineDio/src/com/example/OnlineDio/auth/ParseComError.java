package com.example.OnlineDio.auth;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 30/10/2013
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 */
public class ParseComError implements Serializable
{
    private int code;
    private String error;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }
}
