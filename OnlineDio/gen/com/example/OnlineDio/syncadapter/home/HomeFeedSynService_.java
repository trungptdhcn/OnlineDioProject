//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.example.OnlineDio.syncadapter.home;

import android.content.Context;
import android.content.Intent;

public final class HomeFeedSynService_
    extends HomeFeedSynService
{


    private void init_() {
        sSyncAdapter = HomeFeedSynAdapter_.getInstance_(this);
    }

    @Override
    public void onCreate() {
        init_();
        super.onCreate();
    }

    public static HomeFeedSynService_.IntentBuilder_ intent(Context context) {
        return new HomeFeedSynService_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, HomeFeedSynService_.class);
        }

        public Intent get() {
            return intent_;
        }

        public HomeFeedSynService_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startService(intent_);
        }

    }

}