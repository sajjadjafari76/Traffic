package irstit.transport.Citizens.complaint;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.anychart.core.stock.indicators.EMA;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.MultipartFormDataBody;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.DataModel.LetterRateModel;
import irstit.transport.Globals;
import irstit.transport.LetterRate;
import irstit.transport.R;
import irstit.transport.ViewPager.MainPager;
import irstit.transport.VoiceRecoeder;

import static android.content.Context.MODE_PRIVATE;

public class Complaint extends AppCompatActivity {

    private EditText Text, Name, Family, Email, Phone, CarCode, CarPelake;
    private Spinner Category;
    private spinnerAdapter adapter;
    private Button Btn;
    private String type = "", selectedVideoPath;
    private RelativeLayout Loading;
    private int GALLERY = 1, CAMERA = 2, GALLERYVIDEO = 3, VOICE = 4;
    private String imgDecodableString = "";
    private LinearLayout Image, Video, Voice;
    private TextView Result;
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        adapter = new spinnerAdapter(this, R.layout.layout_custom_spinner);
        Category = findViewById(R.id.Complaint_Category);
        Text = findViewById(R.id.Complaint_Text);
        Name = findViewById(R.id.Complaint_Name);
        Family = findViewById(R.id.Complaint_Family);
        Email = findViewById(R.id.Complaint_Email);
        Phone = findViewById(R.id.Complaint_Phone);
        CarCode = findViewById(R.id.Complaint_CarCode);
        CarPelake = findViewById(R.id.Complaint_carPelak);
        Btn = findViewById(R.id.Complaint_Btn);
        Loading = findViewById(R.id.Complaint_Loading);
        Image = findViewById(R.id.Complaint_Image);
        Video = findViewById(R.id.Complaint_Video);
        Voice = findViewById(R.id.Complaint_Voice);
        Text = findViewById(R.id.Complaint_Text);
        Result = findViewById(R.id.Complaint_Result);


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            }
        }


//        if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED) {
//
//                // Should we show an explanation?
//                if (shouldShowRequestPermissionRationale(
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    // Explain to the user why we need to read the contacts
//                }
//
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//
//            }
//        }


        for (int i = 0; i < getCategory().size(); i++) {
            adapter.add(getCategory().get(i).getText());
        }
        adapter.add("موضوع خود را انتخاب کنید");
        Category.setAdapter(adapter);
        Category.setSelection(adapter.getCount());
        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > (getCategory().size() - 1)) {

                } else {
                    type = String.valueOf(getCategory().get(position).getImage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


       //  VoiceRecoeder gettingVoiceInstance ;
        Btn.setOnClickListener(v -> {

            Log.e("step1", VoiceRecoeder.AudioSavePathInDevice  + " |");

            if (type.isEmpty() || Text.getText().toString().isEmpty()) {
                Toast.makeText(this, "فیلد های شرح پیام نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else if (Name.getText().toString().isEmpty() || Family.getText().toString().isEmpty()) {
                Toast.makeText(this, "فیلد های اطلاعات نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();

            } else {

                if (imgDecodableString != null && !imgDecodableString.equals("")) {

                    Log.e("step2", imgDecodableString  + " |");

                    complaintRequest(Name.getText().toString(), Family.getText().toString(),
                            Email.getText().toString(), Text.getText().toString(), type,
                            CarCode.getText().toString(), CarPelake.getText().toString(), Phone.getText().toString(), imgDecodableString);

                    Loading.setVisibility(View.VISIBLE);
                    Disabled();

                } else if (selectedVideoPath!=null && !selectedVideoPath.equals("")) {
                    Log.e("step3", selectedVideoPath  + " |");

                    complaintRequest(Name.getText().toString(), Family.getText().toString(),
                            Email.getText().toString(), Text.getText().toString(), type,
                            CarCode.getText().toString(), CarPelake.getText().toString(), Phone.getText().toString(), imgDecodableString);

                    Loading.setVisibility(View.VISIBLE);
                    Disabled();

                     Log.e("Audio_recoder_file ",checkShared()+"");
                } else if (VoiceRecoeder.AudioSavePathInDevice != null && !VoiceRecoeder.AudioSavePathInDevice.equals("")) {

                    Log.e("step4", VoiceRecoeder.AudioSavePathInDevice  + " |");

                    Result.setText("فایل صدا");

                //    String fil =VoiceRecoeder.AudioSavePathInDevice;

                    complaintRequest(Name.getText().toString(), Family.getText().toString(),
                            Email.getText().toString(), Text.getText().toString(), type,
                            CarCode.getText().toString(), CarPelake.getText().toString(), Phone.getText().toString(), VoiceRecoeder.AudioSavePathInDevice);

                    Loading.setVisibility(View.VISIBLE);
                    Disabled();

                } else {

                    Toast.makeText(getApplicationContext(), "فایل ضمیمه نمی تواند خالی باشد! ", Toast.LENGTH_LONG).show();
                }

            }
        });



        Image.setOnClickListener(v -> {
            showPictureDialog();
        });

        Video.setOnClickListener(v -> {
            SelectVideo();
        });

        Voice.setOnClickListener(view -> {

            Log.e("jfufu", "jgigil");
            //startActivity(new Intent(getApplicationContext(), Record_Voice.class));
            SelectVoice();

        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "مجوز صادر شد", Toast.LENGTH_SHORT).show();
                // TODO: 10/21/2017 13:58
            } else {
//                Toast.makeText(this, "مجوز صادر نشد", Toast.LENGTH_SHORT).show();
                // TODO: 10/21/2017 13:58
            }
        }

    }


    List<LetterRateModel> getCategory() {
        List<LetterRateModel> data = new ArrayList<>();
        try {
//            if (getIntent().getExtras().getString("data") != null && !getIntent().getExtras().getString("data").isEmpty()) {
//                JSONObject object = new JSONObject(getIntent().getExtras().getString("data"));

//                if (object.getString("status").equals("true")) {

            JSONObject jsonObject = new JSONObject();
            jsonObject = getComplaintArray();
            JSONArray array = new JSONArray(jsonObject.getString("complainttype"));


            for (int i = 0; i < array.length(); i++) {
                LetterRateModel model = new LetterRateModel();
                model.setImage(array.getJSONObject(i).getString("ct_code"));
                model.setText(array.getJSONObject(i).getString("ct_name"));
                data.add(model);
            }

//            } else {

//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            Uri tempUri = getImageUri(getBaseContext(), photo);

            File finalFile = new File(getRealPathFromURI(tempUri));
            imgDecodableString = finalFile.getPath();

//            Picasso.with(getBaseContext())
//                    .load(finalFile)
//                    .into(Picture);

//            Page_137_Result.setText(finalFile.getName());

            Log.e("image12", finalFile.getPath() + " | " + (finalFile.getName()) + " |" + finalFile.getParent());
            Result.setText(finalFile.getName() + "");

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
                Result.setText(file.getName() + "");
//            Page_137_Result.setText(file.getName());
//                Log.e("image12", file.getPath() + " | " + (file.getName()) + " |" + file.getParent());
//                Picasso.with(getBaseContext())
//                        .load(file)
//                        .into(Picture);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK) {
            if (requestCode == GALLERYVIDEO) {
                Log.d("what", "gale");
                if (data != null) {
                    Uri contentURI = data.getData();

                    selectedVideoPath = getPath(contentURI);

                    imgDecodableString = "";

                    Result.setText(new File(selectedVideoPath).getName());
                    Log.d("path", selectedVideoPath);

                }
            }
        }
    }

    private void complaintRequest(String Fname, String LName, String Email, String Message,
                                  String Type, String VehicleCode, String VehiclePelack, String Mobile, String File) {
//        StringRequest complaintRequest = new StringRequest(Request.Method.POST, Globals.APIURL + "/Complaint",
//                response -> {
//                    Log.e("ListLeavesResponse", response + " |");
//
//                    try {
//                        JSONObject object = new JSONObject(response);
//                        if (object.getString("status").equals("true")) {
//
//                            Toast.makeText(this, object.getString("message"), Toast.LENGTH_SHORT).show();
//                            Loading.setVisibility(View.GONE);
//                            Enabled();
//                            Clear();
//
//                        } else if (object.getString("status").equals("false")) {
//                            Loading.setVisibility(View.VISIBLE);
//                            Enabled();
//                        }
//                    } catch (Exception e) {
//                        Log.e("ListLeavesError1", e.toString() + " |");
//                        Loading.setVisibility(View.GONE);
//                        Enabled();
//                    }
//                },
//                error -> {
//                    Log.e("ListLeavesError2", error.toString() + " |");
//                    Loading.setVisibility(View.GONE);
//                    Enabled();
//                }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> map = new HashMap<>();
//                map.put("fname", Fname);
//                map.put("lname", LName);
//                map.put("email", Email);
//                map.put("mobile", Mobile);
//                map.put("message", Message);
//                map.put("typecomplaint", Type);
//                map.put("vehiclecode", VehicleCode);
//                map.put("vehiclepluck", VehiclePelack);
//                return map;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> map = new HashMap<>();
//                map.put("token", "df837016d0fc7670f221197cd92439b5");
//                return map;
//            }
//        };
//
//        complaintRequest.setRetryPolicy(new DefaultRetryPolicy(
//                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(complaintRequest);


        AsyncHttpPost post = new AsyncHttpPost(Globals.APIURL + "/Complaint");
        post.setHeader("token", "df837016d0fc7670f221197cd92439b5");
        post.setTimeout(25000);

        MultipartFormDataBody body = new MultipartFormDataBody();

        body.addStringPart("fname", Fname);
        body.addStringPart("lname", LName);
        body.addStringPart("email", Email);
        body.addStringPart("mobile", Mobile);
        body.addStringPart("message", Message);
        body.addStringPart("typecomplaint", Type);
        body.addStringPart("vehiclecode", VehicleCode);
        body.addStringPart("vehiclepluck", VehiclePelack);

        Log.e("sgdsd44", VoiceRecoeder.AudioSavePathInDevice  + " |" + File);
        if (!File.isEmpty()) {
            Log.e("filesfiles", File + " |");
            body.addFilePart("files", new File(File));
        }
        post.setBody(body);


        AsyncHttpClient.getDefaultInstance().executeString(post, new AsyncHttpClient.StringCallback() {
            @Override
            public void onCompleted(final Exception e, AsyncHttpResponse source, String result) {

                Log.e("UploadResponse", result + " |");

                runOnUiThread(() -> {
                    try {

                        JSONObject object = new JSONObject(result);
                        if (object.getString("status").equals("true")) {

                            Toast.makeText(getBaseContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                            Loading.setVisibility(View.GONE);
                            Enabled();
                            Clear();

                        } else if (object.getString("status").equals("false")) {
                            Loading.setVisibility(View.VISIBLE);
                            Enabled();
                        }

                    } catch (Exception error) {
                        Log.e("GetPhoneError", error.toString() + " |");
                        Loading.setVisibility(View.GONE);
                        Enabled();
                    }
                });


            }
        });


    }

    void Clear() {
        Name.setText("");
        Family.setText("");
        Phone.setText("");
        Email.setText("");
        CarPelake.setText("");
        CarCode.setText("");
        Text.setText("");
        imgDecodableString = "";
    }

    void Enabled() {
        Name.setEnabled(true);
        Family.setEnabled(true);
        Phone.setEnabled(true);
        Email.setEnabled(true);
        CarPelake.setEnabled(true);
        CarCode.setEnabled(true);
        Text.setEnabled(true);
    }

    void Disabled() {
        Name.setEnabled(false);
        Family.setEnabled(false);
        Phone.setEnabled(false);
        Email.setEnabled(false);
        CarPelake.setEnabled(false);
        CarCode.setEnabled(false);
        Text.setEnabled(false);
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
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


    public void SelectVideo() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("video/*");
        startActivityForResult(galleryIntent, GALLERYVIDEO);

    }

    public void SelectVoice() {

        Intent recordVoice = new Intent(getApplicationContext(),VoiceRecoeder.class);
        startActivity(recordVoice);



        // startActivityForResult(recordVoice,VOICE);

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


    private JSONObject getComplaintArray() {

        SharedPreferences sh = getSharedPreferences("complaint", MODE_PRIVATE);
        String Jso = sh.getString("complaintArray", "-1");


        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(Jso);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //  Log.e("from_Complaint",jsonObject);

        return jsonObject;

    }

    private String checkShared() {

        SharedPreferences h = getSharedPreferences("voice", MODE_PRIVATE);
        String getVoice = h.getString("voice_", "-1");
        Log.e("Audio_file",getVoice);
       //String address =  getIntent().getExtras().getString("Audio_source_address");

        return getVoice;

    }

    ;
}
