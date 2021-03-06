package com.example.OnlineDio.syncadapter.home;

import android.database.Cursor;
import com.example.OnlineDio.syncadapter.DbHelper;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeFeedModel implements Serializable
{
//    @JsonProperty("id")
    public Long id;

//    @JsonProperty("account_id")
    public Long account_id;

//    @JsonProperty("user_id")
    public Long user_id;

//    @JsonProperty("title")
    public String title;

//    @JsonProperty("thumbnail ")
    public String thumbnail;

//    @JsonProperty("description")
    public String description;

//    @JsonProperty("sound_path")
    public String sound_path;

//    @JsonProperty("duration")
    public int duration;

//    @JsonProperty("played")
    public String played;

//    @JsonProperty("created_at")
    public String created_at;

//    @JsonProperty("updated_at")
    public String updated_at;

//    @JsonProperty("viewed")
    public int viewed;

//    @JsonProperty("username")
    public String username;

//    @JsonProperty("likes")
    public int likes;

//    @JsonProperty("comments")
    public int comments;

//    @JsonProperty("display_name")
    public String display_name;

//    @JsonProperty("avatar")
    public String avatar;

    public HomeFeedModel()
    {

    }
    public HomeFeedModel(Long id, Long account_id,Long user_id, String title, String thumbnail, String desccription,
                         String sound_path, int duration, String played, String created_at,
                         String updated_at, int viewed, String username, int likes, int comments,
                         String display_name, String avatar)
    {
        this.id = id;
        this.account_id = account_id;
        this.user_id = user_id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = desccription;
        this.sound_path = sound_path;
        this.duration = duration;
        this.played = played;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.viewed = viewed;
        this.username = username;
        this.likes = likes;
        this.comments = comments;
        this.display_name = display_name;
        this.avatar = avatar;
    }


    // Create a object from a cursor
    public static HomeFeedModel fromCursor(Cursor curHomeFeeds)
    {
        Long id = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_ID));
        Long account_id = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_ACCOUNT_ID));
        Long user_id = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_USER_ID));
        String title = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_TITLE));
        String thumbnail = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_THUMBNAIL));
        String description = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_DESCRIPTION));
        String sound_path = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_SOUND_PATH));
        int duration = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_DURATION));
        String played = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_PLAYED));
        String created_at = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_CREATED_AT));
        String updated_at = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_UPDATED_AT));
        int likes = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_LIKES));
        int viewed = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_VIEWED));
        int comment = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_COMMENTS));
        String userName = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_USERNAME));
        String displayName = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_DISPLAY_NAME));
        String avatar = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_AVATAR));

        return new HomeFeedModel(id, account_id,user_id, title, thumbnail, description, sound_path, duration, played, created_at,
                updated_at, viewed, userName, likes, comment, displayName, avatar);
    }

    public Long getId()
    {
        return id;
    }

    public Long getAccount_id()
    {
        return account_id;
    }

    public void setAccount_id(Long account_id)
    {
        this.account_id = account_id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Long user_id)
    {
        this.user_id = user_id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getDesccription()
    {
        return description;
    }

    public void setDesccription(String desccription)
    {
        this.description = desccription;
    }

    public String getSound_path()
    {
        return sound_path;
    }

    public void setSound_path(String sound_path)
    {
        this.sound_path = sound_path;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public String isPlayed()
    {
        return played;
    }

    public void setPlayed(String played)
    {
        this.played = played;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    public String getUpdated_at()
    {
        return updated_at;
    }

    public void setUpdated_at(String updated_at)
    {
        this.updated_at = updated_at;
    }

    public int getViewed()
    {
        return viewed;
    }

    public void setViewed(int viewed)
    {
        this.viewed = viewed;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public int getLikes()
    {
        return likes;
    }

    public void setLikes(int likes)
    {
        this.likes = likes;
    }

    public int getComments()
    {
        return comments;
    }

    public void setComments(int comments)
    {
        this.comments = comments;
    }

    public String getDisplay_name()
    {
        return display_name;
    }

    public void setDisplay_name(String display_name)
    {
        this.display_name = display_name;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
}
