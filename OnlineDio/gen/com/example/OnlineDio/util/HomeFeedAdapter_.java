//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.example.OnlineDio.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public final class HomeFeedAdapter_
    extends HomeFeedAdapter
{

    private Context context_;

    private HomeFeedAdapter_(Context context) {
        super(context);
        context_ = context;
        init_();
    }

    public void afterSetContentView_() {
        if (!(context_ instanceof Activity)) {
            return ;
        }
    }

    /**
     * You should check that context is an activity before calling this method
     * 
     */
    public View findViewById(int id) {
        Activity activity_ = ((Activity) context_);
        return activity_.findViewById(id);
    }

    @SuppressWarnings("all")
    private void init_() {
        if (context_ instanceof Activity) {
            Activity activity = ((Activity) context_);
        }
        mContext = context_;
    }

    public static HomeFeedAdapter_ getInstance_(Context context) {
        return new HomeFeedAdapter_(context);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
