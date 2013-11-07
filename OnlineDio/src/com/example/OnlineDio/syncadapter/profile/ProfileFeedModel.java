package com.example.OnlineDio.syncadapter.profile;

import android.database.Cursor;
import com.example.OnlineDio.syncadapter.DbHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 06/11/2013
 * Time: 09:29
 * To change this template use File | Settings | File Templates.
 */
public class ProfileFeedModel
{
    private String id ;
    private String facebook_id;
    private String username;
    private String password;
    private String avatar;
    private String cover_image;
    private String display_name;
    private String full_name;
    private String phone;
    private String birthday;
    private Long gender;
    private String country_id;
    private Long storage_plan_id;
    private String description;
    private String created_at;
    private String updated_at;
    private Long sounds;
    private Long favorites;
    private Long likes;
    private Long followings;
    private Long audiences;

    public ProfileFeedModel()
    {

    }
    public ProfileFeedModel(String id,String facebook_id,String username,String password,
                            String avatar,String cover_image,String display_name,String full_name,String phone,
                            String birthday,Long gender,String country_id,Long storage_plan_id,String description,String created_at,
                            String updated_at,Long sounds,Long favorites, Long likes,Long followings,Long audiences)
    {
        this.id = id;
        this.facebook_id = facebook_id;
        this.username = password;
        this.password = password;
        this.avatar = avatar;
        this.cover_image = cover_image;
        this.display_name = display_name;
        this.full_name = full_name;
        this.phone = phone;
        this.birthday = birthday;
        this.gender = gender;
        this.country_id = country_id;
        this.username = username;
        this.storage_plan_id = storage_plan_id;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.sounds = sounds;
        this.favorites = favorites;
        this.likes = likes;
        this.followings = followings;
        this.audiences = audiences;
    }

    public static ProfileFeedModel fromCursor(Cursor curHomeFeeds)
    {
        /* private String id ;
    private String facebook_id;
    private String username;
    private String password;
    private String avatar;
    private String cover_image;
    private String display_name;
    private String full_name;
    private String phone;
    private String birthday;
    private Long gender;
    private String country_id;
    private Long storage_plan_id;
    private String description;
    private String created_at;
    private String updated_at;
    private Long sounds;
    private Long favorites;
    private Long likes;
    private Long followings;
    private Long audiences;*/
        String id = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_ID));
        String facebook_id = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_FACEBOOK_ID));
        String username = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_USERNAME));
        String password = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_PASS));
        String avatar = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_AVATAR));
        String cover_image = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_COVER_IMAGE));
        String display_name = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_DISPLAY_NAME));
        String full_name = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_FULL_NAME));
        String phone = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_PHONE));
        String birthday = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_BIRTHDAY));
        Long gender = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_GENDER));
        String country_id = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_COUNTRY_ID));
        Long storage_plan_id = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_STORAGE_PLAN_ID));
        String description = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_DESCRIPTION));
        String created_at = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_CREATED_AT));
        String updated_at = curHomeFeeds.getString(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_UPDATE_AT));
        Long sounds = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_SOUNDS));
        Long favorites = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_FAVORITES));
        Long likes = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_LIKES));
        Long followings = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_FOLLOWINGS));
        Long audiences = curHomeFeeds.getLong(curHomeFeeds.getColumnIndex(DbHelper.PROFILE_AUDIENCES));

        return new ProfileFeedModel(id,facebook_id,username,password,avatar,cover_image,display_name,
                full_name,phone,birthday,gender,country_id,storage_plan_id,description,created_at,updated_at,sounds,favorites,
                likes,followings,audiences);
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFacebook_id()
    {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id)
    {
        this.facebook_id = facebook_id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getCover_image()
    {
        return cover_image;
    }

    public void setCover_image(String cover_image)
    {
        this.cover_image = cover_image;
    }

    public String getDisplay_name()
    {
        return display_name;
    }

    public void setDisplay_name(String display_name)
    {
        this.display_name = display_name;
    }

    public String getFull_name()
    {
        return full_name;
    }

    public void setFull_name(String full_name)
    {
        this.full_name = full_name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public Long getGender()
    {
        return gender;
    }

    public void setGender(Long gender)
    {
        this.gender = gender;
    }

    public String getCountry_id()
    {
        return country_id;
    }

    public void setCountry_id(String country_id)
    {
        this.country_id = country_id;
    }

    public Long getStorage_plan_id()
    {
        return storage_plan_id;
    }

    public void setStorage_plan_id(Long storage_plan_id)
    {
        this.storage_plan_id = storage_plan_id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public Long getSounds()
    {
        return sounds;
    }

    public void setSounds(Long sounds)
    {
        this.sounds = sounds;
    }

    public Long getFavorites()
    {
        return favorites;
    }

    public void setFavorites(Long favorites)
    {
        this.favorites = favorites;
    }

    public Long getLikes()
    {
        return likes;
    }

    public void setLikes(Long likes)
    {
        this.likes = likes;
    }

    public Long getFollowings()
    {
        return followings;
    }

    public void setFollowings(Long followings)
    {
        this.followings = followings;
    }

    public Long getAudiences()
    {
        return audiences;
    }

    public void setAudiences(Long audiences)
    {
        this.audiences = audiences;
    }


}
