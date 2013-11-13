package com.example.OnlineDio.activity;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.OnlineDio.R;
import com.example.OnlineDio.controller.NavigationController;
import com.googlecode.androidannotations.annotations.*;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 14/10/2013
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
@EActivity(R.layout.navigation)
public class NavigationActivity extends FragmentActivity
{
    @ViewById(R.id.left_drawer)
    protected LinearLayout layoutDrawer;

    @ViewById(R.id.navigation_station_layout)
    protected LinearLayout llProfile;

    public static Context context;

    @ViewById(R.id.navigation_drawer_layout)
    DrawerLayout drawer;

    @ViewById(R.id.navigation_lvDrawer)
    ListView navList;

    @Bean
    NavigationController navigationController;

    public NavigationController getNavigationController()
    {
        return navigationController;
    }

    public DrawerLayout getDrawer()
    {
        return drawer;
    }

    public LinearLayout getLayoutDrawer()
    {
        return layoutDrawer;
    }

    public ListView getNavList()
    {
        return navList;
    }

    @AfterViews
    void afterViews()
    {
        navigationController.initObject();
    }

    @Click(R.id.navigation_station_layout)
    void profileClicked()
    {
        navigationController.startProfileActivity();
    }

    @ItemClick(R.id.navigation_lvDrawer)
    void navigationListItemClicked(final int position)
    {
        navigationController.navigationListenerClicked(position);
    }

}
