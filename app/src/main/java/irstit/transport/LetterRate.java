package irstit.transport;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.DataModel.LetterRateModel;
import irstit.transport.Views.CustomTextView;

public class LetterRate extends AppCompatActivity {

    private List<LetterRateModel> data = new ArrayList<>();
    private String type;
    private Spinner Category;
    private spinnerAdapter adapter;
    private ImageView Image;
    private CustomTextView Text;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_rate);

        Category = findViewById(R.id.LetterRate_Category);
        Image = findViewById(R.id.LetterRate_Image);
        Text = findViewById(R.id.LetterRate_Text);
        ImageView back = findViewById(R.id.LetterRate_Back);
        adapter = new spinnerAdapter(this, R.layout.layout_custom_spinner);
        progressBar = findViewById(R.id.pragressbar);


        back.setOnClickListener(v -> {
            finish();

        });


        letterRateRequest();

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



    private void letterRateRequest() {
        StringRequest getPhoneRequest = new StringRequest(Request.Method.GET, Globals.APIURL + "/price",
                response -> {
                    Log.e("ListLeavesResponse", response + " |");

                    try {


                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("true")) {


                            JSONArray array = object.getJSONArray("prices");

                            for (int i = 0 ; i < array.length()  ; i++) {
                                LetterRateModel model = new LetterRateModel();
                                model.setText(array.getJSONObject(i).getString("title"));
                                model.setImage(array.getJSONObject(i).getString("c_fileaddress"));
                                data.add(model);
                            }

                            progressBar.setVisibility(View.VISIBLE);

                            Text.setText("نوع دسته بندی : " + data.get(0).getText());
                            Picasso.with(getBaseContext())
                                    .load(data.get(0).getImage())
                                    .into(Image, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            progressBar.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError() {

                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(),"خطا در ارتباط با سرور",Toast.LENGTH_LONG).show();
                                        }
                                    });




                            progressBar.setVisibility(View.GONE);

                            for (int i = 0 ; i < data.size() ; i++) {
                                adapter.add(data.get(i).getText());
                            }
                            adapter.add("دسته بندی نرخ نامه خود را انتخاب کنید");
                            Category.setAdapter(adapter);
                            Category.setSelection(adapter.getCount());
                            Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                    progressBar.setVisibility(View.VISIBLE);

                                    if (position > (data.size() - 1)) {

                                    }else {
                                        type = String.valueOf(data.get(position).getImage());
                                        Picasso.with(getBaseContext())
                                                .load(type)
                                                .into(Image, new Callback() {
                                                    @Override
                                                    public void onSuccess() {
                                                        progressBar.setVisibility(View.GONE);
                                                    }

                                                    @Override
                                                    public void onError() {
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(getApplicationContext(),"خطا در ارتباط با سرور",Toast.LENGTH_LONG).show();

                                                    }
                                                });

                                        Text.setText("نوع دسته بندی : " + data.get(position).getText());
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });



                        } else if (object.getString("status").equals("false")) {
//                            Loading.setVisibility(View.GONE);
//                            navigation_Recycler.setVisibility(View.VISIBLE);
//                            empty.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        Log.e("ListLeavesError1", e.toString() + " |");
//                        Loading.setVisibility(View.GONE);
//                        navigation_Recycler.setVisibility(View.VISIBLE);
//                        empty.setVisibility(View.GONE);
                    }
                },
                error -> {
                    Log.e("ListLeavesError2", error.toString() + " |");
//                    Loading.setVisibility(View.GONE);
//                    navigation_Recycler.setVisibility(View.VISIBLE);
//                    empty.setVisibility(View.GONE);
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
//                map.put("phone", DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone());
                return map;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("token", "df837016d0fc7670f221197cd92439b5");
                return map;
            }
        };

        getPhoneRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(getPhoneRequest);

    }


}
