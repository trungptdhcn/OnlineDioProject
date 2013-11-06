package com.example.OnlineDio.auth;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 20:30
 * To change this template use File | Settings | File Templates.
 */
public class OnlineDioAccountGeneral
{
    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "com.example.OnlineDio";

    /**
     * Account name
     */
    public static final String ACCOUNT_NAME = "OnlineDio";

    /**
     * User data fields
     */
    public static final String USERDATA_USER_OBJ_ID = "userObjectId";   //Parse.com object id

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an Udinic account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Udinic account";

    public static final OnlineDioServerAuthenticate sServerAuthenticate = new OnlineDioParseServer();
}
