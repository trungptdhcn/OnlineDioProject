package com.example.OnlineDio.network;

import com.example.OnlineDio.auth.User;
import com.example.OnlineDio.network.model.RequestAuthModel;
import com.example.OnlineDio.syncadapter.home.HomeFeedContainer;
import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Post;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 13/11/2013
 * Time: 09:26
 * To change this template use File | Settings | File Templates.
 */
@Rest(rootUrl = "http://113.160.50.84:1009/testing/ica467/trunk/public"
        , converters = {MappingJacksonHttpMessageConverter.class})
public interface ParseInServer
{

    @Get("/home-rest?access_token={access_token}")
    @Accept(MediaType.TEXT_HTML)
    HomeFeedContainer getHomeFeeds(String access_token);

    //authentication
    @Post("/auth-rest")
    @Accept(MediaType.APPLICATION_JSON)
    User userSignIn(RequestAuthModel data);

}

