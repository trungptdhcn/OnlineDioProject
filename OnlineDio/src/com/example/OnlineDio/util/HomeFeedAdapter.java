package com.example.OnlineDio.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.OnlineDio.R;
import com.example.OnlineDio.syncadapter.DbHelper;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 10/11/13
 * Time: 5:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomeFeedAdapter extends SimpleCursorAdapter
{
    public Context mContext;

    public Cursor cr;


        public HomeFeedAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags)
    {
        super(context, layout, c, from, to, flags);
        this.mContext = context;
        this.cr=c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        return mInflater.inflate(R.layout.home_row_of_listview2, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        cursor = this.cr;
        ViewHolder holder = new ViewHolder();
        holder.titleOfSong = (TextView) view.findViewById(R.id.tvTitleOfSong);
        holder.imge_avata = (ImageView) view.findViewById(R.id.ivAvatars);
        holder.nameOfDirector = (TextView) view.findViewById(R.id.tvNameOfDirector);
        holder.numberOfComment = (TextView) view.findViewById(R.id.tvNumberOfComment);
        holder.numberOfLike = (TextView) view.findViewById(R.id.tvNumberOfLiked);
        holder.numberOfPostedDay = (TextView) view.findViewById(R.id.tvNumberOfPostedDay);

        holder.titleOfSong.setText(cursor.getString(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_TITLE)));
        holder.nameOfDirector.setText(cursor.getString(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_DISPLAY_NAME)));
        holder.numberOfComment.setText(cursor.getString(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_COMMENTS)));
        holder.numberOfLike.setText(cursor.getString(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_LIKES)));
        holder.numberOfPostedDay.setText(cursor.getString(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_UPDATED_AT)));
        Image image = new Image(context);
        image.DisplayImage(cursor.getString(cursor.getColumnIndex(DbHelper.HOMEFEED_COL_AVATAR)), holder.imge_avata);
    }

    static class ViewHolder
    {
        ImageView imge_avata;
        TextView titleOfSong;
        TextView nameOfDirector;
        TextView numberOfLike;
        TextView numberOfComment;
        TextView numberOfPostedDay;

    }
}
