package com.example.OnlineDio.syncadapter.home;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 13/11/2013
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeFeedContainer
{
    //    ArrayList<HomeFeedModel> homeFeedModels;
//
////    @JsonValue
//    public ArrayList<HomeFeedModel> getHomeFeedModels()
//    {
//        return homeFeedModels;
//    }
////    @JsonCreator
//    public void setHomeFeedModels(ArrayList<HomeFeedModel> homeFeedModels)
//    {
//        this.homeFeedModels = homeFeedModels;
//    }
    @JsonProperty("code")
    public int code;

    @JsonProperty("status")
    public String status;

    @JsonProperty("data")
    public ArrayList<HomeFeedModel> data;

    public int getCode()
{
    return code;
}

    public void setCode(int code)
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

    public ArrayList<HomeFeedModel> getData()
    {
        return data;
    }

    public void setData(ArrayList<HomeFeedModel> data)
    {
        this.data = data;
    }
}
