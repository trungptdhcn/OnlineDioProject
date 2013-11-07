package com.example.OnlineDio.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.*;
import android.content.*;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.example.OnlineDio.OnlineDioLinkUrl;
import com.example.OnlineDio.R;
import com.example.OnlineDio.auth.OnlineDioAccountGeneral;
import com.example.OnlineDio.auth.ParseComError;
import com.example.OnlineDio.syncadapter.DbHelper;
import com.example.OnlineDio.syncadapter.ProviderContract;
import com.example.OnlineDio.syncadapter.profile.ProfileFeedModel;
import com.example.OnlineDio.util.CircularImageView;
import com.example.OnlineDio.util.CropOption;
import com.example.OnlineDio.util.CropOptionAdapter;
import com.example.OnlineDio.util.Image;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.DialogInterface.OnClickListener;

/**
 * Created with IntelliJ IDEA.
 * User: khangpv
 * Date: 10/17/13
 * Time: 8:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProfileActivity extends Activity
{
    private EditText etBirthday;
    private EditText etCountry;
    private int year;
    private int month;
    private int day;
    private Spinner spListCountry;
    private RelativeLayout rlCoverImage;
    private CircularImageView ibProfileIcon;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private ImageButton ibProfileBack;
    private EditText etDisplayName;
    private ImageView ivCancelDisplayName;
    private EditText etFullName;
    private ImageButton ibCleanFullName;
    private Uri mImageCaptureUri;
    private ImageButton profile_imgbtSave;
    private TextView profile_tvPhoneNumber;
    private TextView profile_tvDescription;
    private EditText profile_edDescription;
    private EditText profile_edPhone;
    private CircularImageView profile_imageViewAvata;
    private RelativeLayout profile_imageViewProfile;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    private boolean coverOrBackground = false;

    private AccountManager accountManager;
    private String athToken;

    String[] projection = {
            DbHelper.PROFILE_ID,
            DbHelper.PROFILE_FACEBOOK_ID,
            DbHelper.PROFILE_USERNAME,
            DbHelper.PROFILE_PASS,
            DbHelper.PROFILE_AVATAR,
            DbHelper.PROFILE_COVER_IMAGE,
            DbHelper.PROFILE_DISPLAY_NAME,
            DbHelper.PROFILE_FULL_NAME,
            DbHelper.PROFILE_PHONE,
            DbHelper.PROFILE_BIRTHDAY,
            DbHelper.PROFILE_GENDER,
            DbHelper.PROFILE_COUNTRY_ID,
            DbHelper.PROFILE_STORAGE_PLAN_ID,
            DbHelper.PROFILE_DESCRIPTION,
            DbHelper.PROFILE_CREATED_AT,
            DbHelper.PROFILE_UPDATE_AT,
            DbHelper.PROFILE_SOUNDS,
            DbHelper.PROFILE_FAVORITES,
            DbHelper.PROFILE_LIKES,
            DbHelper.PROFILE_FOLLOWINGS,
            DbHelper.PROFILE_AUDIENCES,
    };
    private AccountManager mAccountManager;
    private RadioButton profile_rdFemale;
    private RadioButton profile_rdMale;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile2);
        mAccountManager = AccountManager.get(this);
        final String[] items = new String[]{"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        etBirthday = (EditText) findViewById(R.id.profile_dpBirthday);
        spListCountry = (Spinner) findViewById(R.id.profile_spListCountry);
        etCountry = (EditText) findViewById(R.id.profile_etCountry);
        rlCoverImage = (RelativeLayout) findViewById(R.id.profile_rlCoverImage);
        ibProfileIcon = (CircularImageView) findViewById(R.id.profile_ivAvatar);
        profile_edPhone = (EditText) findViewById(R.id.profile_etPhoneNumber);
        ibProfileBack = (ImageButton) findViewById(R.id.profile_ibBack);
        etDisplayName = (EditText) findViewById(R.id.profile_etDisplayName);
        ivCancelDisplayName = (ImageView) findViewById(R.id.profile_ivCleanDisplayName);
        etFullName = (EditText) findViewById(R.id.profile_etFullName);
        ibCleanFullName = (ImageButton) findViewById(R.id.profile_ibClearFullName);
        profile_edDescription = (EditText) findViewById(R.id.profile_edtDescription);
        profile_imageViewAvata = (CircularImageView)findViewById(R.id.profile_ivAvatar);
        profile_imageViewProfile = (RelativeLayout)findViewById(R.id.profile_rlCoverImage);
        etCountry.setOnClickListener(clickedCountry);
        etBirthday.setOnClickListener(setBirthdayDate);
        spListCountry.setOnItemSelectedListener(itemSelect);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, dialogInterface);
        dialog = builder.create();
        initialCurrentTime();
        rlCoverImage.setOnClickListener(onClickCoverImage);
        ibProfileIcon.setOnClickListener(onClickProfileImage);
        ibProfileBack.setOnClickListener(onClickProfileBackImage);

        profile_rdFemale = (RadioButton) findViewById(R.id.profile_rdFemale);
        profile_rdMale = (RadioButton) findViewById(R.id.profile_rdMale);

        etDisplayName.addTextChangedListener(textChangeDisplayName);
        etDisplayName.setOnFocusChangeListener(focusChangeDisplayName);

        etFullName.addTextChangedListener(textChangeDisplayName);
        etFullName.setOnFocusChangeListener(focusChangeDisplayName);
        profile_imgbtSave = (ImageButton) findViewById(R.id.profile_ibSave);
        profile_tvDescription = (TextView) findViewById(R.id.profile_edtDescription);
        loadDatdaToActivity();
        profile_imgbtSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String displayName = etDisplayName.getText().toString();
                final String fullName = etDisplayName.getText().toString();
                final String phone = profile_edPhone.getText().toString();
                final String birthday = etBirthday.getText().toString();
                Resources r = getResources();
                final Long gender;
                if (profile_rdMale.isChecked())
                {
                    gender = 0L;
                }
                else
                {
                    gender = 1L;
                }
                final String countryId = r.getStringArray(R.array.codeCountry)[spListCountry.getSelectedItemPosition()];
                final String description = profile_tvDescription.getText().toString();
                final Long finalGender = gender;
                final Boolean[] checksuccessfull = new Boolean[1];
              AsyncTask<String,Void,Boolean> asyncTask =  new AsyncTask<String,Void,Boolean>()
                {
                    Boolean resultcheck;
                    ProgressDialog myPd_ring = new ProgressDialog(ProfileActivity.this);
                    String user_id = getIntent().getStringExtra("user_id");
                    @Override
                    protected Boolean doInBackground(String... params)
                    {
                        try
                        {
                            resultcheck = editInfoProfile(user_id,displayName, fullName,
                                    phone, birthday, finalGender, countryId, description);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        catch (UnsupportedEncodingException e)
                        {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        return resultcheck;
                    }

                    @Override
                    protected void onPreExecute()
                    {
                        super.onProgressUpdate();
                        myPd_ring.setMessage("Waitting login...");
                        myPd_ring.show();
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean)
                    {
                        super.onPostExecute(aBoolean);
                        myPd_ring.cancel();
                        if(aBoolean)
                        {
                            Toast.makeText(getApplicationContext(), "update successfull", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
                    asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    asyncTask.execute();
                }
            }
        });
    }

    private void loadDatdaToActivity()
    {
        String full_name = getDataFromContentProvider().getFull_name().toString();
        String url_avarta = getDataFromContentProvider().getAvatar();
        String url_cover_image = getDataFromContentProvider().getCover_image();
        String display_name = getDataFromContentProvider().getDisplay_name();
        Long gender = getDataFromContentProvider().getGender();
        String birthDay = getDataFromContentProvider().getBirthday();
        String description = getDataFromContentProvider().getDescription();
        String country_id = getDataFromContentProvider().getCountry_id();
        String phone = getDataFromContentProvider().getPhone();
        if (full_name != null)
        {
            etFullName.setText(full_name);
        }
        if (display_name != null)
        {
            etDisplayName.setText(display_name.toString());
        }
        if (gender == 1)
        {
            profile_rdFemale.isChecked();
        }
        else if (gender == 0)
        {
            profile_rdMale.isChecked();
        }
        if (birthDay != null)
        {
            etBirthday.setText(birthDay);

        }
        if (description != null)
        {
            profile_edDescription.setText(description);
        }
        if (country_id != null)
        {
            etCountry.setText(country_id);
        }
        if (phone != null)
        {
            profile_edPhone.setText(phone);
        }
        if(url_avarta != null)
        {
            Image image = new Image(getApplicationContext());
            image.DisplayImage(url_avarta,profile_imageViewAvata);
        }
        if(url_cover_image != null)
        {

        }

    }

    private View.OnFocusChangeListener focusChangeDisplayName = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View view, boolean b)
        {
            switch (view.getId())
            {
                case R.id.profile_etDisplayName:
                    if (!b)
                    {
                        ivCancelDisplayName.setVisibility(View.INVISIBLE);
                    }
                    else if (!etDisplayName.getText().toString().isEmpty() && b)
                    {
                        ivCancelDisplayName.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.profile_etFullName:
                    if (!b)
                    {
                        ibCleanFullName.setVisibility(View.INVISIBLE);
                    }
                    else if (!etFullName.getText().toString().isEmpty() && b)
                    {
                        ibCleanFullName.setVisibility(View.VISIBLE);
                    }
            }

        }
    };
    private TextWatcher textChangeDisplayName = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void afterTextChanged(Editable editable)
        {
            if (!etDisplayName.getText().toString().isEmpty() && etDisplayName.isFocused())
            {
                ivCancelDisplayName.setVisibility(View.VISIBLE);
            }
            else
            {
                ivCancelDisplayName.setVisibility(View.INVISIBLE);
            }

            if (!etFullName.getText().toString().isEmpty() && etFullName.isFocused())
            {
                ibCleanFullName.setVisibility(View.VISIBLE);
            }
            else
            {
                ibCleanFullName.setVisibility(View.INVISIBLE);
            }
        }
    };
    private View.OnClickListener onClickProfileBackImage = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    };
    private OnClickListener dialogInterface = new OnClickListener()
    {

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            //pick from camera
            if (which == 0)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

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
            { //pick from file
                Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
            }
        }

    };
    private View.OnClickListener onClickCoverImage = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            coverOrBackground = true;
            dialog.show();

        }

    };
    private View.OnClickListener onClickProfileImage = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            coverOrBackground = false;
            dialog.show();

        }

    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
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
                    if (!coverOrBackground)
                    {
                        ibProfileIcon.setImageBitmap(photo);
                    }
                    else
                    {
                        Drawable d = new BitmapDrawable(getResources(), photo);
                        //rlCoverImage.setBackground(d);
                        rlCoverImage.setBackgroundDrawable(d);
                        coverOrBackground = false;
                    }
                }

                File f = new File(mImageCaptureUri.getPath());

                if (f.exists())
                {
                    f.delete();
                }

                break;

        }
    }

    private void doCrop()
    {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = this.getPackageManager().queryIntentActivities(intent, 0);

        int size = list.size();

        if (size == 0)
        {
            Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();

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

                    co.title = this.getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = this.getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(this.getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter, new OnClickListener()
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
                            getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }

    private void initialCurrentTime()
    {
        final Calendar c = Calendar.getInstance();
        year = 1990;
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    private View.OnClickListener clickedCountry = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            spListCountry.performClick();
        }
    };
    private View.OnClickListener setBirthdayDate = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            onCreateDialog().show();
        }
    };
    private AdapterView.OnItemSelectedListener itemSelect = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            etCountry.setText(spListCountry.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener()
    {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay)
        {

            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;

            etBirthday.setText(new StringBuilder().append("").append(selectedDay)
                    .append(":").append(selectedMonth + 1).append(":").append(selectedYear)
                    .append(" "));
        }

    };

    protected Dialog onCreateDialog()
    {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    public ProfileFeedModel getDataFromContentProvider()
    {

        Cursor cur = getContentResolver().query(ProviderContract.PROFILE_CONTENT_URI, null, null, null, null);
        ProfileFeedModel profileFeedModel = null;
        if (cur != null)
        {
            cur.moveToFirst();
            profileFeedModel = ProfileFeedModel.fromCursor(cur);
        }
        return profileFeedModel;
    }

    private Boolean editInfoProfile(String id,String displayName, String fullName, String phone,
                                    String birthday, Long gender, String countryId, String description) throws JSONException, UnsupportedEncodingException
    {
        String authToken = getIntent().getStringExtra(AccountManager.KEY_AUTHTOKEN);
        String url = OnlineDioLinkUrl.URL_PROFILE_FEED + "/"+ id + "?access_token=" + authToken;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-Type", "application/json");
        JSONObject json = new JSONObject();
        json.put("display_name", displayName);
        json.put("full_name", fullName);
        json.put("phone", phone);
        json.put("birthday", birthday);
        json.put("gender", gender);
        json.put("country_id", countryId);
        json.put("description", description);
        HttpEntity entity = new StringEntity(json.toString());
        httpPut.setEntity(entity);
        ParseComError parseComError;
        Boolean resultcheck = false;
        try
        {
            HttpResponse response = httpClient.execute(httpPut);
            String reponseString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 200)
            {
                parseComError = new Gson().fromJson(reponseString, ParseComError.class);
                throw new Exception("Error signing-in[" + parseComError.getCode() + "] - " + parseComError.getError());

            }
            if (reponseString.equals("cannot access my apis"))
            {

                String accountName = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                Account account = new Account(accountName, OnlineDioAccountGeneral.ACCOUNT_TYPE);
                mAccountManager.invalidateAuthToken(account.type, authToken);
                String newToken = mAccountManager.getAuthToken(account, OnlineDioAccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true, null, null)
                        .getResult().getString(AccountManager.KEY_AUTHTOKEN);
                json.put("access_token", newToken);
                response = httpClient.execute(httpPut);
                reponseString = EntityUtils.toString(response.getEntity());
            }
            if (response.getStatusLine().getStatusCode() != 200)
            {
                parseComError = new Gson().fromJson(reponseString, ParseComError.class);
                throw new Exception("Error signing-in[" + parseComError.getCode() + "] - " + parseComError.getError());

            }
            parseComError = new Gson().fromJson(reponseString, ParseComError.class);
            if (parseComError.getCode() == 400)
            {
                throw new Exception("Invalid username or password");
            }
            JSONObject jsonObject = (JSONObject) new JSONObject(reponseString).get("data");
            ProfileFeedModel profileFeedModel = new ProfileFeedModel();
            profileFeedModel = new Gson().fromJson(jsonObject.toString(), ProfileFeedModel.class);
            ContentValues value = updateProfileInfo(profileFeedModel);
            int i = getContentResolver().update(ProviderContract.PROFILE_CONTENT_URI, value, null, null);
            if (i == 1)
            {
                resultcheck = true;

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();

            return resultcheck;
        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        return resultcheck;
    }

    public ContentValues updateProfileInfo(ProfileFeedModel profileFeedModel)
    {
        ContentValues values = new ContentValues();
        values.put(DbHelper.PROFILE_ID, profileFeedModel.getId());
        values.put(DbHelper.PROFILE_FACEBOOK_ID, profileFeedModel.getFacebook_id());
        values.put(DbHelper.PROFILE_USERNAME, profileFeedModel.getUsername());
        values.put(DbHelper.PROFILE_PASS, profileFeedModel.getPassword());
        values.put(DbHelper.PROFILE_AVATAR, profileFeedModel.getAvatar());
        values.put(DbHelper.PROFILE_COVER_IMAGE, profileFeedModel.getCover_image());
        values.put(DbHelper.PROFILE_DISPLAY_NAME, profileFeedModel.getDisplay_name());
        values.put(DbHelper.PROFILE_FULL_NAME, profileFeedModel.getFull_name());
        values.put(DbHelper.PROFILE_PHONE, profileFeedModel.getPhone());
        values.put(DbHelper.PROFILE_BIRTHDAY, profileFeedModel.getBirthday());
        values.put(DbHelper.PROFILE_GENDER, profileFeedModel.getGender());
        values.put(DbHelper.PROFILE_COUNTRY_ID, profileFeedModel.getCountry_id());
        values.put(DbHelper.PROFILE_STORAGE_PLAN_ID, profileFeedModel.getStorage_plan_id());
        values.put(DbHelper.PROFILE_DESCRIPTION, profileFeedModel.getDescription());
        values.put(DbHelper.PROFILE_CREATED_AT, profileFeedModel.getCreated_at());
        values.put(DbHelper.PROFILE_UPDATE_AT, profileFeedModel.getUpdated_at());
        values.put(DbHelper.PROFILE_SOUNDS, profileFeedModel.getSounds());
        values.put(DbHelper.PROFILE_FAVORITES, profileFeedModel.getFavorites());
        values.put(DbHelper.PROFILE_LIKES, profileFeedModel.getLikes());
        values.put(DbHelper.PROFILE_FOLLOWINGS, profileFeedModel.getFollowings());
        values.put(DbHelper.PROFILE_AUDIENCES, profileFeedModel.getAudiences());
        return values;
    }
}
