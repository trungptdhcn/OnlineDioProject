package com.example.OnlineDio.util;

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
public class ListViewCustomerAdapter extends SimpleCursorAdapter
{
    private Context mContext;
    private Context appContext;
    private int layout;
    private Cursor cr;
    private final LayoutInflater inflater;


    public ListViewCustomerAdapter(Context context,int layout, Cursor c,String[] from, int[] to, int flags)
    {
        super(context, layout, c, from, to, flags);
        this.layout=layout;
        this.mContext = context;
        this.inflater=LayoutInflater.from(context);
        this.cr=c;
    }

//    public ListViewCustomerAdapter(Context context, ArrayList<HomeFeedModel> listItems)
//    {
//        this.listData = listItems;
//        this.layoutInflater = LayoutInflater.from(context);
//    }
//
//
//    @Override
//    public int getCount()
//    {
//        return listData.size();  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public Object getItem(int position)
//    {
//        return listData.get(position);  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public long getItemId(int position)
//    {
//        return position;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        ViewHolder holder;
//        if (convertView == null)
//        {
//            convertView = layoutInflater.inflate(R.layout.home_row_of_listview2, null);
//            holder = new ViewHolder();
//            holder.titleOfSong = (TextView) convertView.findViewById(R.id.tvTitleOfSong);
//            holder.imge_avata = (ImageView) convertView.findViewById(R.id.ivAvatars);
//
//            holder.nameOfDirector = (TextView) convertView.findViewById(R.id.tvNameOfDirector);
//            holder.numberOfComment = (TextView) convertView.findViewById(R.id.tvNumberOfComment);
//            holder.numberOfLike = (TextView) convertView.findViewById(R.id.tvNumberOfLiked);
//            holder.numberOfPostedDay = (TextView) convertView.findViewById(R.id.tvNumberOfPostedDay);
//            convertView.setTag(holder);
//        }
//        else
//        {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        holder.titleOfSong.setText(listData.get(position).getTitle());
//        holder.imge_avata.setImageResource(R.drawable.home_avatasnhac);
//        holder.nameOfDirector.setText(listData.get(position).getDisplay_name());
//        holder.numberOfLike.setText(""+listData.get(position).getLikes());
//        holder.numberOfComment.setText(""+listData.get(position).getComments());
//        holder.numberOfPostedDay.setText(listData.get(position).getUpdated_at());
//        return convertView;
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return inflater.inflate(layout, null);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
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
