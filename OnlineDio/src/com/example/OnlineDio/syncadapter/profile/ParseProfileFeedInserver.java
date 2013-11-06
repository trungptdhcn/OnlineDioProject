package com.example.OnlineDio.syncadapter.profile;

import com.example.OnlineDio.OnlineDioLinkUrl;
import com.example.OnlineDio.utilities.LoadDataFromServer;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 06/11/2013
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */
public class ParseProfileFeedInserver
{
    public List<ProfileFeedModel> getHomeFeed(final String accessToken, Long id) throws JSONException
    {
        String url = OnlineDioLinkUrl.URL_PROFILE_FEED + "?access_token=" + accessToken + "&id=" + id;
        String result = LoadDataFromServer.loadDataWithGetMethod(url);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonData = (JSONArray) jsonObject.get("data");
        List<ProfileFeedModel> homeFeedDTOList = new ArrayList<ProfileFeedModel>();
        for (int i = 0; i < jsonData.length(); i++)
        {
            homeFeedDTOList.add(new Gson().fromJson(jsonData.getString(i), ProfileFeedModel.class));
        }
        return homeFeedDTOList;
    }
}
