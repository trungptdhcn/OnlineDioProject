package com.example.OnlineDio.network;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 13/11/2013
 * Time: 09:26
 * To change this template use File | Settings | File Templates.
 */
@Rest(rootUrl = "http://113.160.50.84:1009/testing/ica467/trunk/public/home-rest?access_token="
        ,converters = {GsonHttpMessageConverter.class })
 public interface HomeParseFeedInService
{
    @Get("{access_token}")
    Object getHomeFeeds(String access_token);
}
