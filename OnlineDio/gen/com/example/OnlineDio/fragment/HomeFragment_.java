//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.example.OnlineDio.fragment;

import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.OnlineDio.R.layout;
import com.googlecode.androidannotations.api.BackgroundExecutor;

public final class HomeFragment_
    extends HomeFragment
{

    private View contentView_;
    private Handler handler_ = new Handler();

    private void init_(Bundle savedInstanceState) {
        injectFragmentArguments_();
        accountManager = ((AccountManager) getActivity().getSystemService(Context.ACCOUNT_SERVICE));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        home_ibNotify = ((ImageButton) findViewById(com.example.OnlineDio.R.id.ibDone));
        home_lvFeeds = ((ListView) findViewById(com.example.OnlineDio.R.id.lvListSongs));
        home_ibOption = ((ImageButton) findViewById(com.example.OnlineDio.R.id.ibOption));
        {
            View view = findViewById(com.example.OnlineDio.R.id.ibOption);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        HomeFragment_.this.homeButtonClicked(view);
                    }

                }
                );
            }
        }
        {
            AdapterView<?> view = ((AdapterView<?> ) findViewById(com.example.OnlineDio.R.id.lvListSongs));
            if (view!= null) {
                view.setOnItemClickListener(new OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        listHomeFeedsCliked();
                    }

                }
                );
            }
        }
        afterViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.home, container, false);
        }
        return contentView_;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterSetContentView_();
    }

    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    public static HomeFragment_.FragmentBuilder_ builder() {
        return new HomeFragment_.FragmentBuilder_();
    }

    private void injectFragmentArguments_() {
        Bundle args_ = getArguments();
        if (args_!= null) {
            if (args_.containsKey("authtoken")) {
                try {
                    authToken = args_.getString("authtoken");
                } catch (ClassCastException e) {
                    Log.e("HomeFragment_", "Could not cast argument to the expected type, the field is left to its default value", e);
                }
            }
            if (args_.containsKey("user_id")) {
                try {
                    user_id = args_.getString("user_id");
                } catch (ClassCastException e) {
                    Log.e("HomeFragment_", "Could not cast argument to the expected type, the field is left to its default value", e);
                }
            }
            if (args_.containsKey("authAccount")) {
                try {
                    accountName = args_.getString("authAccount");
                } catch (ClassCastException e) {
                    Log.e("HomeFragment_", "Could not cast argument to the expected type, the field is left to its default value", e);
                }
            }
        }
    }

    @Override
    public void synBeforeViewList() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    HomeFragment_.super.synBeforeViewList();
                } catch (RuntimeException e) {
                    Log.e("HomeFragment_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void uiUpdate() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    HomeFragment_.super.uiUpdate();
                } catch (RuntimeException e) {
                    Log.e("HomeFragment_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void backgroundRefresh() {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    HomeFragment_.super.backgroundRefresh();
                } catch (RuntimeException e) {
                    Log.e("HomeFragment_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public HomeFragment build() {
            HomeFragment_ fragment_ = new HomeFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

        public HomeFragment_.FragmentBuilder_ authToken(String authToken) {
            args_.putString("authtoken", authToken);
            return this;
        }

        public HomeFragment_.FragmentBuilder_ user_id(String user_id) {
            args_.putString("user_id", user_id);
            return this;
        }

        public HomeFragment_.FragmentBuilder_ accountName(String accountName) {
            args_.putString("authAccount", accountName);
            return this;
        }

    }

}
