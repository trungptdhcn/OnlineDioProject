//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.example.OnlineDio.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import com.example.OnlineDio.R.layout;
import com.example.OnlineDio.util.CircularImageView;

public final class ContentFragment_
    extends ContentFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        content_btnPlay = ((ImageButton) findViewById(com.example.OnlineDio.R.id.content_btnPlay));
        content_rbComment = ((RadioButton) findViewById(com.example.OnlineDio.R.id.content_rbComment));
        content_imgAvatar = ((CircularImageView) findViewById(com.example.OnlineDio.R.id.content_imgAvatar));
        content_btBack = ((ImageButton) findViewById(com.example.OnlineDio.R.id.content_btBack));
        content_rbThumbnail = ((RadioButton) findViewById(com.example.OnlineDio.R.id.content_rbThumbnail));
        content_rbDetail = ((RadioButton) findViewById(com.example.OnlineDio.R.id.content_rbDetail));
        {
            View view = findViewById(com.example.OnlineDio.R.id.content_btnPlay);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ContentFragment_.this.contentButtonClicked(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.example.OnlineDio.R.id.content_btBack);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ContentFragment_.this.contentButtonClicked(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.example.OnlineDio.R.id.content_rbComment);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ContentFragment_.this.contentButtonClicked(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.example.OnlineDio.R.id.content_rbDetail);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ContentFragment_.this.contentButtonClicked(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.example.OnlineDio.R.id.content_rbThumbnail);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ContentFragment_.this.contentButtonClicked(view);
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
            contentView_ = inflater.inflate(layout.content_layout, container, false);
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

    public static ContentFragment_.FragmentBuilder_ builder() {
        return new ContentFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public ContentFragment build() {
            ContentFragment_ fragment_ = new ContentFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
