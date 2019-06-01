package irstit.transport.annoucment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.Citizens.SearchObject;
import irstit.transport.DataModel.SearchObjModel;
import irstit.transport.Globals;
import irstit.transport.R;

public class announcement extends AppCompatActivity {

    private RecyclerView re;
    private AnnouncementRecyclerAdoptor ad ;

    private List<AnnoucmentContorol> list = new ArrayList<>();

    private ProgressBar announcement_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        re = findViewById(R.id.announcement_recycler);
        announcement_progressbar = findViewById(R.id.announcement_progressbar);


        getDate();
//        ad = new AnnouncementRecyclerAdoptor(list,getApplicationContext());
        re.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        re.setAdapter(ad);
      //  announcement_progressbar.setVisibility(View.GONE);


    }


    private  void getDate(){

        announcement_progressbar.setVisibility(View.VISIBLE);
        Log.e("insdie:0","comes");
        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST, Globals.APIURL + "/notification",
                response -> {
                    Log.e("GetPhoneResponse", response + " |");

                    try {
                        JSONObject object = new JSONObject(response);
//                        Critical_Loading.setVisibility(View.GONE);
                        if (object.getString("status").equals("true")) {
//                            enabledEditText();
//                            clear();

                            JSONArray array = object.getJSONArray("result");



                            if (array.length() == 0) {



                            } else {

//                                Null.setVisibility(View.GONE);
                                re.setVisibility(View.VISIBLE);

                             //   List<AnnoucmentContorol> obj = new ArrayList<>();


                                for (int i = 0 ; i < array.length() ; i++) {
                                    AnnoucmentContorol annou = new AnnoucmentContorol();

                                    annou.setTitle(array.getJSONObject(i).getString("title"));
                                    annou.setDate(array.getJSONObject(i).getString("regtime"));
                                    annou.setLink(array.getJSONObject(i).getString("link"));
                                    annou.setContent(array.getJSONObject(i).getString("desc"));
                                    annou.setState(array.getJSONObject(i).getString("state"));
                                    annou.setImage(array.getJSONObject(i).getString("image"));
//                                    Picasso picasso = new Picasso(this,;
                                    list.add(annou);
                                    Log.e("announcement0",list.toString());
                                }


                                Log.e("announcement1",list.toString());

                                re.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
                                ad = new AnnouncementRecyclerAdoptor(list,announcement.this);
                                re.setAdapter(ad);
                                Log.e("announcement2",list.toString());
                                // re.setAdapter(new AnnouncementRecyclerAdoptor(list,getBaseContext()));
                                announcement_progressbar.setVisibility(View.GONE);
                            }
                        } else if (object.getString("status").equals("true")) {
//                            enabledEditText();
                            Toast.makeText(getBaseContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
//                        announcement_progressbar.setVisibility(View.GONE);


                        Log.e("GetPhoneError", e.toString() + " |");
//                        enabledEditText();
                    }
                },
                error -> {
                    Log.e("GetPhoneError", error.toString() + " |");
//                    Critical_Loading.setVisibility(View.GONE);
//                    enabledEditText();
                }) {

//            @Override
//            protected Map<String, String> getParams() {
//                Log.e("GetPhoneResponse", "dfgdgdfdgdgfdgd" + " |");
//                Map<String, String> map = new HashMap<>();
//                map.put("startdate", StartDate);
//                map.put("enddate", EndDate);
//                map.put("type", type);
//                return map;
//            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("token", "df837016d0fc7670f221197cd92439b5");
                return map;
            }
        };

//        announcement_progressbar.setVisibility(View.GONE);

        getPhoneRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(getPhoneRequest);

    }


}


