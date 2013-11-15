package com.example.OnlineDio.network.impl;

import com.example.OnlineDio.auth.ParseComError;
import com.example.OnlineDio.network.ParseInServer;
import com.example.OnlineDio.syncadapter.home.HomeFeedContainer;
import com.example.OnlineDio.syncadapter.home.HomeFeedModel;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.rest.RestService;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 13/11/2013
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
@EBean
public class HomeParseFeedInServiceImpl
{
    @RestService
    ParseInServer parseInServer;

    public Object getListHomeFeeds(String authToken) throws JSONException
    {
        HomeFeedContainer homeFeedContainerresult = parseInServer.getHomeFeeds(authToken);
        if (!homeFeedContainerresult.getStatus().equals("success"))
        {
            // Todo refresh token
            ParseComError error = new ParseComError();
            error.setError(homeFeedContainerresult.getStatus());
            return error;
        }
        ArrayList<HomeFeedModel> homeFeedModels = homeFeedContainerresult.getData();
        return homeFeedModels;
    }
}
