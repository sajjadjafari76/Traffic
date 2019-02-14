package irstit.transport.Citizens;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.MultipartFormDataBody;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import irstit.transport.DataModel.SpinnerModel;
import irstit.transport.Globals;
import irstit.transport.R;
import irstit.transport.Views.CFProvider;
import irstit.transport.Views.CustomTextView;

public class RegisterObject extends AppCompatActivity implements View.OnClickListener {

    private int GALLERY = 1, CAMERA = 2;
    private String imgDecodableString = "";
    private ViewGroup RegisterObject_Loading;
    private EditText Title, Description, Date;
    private ImageView Picture;
    private PersianDatePickerDialog picker;
    private String mDate = "";
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_object);

        RegisterObject_Loading = findViewById(R.id.RegisterObject_Loading);
        Title = findViewById(R.id.RegisterObject_Title);
        Description = findViewById(R.id.RegisterObject_Description);
        Date = findViewById(R.id.RegisterObject_Date);
        Button RegisterObject_Btn = findViewById(R.id.RegisterObject_Btn);
        Picture = findViewById(R.id.RegisterObject_Picture);
        ImageView Back = findViewById(R.id.RegisterObject_Back);
        Spinner Category = findViewById(R.id.RegisterObject_Category);

        configureDate();

        Picture.setOnClickListener(this);
        Back.setOnClickListener(this);
        Date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    picker.show();
                }
            }
        });

        RegisterObject_Btn.setOnClickListener(view -> {
            if (Title.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "عنوان نمی تواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else if (Description.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "توضیحات نمی تواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else if (mDate.isEmpty()) {
                Toast.makeText(getBaseContext(), "تاریخ نمی تواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else {
                uploadContent(Title.getText().toString(),
                        Description.getText().toString(),
                        mDate,
                        type,
                        imgDecodableString);

                disabledEditText();
                TransitionManager.beginDelayedTransition(RegisterObject_Loading);
                RegisterObject_Loading.setVisibility(View.VISIBLE);
            }
        });

        spinnerAdapter adapter = new spinnerAdapter(this, R.layout.layout_custom_spinner);

        for (int i = 0 ; i < getCategory().size() ; i++) {
            adapter.add(getCategory().get(i).getName());
        }
        adapter.add("یک موضوع را انتخاب کنید!");
        Category.setAdapter(adapter);
        Category.setSelection(adapter.getCount());
        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > (getCategory().size() - 1)) {

                }else {
                    type = String.valueOf(getCategory().get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private List<SpinnerModel> getCategory() {
        List<SpinnerModel> data = new ArrayList<>();

        for (int i = 0 ; i < 6 ; i++) {
            switch (i) {

                case 0:
                    SpinnerModel model = new SpinnerModel();
                    model.setId(5154);
                    model.setName("اوراق بهادر");
                    data.add(model);
                    break;
                case 1:
                    SpinnerModel model2 = new SpinnerModel();
                    model2.setId(5164);
                    model2.setName("وسایل شخصی");
                    data.add(model2);
                    break;
                case 2:
                    SpinnerModel model3 = new SpinnerModel();
                    model3.setId(6164);
                    model3.setName("وسایل الکترونیکی");
                    data.add(model3);
                    break;
                case 3:
                    SpinnerModel model4 = new SpinnerModel();
                    model4.setId(6214);
                    model4.setName("توشه");
                    data.add(model4);
                    break;
                case 4:
                    SpinnerModel model5 = new SpinnerModel();
                    model5.setId(7214);
                    model5.setName("سایر");
                    data.add(model5);
                    break;
            }
        }
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RegisterObject_Picture:

                if (hasMarshmallow()) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        showPictureDialog();
                    } else {
                        CustomTextView mytext = new CustomTextView(this);
                        mytext.setText("مجوز دسترسی داده نشده است!");
                        mytext.setTextColor(Color.WHITE);
                        mytext.setBackgroundColor(Color.rgb(231, 76, 60));
                        mytext.setPadding(17, 17, 17, 17);
                        mytext.setTypeface(CFProvider.getIRANIANSANS(this));
                        Toast toast = new Toast(this);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(mytext);
                        toast.show();
                    }
                } else {
                    showPictureDialog();
                }
                break;
            case R.id.RegisterObject_Back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            Uri tempUri = getImageUri(getBaseContext(), photo);

            File finalFile = new File(getRealPathFromURI(tempUri));
            imgDecodableString = finalFile.getPath();

            Picasso.with(getBaseContext())
                    .load(finalFile)
                    .into(Picture);

//            Page_137_Result.setText(finalFile.getName());

            Log.e("image12", finalFile.getPath() + " | " + (finalFile.getName()) + " |" + finalFile.getParent());


        } else if (requestCode == GALLERY && resultCode == RESULT_OK) {

            try {
                Uri selectedImage = data.getData();

                Log.e("okokiok", "data : " + " |");

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();


                File file = new File(imgDecodableString);
//            Page_137_Result.setText(file.getName());
                Log.e("image12", file.getPath() + " | " + (file.getName()) + " |" + file.getParent());
                Picasso.with(getBaseContext())
                        .load(file)
                        .into(Picture);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public boolean hasMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
//        pictureDialog.setTitle("انتخاب عکس");
        String[] pictureDialogItems = {
                "انتخاب از گالری",
                "دوربین"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    public void uploadContent(String Title, String Description, String Date, String Type, String File) {

        AsyncHttpPost post = new AsyncHttpPost(Globals.APIURL + "/regobjcustomer");
        post.setHeader("token", "df837016d0fc7670f221197cd92439b5");
        post.setTimeout(20000);

        MultipartFormDataBody body = new MultipartFormDataBody();

        body.addStringPart("name", Title);
        body.addStringPart("desc", Description);
        body.addStringPart("time", Date);
        body.addStringPart("type", Type);
        if (!File.isEmpty()) {
            body.addFilePart("img", new File(File));
        }
        post.setBody(body);


        AsyncHttpClient.getDefaultInstance().executeString(post, new AsyncHttpClient.StringCallback() {
            @Override
            public void onCompleted(final Exception e, AsyncHttpResponse source, String result) {

                Log.e("UploadResponse", result + " |");

                runOnUiThread(() -> {
                    try {

                        JSONObject object = new JSONObject(result);
                        RegisterObject_Loading.setVisibility(View.GONE);
                        if (object.getString("status").equals("true")) {
                            enabledEditText();
                            clear();
                            Toast.makeText(getBaseContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

                        } else if (object.getString("status").equals("true")) {
                            enabledEditText();
                            Toast.makeText(getBaseContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception error) {
                        Log.e("GetPhoneError", error.toString() + " |");
                        enabledEditText();
                        RegisterObject_Loading.setVisibility(View.GONE);
                    }
                });


            }
        });

    }

    // for captured image from camera
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // for captured image from camera
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void disabledEditText() {
        Description.setEnabled(false);
        Title.setEnabled(false);
    }

    private void enabledEditText() {
        Description.setEnabled(true);
        Title.setEnabled(true);
    }

    private void clear() {
        Description.setText("");
        Title.setSelection(0);
    }

    public class spinnerAdapter extends ArrayAdapter<String> {

        spinnerAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {

            // TODO Auto-generated method stub
            int count = super.getCount();

            return count > 0 ? count - 1 : count;

        }
    }

    public void configureDate() {
        PersianCalendar calendar = new PersianCalendar(System.currentTimeMillis());
        picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
//                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setInitDate(calendar)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMinYear(1300)
                .setActionTextColor(Color.GRAY)
                .setTypeFace(CFProvider.getIRANIANSANS(getBaseContext()))
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        mDate = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();
                        Date.setText(mDate);
                    }

                    @Override
                    public void onDismissed() {

                    }
                });


    }

}
