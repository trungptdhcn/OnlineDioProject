package com.example.OnlineDio.fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.OnlineDio.R;
import com.example.OnlineDio.util.CircularImageView;
import com.example.OnlineDio.util.CropOption;
import com.example.OnlineDio.util.CropOptionAdapter;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 16/10/2013
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
@EFragment(R.layout.content_layout)
public class ContentFragment extends Fragment
{
    @ViewById(R.id.content_rbThumbnail)
    protected RadioButton content_rbThumbnail;

    @ViewById(R.id.content_rbDetail)
    protected RadioButton content_rbDetail;

    @ViewById(R.id.content_rbComment)
    protected RadioButton content_rbComment;


    @ViewById(R.id.content_btBack)
    protected ImageButton content_btBack;

    @ViewById(R.id.content_btnPlay)
    protected ImageButton content_btnPlay;

    @ViewById(R.id.content_imgAvatar)
    protected CircularImageView content_imgAvatar;

    ArrayAdapter<String> adapter;

    AlertDialog.Builder builder;

    LinearLayout layoutDrawer;
    DrawerLayout drawer;

    protected Uri mImageCaptureUri;
    protected static final int PICK_FROM_CAMERA = 1;
    protected static final int CROP_FROM_CAMERA = 2;
    protected static final int PICK_FROM_FILE = 3;

    final String[] items = new String[]{"Take from camera", "Select from gallery"};

    protected Button content_btPlayMusic;
    protected MediaPlayer mediaPlayer;
    protected SeekBar content_SeekbarMusic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.content_layout, container, false);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.navigation_drawer_layout);
        return view;
    }

    @Click({R.id.content_btnPlay, R.id.content_btBack,R.id.content_rbComment,R.id.content_rbDetail,R.id.content_rbThumbnail})
    void contentButtonClicked(View clickedView)
    {
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        switch (clickedView.getId())
        {
            case R.id.content_btnPlay:
                break;
            case R.id.content_btBack:
                getFragmentManager().popBackStack();
                break;
            case R.id.content_rbComment:
                CommentFragment commentFragment = new CommentFragment();
                tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                tx.replace(R.id.content_frame_layout, commentFragment);
                tx.commit();
                break;
            case R.id.content_rbDetail:
                DetailFragment detailFragment = new DetailFragment();
                tx.replace(R.id.content_frame_layout, detailFragment);
                tx.commit();
                break;
            case R.id.content_rbThumbnail:
                ThumbnailFragment thumbnailFragment = new ThumbnailFragment();
                tx.replace(R.id.content_frame_layout, thumbnailFragment);
                tx.commit();
                break;
        }
    }

    @AfterViews
    void afterViews()
    {
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        ThumbnailFragment thumbnailFragment = new ThumbnailFragment();
        tx.replace(R.id.content_frame_layout, thumbnailFragment);
        tx.commit();
        createImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        choseWayCreateImage(requestCode, data);

    }
    //Create Image -======================================================================================
    protected void createImage()
    {
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                if (item == 0)
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                            "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    try
                    {
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    }
                    catch (ActivityNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                }
            }
        }
        );
        final AlertDialog dialog = builder.create();
        content_imgAvatar.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                dialog.show();
            }
        }

        );
    }
    protected void choseWayCreateImage(int requestCode, Intent data)
    {
        switch (requestCode)
        {
            case PICK_FROM_CAMERA:
                doCrop();

                break;

            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();

                doCrop();

                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();

                if (extras != null)
                {
                    Bitmap photo = extras.getParcelable("data");

                    content_imgAvatar.setImageBitmap(photo);
                }

                File f = new File(mImageCaptureUri.getPath());

                if (f.exists())
                {
                    f.delete();
                }
                break;
        }
    }

    protected void doCrop()
    {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);

        int size = list.size();

        if (size == 0)
        {
            Toast.makeText(getActivity(), "Can not find image crop app", Toast.LENGTH_SHORT).show();

            return;
        }
        else
        {
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            if (size == 1)
            {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            }
            else
            {
                for (ResolveInfo res : list)
                {
                    final CropOption co = new CropOption();

                    co.title = getActivity().getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = getActivity().getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(getActivity().getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int item)
                    {
                        startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialog)
                    {

                        if (mImageCaptureUri != null)
                        {
                            getActivity().getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }
}

