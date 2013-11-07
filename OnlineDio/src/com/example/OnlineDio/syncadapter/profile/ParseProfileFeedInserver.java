package com.example.OnlineDio.syncadapter.profile;

import com.example.OnlineDio.OnlineDioLinkUrl;
import com.example.OnlineDio.utilities.LoadDataFromServer;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 06/11/2013
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */
public class ParseProfileFeedInserver
{
    public ProfileFeedModel getHomeFeed(final String accessToken, String id) throws JSONException
    {
        String url = OnlineDioLinkUrl.URL_PROFILE_FEED + "?access_token=" + accessToken + "&id=" + id;
        String result = LoadDataFromServer.loadDataWithGetMethod(url);
        JSONObject jsonObject = new JSONObject(result);
        String jsonData = jsonObject.get("data").toString();
        ProfileFeedModel profileFeedModel = new ProfileFeedModel();
        profileFeedModel = new Gson().fromJson(jsonData, ProfileFeedModel.class);
        return profileFeedModel;
    }
}
