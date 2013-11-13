package com.example.OnlineDio.syncadapter.home;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 13/11/2013
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeFeedContainer
{
    ArrayList<HomeFeedModel> homeFeedModels;

//    @JsonValue
    public ArrayList<HomeFeedModel> getHomeFeedModels()
    {
        return homeFeedModels;
    }
//    @JsonCreator
    public void setHomeFeedModels(ArrayList<HomeFeedModel> homeFeedModels)
    {
        this.homeFeedModels = homeFeedModels;
    }
}
