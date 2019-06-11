package irstit.transport.Citizens.complaint;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.MultipartFormDataBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import irstit.transport.AppController.AppController;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.LetterRateModel;
import irstit.transport.DataModel.ThreeCompliant;
import irstit.transport.Globals;
import irstit.transport.MainPage;
import irstit.transport.R;
import irstit.transport.VoiceRecoeder;

import org.json.*;


public class Complaint extends AppCompatActivity {

    private EditText Text, Name, Family, Email, Phone, CarCode, CarPelake;
    private Spinner Category;
    private spinnerAdapter adapter;
    private Button Btn;
    private String type = "", selectedVideoPath ="";
    private RelativeLayout Loading;
    private int GALLERY = 1, CAMERA = 2, GALLERYVIDEO = 3, VOICE = 4;
    public static int NOTPICTURESANDVIDEO = -1;
    private String imgDecodableString = "";
    private LinearLayout Image, Video, Voice;
    private TextView Result;
    public RecyclerView threeRecyclerView;
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 200;
    private ScrollView Page137_Scrool;
    private ProgressBar progressbar;


    //private List<ThreeCompliant> three = new ArrayList<ThreeCompliant>();
    private List<ThreeCompliant> modelofThreeCompliantList = new ArrayList<ThreeCompliant>();


    ThreeRecyclerView complaintAdapter = new ThreeRecyclerView(modelofThreeCompliantList);

    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NOTPICTURESANDVIDEO = -1;

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onstartHabib", "works");


        SharedPreferences getsh = getSharedPreferences("source", MODE_PRIVATE);
        String address_of_file = getsh.getString("filename", "-2");

        if (NOTPICTURESANDVIDEO == 3 && !address_of_file.equals("-2")) {


            Log.e("onstartHabib0", "works");
            Log.e("printContentsofList", modelofThreeCompliantList.toString());
            for (int i = 0; i < modelofThreeCompliantList.size(); i++) {

                Log.e("onstartHabib1", "works");
                Log.e("ListContentsStatus", modelofThreeCompliantList.get(i).getStatus().toString());
                Log.e("ListContentsFilename", modelofThreeCompliantList.get(i).getFileName().toString());


                if (modelofThreeCompliantList.get(i).getStatus().equals("Audio")) {
//
                    modelofThreeCompliantList.remove(i);
                }
            }
            ThreeCompliant audio = new ThreeCompliant();
            audio.setStatus("Audio");
            audio.setFileName(address_of_file);
            modelofThreeCompliantList.add(audio);
            Log.e("addressoffile", address_of_file + "");
            complaintAdapter.notifyDataSetChanged();

        }
//
//                for (int j = 0; j < modelofThreeCompliantList.size(); j++) {
//
//                    Log.e("afterfists0","afterfists_works");
//                    Log.e("afterfists1",modelofThreeCompliantList.get(j).getStatus().toString());
//                    Log.e("afterfists2",modelofThreeCompliantList.get(j).getFileName().toString());
//
//
//
//        }
//


        //   }
//        for (int i = 0; i < modelofThreeCompliantList.size(); i++) {
//
//            if (modelofThreeCompliantList.get(i).getStatus().equals("Sound")) {
//
//                modelofThreeCompliantList.get(i).setFileName(imgDecodableString);
//
//            } else {
//
//                ThreeCompliant camera = new ThreeCompliant();
//                camera.setStatus("Sound");
//                camera.setFileName(imgDecodableString);
//
//            }
//
//        }

    }


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
        Page137_Scrool = findViewById(R.id.Page137_Scrool);
        progressbar = findViewById(R.id.progressbar);
        getComplaintArray();

//        modelofThreeCompliantList.add(new ThreeCompliant());
//        modelofThreeCompliantList.add(new ThreeCompliant());
//        modelofThreeCompliantList.add(new ThreeCompliant());
        fillthree();


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




        //  VoiceRecoeder gettingVoiceInstance ;
        Btn.setOnClickListener(v -> {

            if (type.isEmpty() || Text.getText().toString().isEmpty()) {
                Toast.makeText(this, "فیلد های شرح پیام نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else if (Name.getText().toString().isEmpty() || Family.getText().toString().isEmpty()) {
                Toast.makeText(this, "فیلد های اطلاعات نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();

            } else if ((imgDecodableString != null && !imgDecodableString.equals("") || selectedVideoPath != null && !selectedVideoPath.equals("")) || VoiceRecoeder.AudioSavePathInDevice != null && !VoiceRecoeder.AudioSavePathInDevice.equals("")) {

                complaintRequest(Name.getText().toString(), Family.getText().toString(),
                        Email.getText().toString(), Text.getText().toString(), type,
                        CarCode.getText().toString(), CarPelake.getText().toString(), Phone.getText().toString(), modelofThreeCompliantList);

                Loading.setVisibility(View.VISIBLE);
                Disabled();

            } else {

                Toast.makeText(getApplicationContext(), "فایل ضمیمه نمی تواند خالی باشد! ", Toast.LENGTH_LONG).show();
            }

          /*  Log.e("step1", VoiceRecoeder.AudioSavePathInDevice + " |");

            if (type.isEmpty() || Text.getText().toString().isEmpty()) {
                Toast.makeText(this, "فیلد های شرح پیام نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else if (Name.getText().toString().isEmpty() || Family.getText().toString().isEmpty()) {
                Toast.makeText(this, "فیلد های اطلاعات نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();

            } else {

                if (imgDecodableString != null && !imgDecodableString.equals("")) {

                    Log.e("step2", imgDecodableString + " |");

                    complaintRequest(Name.getText().toString(), Family.getText().toString(),
                            Email.getText().toString(), Text.getText().toString(), type,
                            CarCode.getText().toString(), CarPelake.getText().toString(), Phone.getText().toString(), imgDecodableString);

                    Loading.setVisibility(View.VISIBLE);
                    Disabled();

                } else if (selectedVideoPath != null && !selectedVideoPath.equals("")) {
                    Log.e("step3", selectedVideoPath + " |");

                    complaintRequest(Name.getText().toString(), Family.getText().toString(),
                            Email.getText().toString(), Text.getText().toString(), type,
                            CarCode.getText().toString(), CarPelake.getText().toString(), Phone.getText().toString(), imgDecodableString);

                    Loading.setVisibility(View.VISIBLE);
                    Disabled();

                    Log.e("Audio_recoder_file ", checkShared() + "");
                } else if (VoiceRecoeder.AudioSavePathInDevice != null && !VoiceRecoeder.AudioSavePathInDevice.equals("")) {

                    Log.e("step4", VoiceRecoeder.AudioSavePathInDevice + " |");



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
            */
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


    List<LetterRateModel> getCategory(JSONArray jsonArray) {
        List<LetterRateModel> data = new ArrayList<>();
        try {
//            if (getIntent().getExtras().getString("data") != null && !getIntent().getExtras().getString("data").isEmpty()) {
//                JSONObject object = new JSONObject(getIntent().getExtras().getString("data"));

//                if (object.getString("status").equals("true")) {

//            JSONObject jsonObject = new JSONObject();
//            jsonObject = getComplaintArray();
//            Log.i("jdshfuahf", "getCategory: "+jsonObject);
//            JSONArray array = new JSONArray(jsonObject.getString("complainttype"));

            JSONArray array = jsonArray;

            if (array.length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    LetterRateModel model = new LetterRateModel();
                    model.setImage(array.getJSONObject(i).getString("ct_code"));
                    model.setText(array.getJSONObject(i).getString("ct_name"));
                    data.add(model);
                }

            } else {


                LetterRateModel model = new LetterRateModel();
                model.setImage("عدم دریافت اطلاعات از سرور");
                model.setText(" عدم دریافت اطلاعات از سرور");
                data.add(model);
            }


//            } else {

//            }
        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(),"عدم دریافت اطلاعات از سرور",Toast.LENGTH_LONG).show();
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
            NOTPICTURESANDVIDEO = 0;
//            if (modelofThreeCompliantList.size() == 0) {
//                ThreeCompliant camera = new ThreeCompliant();
//                camera.setStatus("camera");
//                camera.setFileName(imgDecodableString);
//                modelofThreeCompliantList.add(camera);
//                complaintAdapter.notifyDataSetChanged();
//            }
//
//            for (int i = 0; i < modelofThreeCompliantList.size(); i++) {
//
//                if (modelofThreeCompliantList.get(i).getStatus().equals("camera")) {
//
//                    modelofThreeCompliantList.get(i).setFileName(imgDecodableString);
//                    complaintAdapter.notifyDataSetChanged();
//
//                } else {
//
//                    ThreeCompliant camera = new ThreeCompliant();
//                    camera.setStatus("camera");
//                    camera.setFileName(imgDecodableString);
//                    modelofThreeCompliantList.add(camera);
//                    complaintAdapter.notifyDataSetChanged();
//
//                }
//
//            }


            for (int i = 0; i < modelofThreeCompliantList.size(); i++) {

                if (modelofThreeCompliantList.get(i).getStatus().equals("camera")) {
                    modelofThreeCompliantList.remove(i);

                }
            }

            ThreeCompliant camera = new ThreeCompliant();
            camera.setStatus("camera");
            camera.setFileName(imgDecodableString);
            modelofThreeCompliantList.add(camera);
            complaintAdapter.notifyDataSetChanged();

//
//            for (ThreeCompliant ob1 : modelofThreeCompliantList) {
//
//                if ("camera".equals(ob1.getStatus())) {
//
//                    int index = modelofThreeCompliantList.indexOf(ob1);
//                    modelofThreeCompliantList.set(index, camera);
//                    fillthree();
//
//
////                         modelofThreeCompliantList.remove(picturesGallery);
////                         modelofThreeCompliantList.add(picturesGallery);
//                } else {
//
//                    camera.setFileName(imgDecodableString);
//                    modelofThreeCompliantList.add(camera);
//                    fillthree();
//                }
//            }

//
//           if(modelofThreeCompliantList.equals(camera)){
//
//                modelofThreeCompliantList.remove(camera);
//                modelofThreeCompliantList.add(camera);
//                fillthree();
//
//            }
//            else{
//
//                camera.setFileName(imgDecodableString);
//                modelofThreeCompliantList.add(camera);
//                fillthree();
//            }


//            Picasso.with(getBaseContext())
//                    .load(finalFile)
//                    .into(Picture);

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
                NOTPICTURESANDVIDEO = 1;

//                if (modelofThreeCompliantList.size() == 0) {
//                    ThreeCompliant camera = new ThreeCompliant();
//                    camera.setStatus("Gallery");
//                    camera.setFileName(imgDecodableString);
//                    modelofThreeCompliantList.add(camera);
//                    complaintAdapter.notifyDataSetChanged();
//                    Log.e("state", "null");
//                }
//
//                for (int i = 0; i < modelofThreeCompliantList.size(); i++) {
//
//                    if (modelofThreeCompliantList.get(i).getStatus().equals("Gallery")) {
//                        Log.e("state", "exist");
//                        modelofThreeCompliantList.get(i).setFileName(imgDecodableString);
//                        complaintAdapter.notifyDataSetChanged();
//
//                    } else {
//                        Log.e("state", "Not Exist");
//
//                        ThreeCompliant camera = new ThreeCompliant();
//                        camera.setStatus("Gallery");
//                        camera.setFileName(imgDecodableString);
//                        modelofThreeCompliantList.add(camera);
//                        complaintAdapter.notifyDataSetChanged();
//
//                    }
//
//                }

                for (int i = 0; i < modelofThreeCompliantList.size(); i++) {

                    if (modelofThreeCompliantList.get(i).getStatus().equals("camera")) {
                        modelofThreeCompliantList.remove(i);

                    }
                }

                ThreeCompliant camera = new ThreeCompliant();
                camera.setStatus("camera");
                camera.setFileName(imgDecodableString);
                modelofThreeCompliantList.add(camera);
                complaintAdapter.notifyDataSetChanged();
                //save to model for recyclerView
//                ThreeCompliant picturesGallery = new ThreeCompliant();
//                picturesGallery.setStatus("video");
//                picturesGallery.setFileName(imgDecodableString);
//                Log.e("videoGallery_0", picturesGallery.getStatus());
//
//                for (ThreeCompliant ob1 : modelofThreeCompliantList) {
//
//                    if ("video".equals(ob1.getStatus())) {
//
//                        Log.e("videoGallery_1", picturesGallery.getStatus());
//                        int index = modelofThreeCompliantList.indexOf(ob1);
//                        modelofThreeCompliantList.set(index, picturesGallery);
//                        fillthree();
//                        Log.e("from_video", ob1.getStatus());
////                         modelofThreeCompliantList.remove(picturesGallery);
////                         modelofThreeCompliantList.add(picturesGallery);
//                    } else {
//                        Log.e("videoGallery_2", picturesGallery.getStatus());
//                        picturesGallery.setFileName(imgDecodableString);
//                        modelofThreeCompliantList.add(picturesGallery);
//                        fillthree();
//                    }
//                }


                cursor.close();


                File file = new File(imgDecodableString);

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

//                    ThreeCompliant camera = new ThreeCompliant();
//                    videoGallery.setStatus("camera");
//                    videoGallery.setFileName(selectedVideoPath);
                    NOTPICTURESANDVIDEO = 2;

//                    if (modelofThreeCompliantList.size() == 0) {
//                        ThreeCompliant camera = new ThreeCompliant();
//                        camera.setStatus("Video");
//                        camera.setFileName(selectedVideoPath);
//                        modelofThreeCompliantList.add(camera);
//                        complaintAdapter.notifyDataSetChanged();
//                    }
//
//                    for (int i = 0; i < modelofThreeCompliantList.size(); i++) {
//
//                        if (modelofThreeCompliantList.get(i).getStatus().equals("Video")) {
//
//                            modelofThreeCompliantList.get(i).setFileName(selectedVideoPath);
//                            complaintAdapter.notifyDataSetChanged();
//
//                        } else {
//
//                            ThreeCompliant camera = new ThreeCompliant();
//                            camera.setStatus("Video");
//                            camera.setFileName(selectedVideoPath);
//                            modelofThreeCompliantList.add(camera);
//                            complaintAdapter.notifyDataSetChanged();
//
//                        }
//
//                    }

                    for (int i = 0; i < modelofThreeCompliantList.size(); i++) {

                        if (modelofThreeCompliantList.get(i).getStatus().equals("Video")) {
                            modelofThreeCompliantList.remove(i);

                        }
                    }

                    ThreeCompliant camera = new ThreeCompliant();
                    camera.setStatus("Video");
                    camera.setFileName(selectedVideoPath);
                    modelofThreeCompliantList.add(camera);
                    complaintAdapter.notifyDataSetChanged();

//                    Log.e("videoGallery_0", videoGallery.getStatus());
//                    for (ThreeCompliant ob1 : modelofThreeCompliantList) {
//
//                        Log.e("videoGallery_1", videoGallery.getStatus());
//                        if ("videoGallery".equals(ob1.getStatus())) {
//
//                            int index = modelofThreeCompliantList.indexOf(ob1);
//                            modelofThreeCompliantList.set(index, videoGallery);
//                            fillthree();
//
//                            Log.e("videoGallery_2", videoGallery.getStatus());
////                         modelofThreeCompliantList.remove(picturesGallery);
////                         modelofThreeCompliantList.add(picturesGallery);
//                        } else {
//
//                            Log.e("videoGallery_3", videoGallery.getStatus());
//                            videoGallery.setFileName(imgDecodableString);
//                            modelofThreeCompliantList.add(videoGallery);
//                            fillthree();
//                        }
//                    }


//                    if(modelofThreeCompliantList.equals(videoGallery)){
//
//
//                        modelofThreeCompliantList.remove(videoGallery);
//                        modelofThreeCompliantList.add(videoGallery);
//                        fillthree();
//
//                    }
//                    else{
//
//                        videoGallery.setFileName(selectedVideoPath);
//                        modelofThreeCompliantList.add(videoGallery);
//                        fillthree();
//                    }


                    imgDecodableString = "";


                    Log.d("path", selectedVideoPath);

                }
            }
        }
    }

    private void complaintRequest(String Fname, String LName, String Email, String Message,
                                  String Type, String VehicleCode, String VehiclePelack, String Mobile, List<ThreeCompliant> listOfCompliants) {
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


        // this section is comment by Habib Allah

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

        Log.e("sgdsd44", VoiceRecoeder.AudioSavePathInDevice + " |" + listOfCompliants);
        if (!listOfCompliants.isEmpty()) {
            Log.e("filesfiles", listOfCompliants + " |");
            for (int i = 0; i < listOfCompliants.size(); i++) {

                String itemOfList = listOfCompliants.get(i).getFileName();
                body.addFilePart("files[]", new File(itemOfList));
                Log.e("first" + i, itemOfList);


            }
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
                            AlertDialog alertDialog = new AlertDialog.Builder(Complaint.this)
                                    //set icon
                                    //set title
                                    .setTitle("کد پیگیری : ")
                                    //set message
                                    .setMessage(object.getString("code"))
                                    //set positive button
                                    .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //set what would happen when positive button is clicked

                                        }
                                    })
                                    //set negative button
                                    .show();

                            alertDialog.show();
                            // The below Toast shows some characters when uploading finishes successfully
                            //     Toast.makeText(getBaseContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                            Loading.setVisibility(View.GONE);
                            Enabled();
                            Clear();
                            Toast.makeText(getApplicationContext(), "آپلود با موفقعیت انجام شد.", Toast.LENGTH_LONG).show();
                            modelofThreeCompliantList.clear();
                            complaintAdapter.notifyDataSetChanged();

//                            Dialog dialog = new Dialog(getApplicationContext());
//                            dialog.setTitle("کد پیگیری : ");
//                            dialog.setTitle(object.getString("code"));
//                            dialog.setCancelable(false);
//                            dialog.setCancelMessage("تایید");


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
        selectedVideoPath = "";
        VoiceRecoeder.AudioSavePathInDevice = "";


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

        Intent recordVoice = new Intent(getApplicationContext(), VoiceRecoeder.class);
        startActivity(recordVoice);
        NOTPICTURESANDVIDEO = 3;

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


    private void getComplaintArray() {

//        SharedPreferences sh = getSharedPreferences("complaint", MODE_PRIVATE);
//        String Jso = sh.getString("complaintArray", "-1");
//        Log.i("jdshfuahf", "getNews: "+Jso);

        Page137_Scrool.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);


        StringRequest getPhoneRequest = new StringRequest(Request.Method.GET, Globals.APIURL + "/complaintType",
                response -> {

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.getString("status").equals("true")) {
//                            Log.v("news", object.getString("news").toString());
//                            Log.e("userData", object.getString("userdata"));

                            Page137_Scrool.setVisibility(View.VISIBLE);
                            progressbar.setVisibility(View.GONE);

                            JSONArray jsonArray;
                             jsonArray = object.getJSONArray("complainttype");



                            if (!getCategory(jsonArray).isEmpty()) {

                                for (int i = 0; i < getCategory(jsonArray).size(); i++) {
                                    adapter.add(getCategory(jsonArray).get(i).getText());
                                }
                            } else {

                                LetterRateModel model = new LetterRateModel();
                                model.setImage("عدم دریافت اطلاعات از سرور ");
                                model.setText(" عدم دریافت اطلاعات از سرور لطفا به اینترنت متصل شوید");
                                adapter.add(model.getText());
                            }


                            adapter.add("موضوع خود را انتخاب کنید");
                            Category.setAdapter(adapter);
                            Category.setSelection(adapter.getCount());
                            Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                    if (position > (getCategory(jsonArray).size() - 1)) {


                                    } else if (getCategory(jsonArray).isEmpty()) {

                                        Toast.makeText(getApplicationContext(), "عدم دریافت اطلاعات از سرور", Toast.LENGTH_LONG).show();

                                    } else {
                                        type = String.valueOf(getCategory(jsonArray).get(position).getImage());
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

//                            for (int i = 0; i < jsonArray.length(); i++) {
//
//                                jsonObject = new JSONObject(jsonArray.getString(i));
//                            }




//                            SharedPreferences shared = getSharedPreferences("complaint", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = shared.edit();
//                            editor.putString("complaintArray", object.toString());
//                            editor.apply();
                            Log.i("jdshfuahf", "getNews: " + object.toString());




                        } else if (object.getString("status").equals("false")) {
                            Page137_Scrool.setVisibility(View.VISIBLE);
                            progressbar.setVisibility(View.GONE);


                        }
                    } catch (Exception e) {
                        Page137_Scrool.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);


                        Log.e("ListLeavesError1", e.toString() + " |");

                    }
                },
                error -> {
                    Page137_Scrool.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);


                    Log.e("ListLeavesError2", error + " |");

                });

        getPhoneRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(getPhoneRequest);

        //  Log.e("from_Complaint",jsonObject);


    }

    private String checkShared() {

        SharedPreferences h = getSharedPreferences("voice", MODE_PRIVATE);
        String getVoice = h.getString("voice_", "-1");
        Log.e("Audio_file", getVoice);
        //String address =  getIntent().getExtras().getString("Audio_source_address");

        return getVoice;

    }

    ;


    class ThreeRecyclerView extends RecyclerView.Adapter<ThreeRecyclerView.CustomizedView> {

        List<ThreeCompliant> lisOfUploadFiles;

        public ThreeRecyclerView(List<ThreeCompliant> lisOf) {
            this.lisOfUploadFiles = lisOf;
        }

        @NonNull
        @Override
        public ThreeRecyclerView.CustomizedView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ThreeRecyclerView.CustomizedView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.for_three_recycler,
                    null
            ));
        }

        @Override
        public void onBindViewHolder(@NonNull CustomizedView customizedView, int i) {

            switch (lisOfUploadFiles.get(i).getStatus()) {
                case "Video":
                    customizedView.filename.setText("ویدیو");

                    break;
                case "Audio":
                    customizedView.filename.setText("صدا");

                    break;
                case "camera":
                    customizedView.filename.setText("تصویر");
                    break;
            }
            customizedView.deleteFromList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    modelofThreeCompliantList.remove(i);
                    complaintAdapter.notifyDataSetChanged();

                }
            });

        }


        @Override
        public int getItemCount() {
            return lisOfUploadFiles.size();
        }


        public class CustomizedView extends RecyclerView.ViewHolder {

            TextView filename;
            ImageView deleteFromList;


            public CustomizedView(@NonNull View itemView) {
                super(itemView);
                filename = itemView.findViewById(R.id.textView_for_three_recycle);
                deleteFromList = itemView.findViewById(R.id.delete_for_three_recycle);

            }
        }
    }

    private void fillthree() {


//        ThreeCompliant obj = new ThreeCompliant();
//        obj.setFileName("pic0");
//        three.add(obj);
//
//        obj.setFileName("pic1");
//        three.add(obj);
//
//        obj.setFileName("pic3");
//        three.add(obj);
//
//        obj.setFileName("pic4");
//        three.add(obj);
//
//        obj.setFileName("pic5");
//        three.add(obj);
//
//        obj.setFileName("pic15");
//        three.add(obj);
//        obj.setFileName("pic5");
//        three.add(obj);
//
//        obj.setFileName("pic5");
//        three.add(obj);
//
//        obj.setFileName("pic5");
//        three.add(obj);
//        obj.setFileName("pic5");
//        three.add(obj);
//        obj.setFileName("pic5");
//        three.add(obj);
//        obj.setFileName("pic5");
//        three.add(obj);


        threeRecyclerView = findViewById(R.id.threeRecycler);
        threeRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayout.VERTICAL, false));
        threeRecyclerView.setAdapter(complaintAdapter);

     /*   LinearSnapHelper snapHelper  = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(threeRecyclerView);



        //This is used to center first and last item on screen
        threeRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildViewHolder(view).getAdapterPosition();

                if (position == 0 || position == state.getItemCount() - 2) {

                    int elementWidth = (int)getResources().getDimension(R.dimen.widthof);
                    int elementMargin = (int)getResources().getDimension(R.dimen.marginof);

                    int padding = Resources.getSystem().getDisplayMetrics().widthPixels / 2 - elementWidth - elementMargin/2;

                    if (position == 0) {
                        outRect.left = padding;
                    }
                }
            }
        });
        */

//        if (!three.isEmpty())
//            Log.e("are_You_really_null", "null");


    }


}
