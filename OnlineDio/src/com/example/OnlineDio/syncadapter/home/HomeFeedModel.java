package com.example.OnlineDio.syncadapter.home;

import android.database.Cursor;
import com.example.OnlineDio.syncadapter.DbHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 04/11/2013
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
public class HomeFeedModel
{
    public Long id;
    public Long user_id;
    public String title;
    public String thumbnail;
    public String desccription;
    public String sound_path;
    public int duration;
    public boolean played;
    public String created_at;
    public String updated_at;
    public int viewed;
    public String username;
    public int likes;
    public int comments;
    public String display_name;
    public String avatar;

    public HomeFeedModel(Long id, Long user_id, String title, String thumbnail, String desccription,
                         String sound_path, int duration, boolean played, String created_at,
                         String updated_at, int viewed, String username, int likes, int comments,
                         String display_name, String avatar)
    {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.desccription = desccription;
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
        Long user_id = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_USER_ID));
        String title = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_TITLE));
        String thumbnail = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_THUMBNAIL));
        String description = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_DESCRIPTION));
        String sound_path = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_SOUND_PATH));
        int duration = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_DURATION));
        boolean played = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_PLAYED)) > 0;
        String created_at = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_CREATED_AT));
        String updated_at = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_UPDATED_AT));
        int likes = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_LIKES));
        int viewed = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_VIEWED));
        int comment = curHomeFeeds.getInt(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_COMMENTS));
        String userName = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_USERNAME));
        String displayName = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_DISPLAY_NAME));
        String avatar = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.HOMEFEED_COL_AVATAR));

        return new HomeFeedModel(id, user_id, title, thumbnail, description, sound_path, duration, played, created_at,
                updated_at, viewed, userName, likes, comment, displayName, avatar);
    }

    public Long getId()
    {
        return id;
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
        return desccription;
    }

    public void setDesccription(String desccription)
    {
        this.desccription = desccription;
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

    public boolean isPlayed()
    {
        return played;
    }

    public void setPlayed(boolean played)
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
