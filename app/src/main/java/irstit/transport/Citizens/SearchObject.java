package irstit.transport.Citizens;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import irstit.transport.AppController.AppController;
import irstit.transport.DataModel.NavModel;
import irstit.transport.DataModel.NewsModel;
import irstit.transport.DataModel.SearchObjModel;
import irstit.transport.DataModel.SpinnerModel;
import irstit.transport.Globals;
import irstit.transport.MainActivity;
import irstit.transport.R;
import irstit.transport.Views.CFProvider;


public class SearchObject extends AppCompatActivity {

    private RecyclerView Recycler;
    private PersianDatePickerDialog picker;
    private EditText Start, End;
    private spinnerAdapter adapter;
    private Spinner Category;
    private ExpansionHeader Header;
    private ExpansionLayout Layout;
    private String StartDate, EndDate;
    private PersianCalendar StartDateCalender = new PersianCalendar(), EndDateCalender = new PersianCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_object);
        ExpansionLayout layout;


        ImageView Back = findViewById(R.id.RegisterObject_Back);
        Button Btn = findViewById(R.id.RegisterObject_Btn);
        Header = findViewById(R.id.SearchObject_Header);
        Layout = findViewById(R.id.SearchObject_ExpansionLayout);
        Recycler = findViewById(R.id.SearchObject_Recycler);
        Category = findViewById(R.id.RegisterObject_Category);
        Start = findViewById(R.id.SearchObject_Start);
        End = findViewById(R.id.SearchObject_End);
        Start.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                configureStartDate();
            }
        });
        End.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                configureEndDate();
            }
        });


        adapter = new spinnerAdapter(this, R.layout.layout_custom_spinner);

        for (int i = 0 ; i < getCategory().size() ; i++) {
            adapter.add(getCategory().get(i).getName());
        }
        adapter.add("یک موضوع را انتخاب کنید!");
        Category.setAdapter(adapter);
        Category.setSelection(adapter.getCount());
        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                if (position > (category.size() - 1)) {
//
//                }else {
////                    type = String.valueOf(getCategory().get(position).getId());
//                    Log.e("GetPhoneResponse", String.valueOf(getCategory().get(position).getId()) + " |");
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Back.setOnClickListener(view -> {
            finish();
        });

        Btn.setOnClickListener(view -> {

            if (Start.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "تاریخ آغاز نمی تواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else if (End.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "تاریخ پایان نمی تواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else if (EndDateCalender.before(StartDateCalender)) {
                Toast.makeText(getBaseContext(), "بازه زمانی تعیین شده اشتباه است", Toast.LENGTH_SHORT).show();
            } else {
                criticalRequest(StartDate, "", EndDate);

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
                    model2.setName("زیورالات");
                    data.add(model2);
                    break;
                case 2:
                    SpinnerModel model6 = new SpinnerModel();
                    model6.setId(6164);
                    model6.setName("وسایل شخصی");
                    data.add(model6);
                    break;
                case 3:
                    SpinnerModel model3 = new SpinnerModel();
                    model3.setId(6214);
                    model3.setName("وسایل الکترونیکی");
                    data.add(model3);
                    break;
                case 4:
                    SpinnerModel model4 = new SpinnerModel();
                    model4.setId(7214);
                    model4.setName("توشه");
                    data.add(model4);
                    break;
                case 5:
                    SpinnerModel model5 = new SpinnerModel();
                    model5.setId(8214);
                    model5.setName("سایر");
                    data.add(model5);
                    break;
            }
        }
        return data;
    }

    private List<NavModel> Data() {
        List<NavModel> data = new ArrayList<>();
        for (int i = 0 ; i <= 6 ; i++) {

            if (i == 0) {
                NavModel model = new NavModel();
//                model.setName("نظریه جدید در سازمان");
//                model.setImage("http://traffictakestan.ir/images/photo_2019-01-27_05-26-38.jpg");
                data.add(model);
            } else if (i == 1) {
                NavModel model = new NavModel();
//                model.setName("نرخ های جدید سازمان");
//                model.setImage("https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg");
                data.add(model);
            } else if (i == 2) {
                NavModel model = new NavModel();
//                model.setName("پیشنهاد های مردمی");
//                model.setImage("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png");
                data.add(model);
            } else if (i == 3) {
                NavModel model = new NavModel();
//                model.setName("پیشنهاد های مردمی");
//                model.setImage("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png");
                data.add(model);
            } else if (i == 4) {
                NavModel model = new NavModel();
//                model.setName("پیشنهاد های مردمی");
//                model.setImage("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png");
                data.add(model);
            }

        }

        return data;
    }

    private class MyNavigationAdapter extends RecyclerView.Adapter<MyNavigationAdapter.MyCustomView> {

        List<SearchObjModel> data;

        public MyNavigationAdapter(List<SearchObjModel> data) {
            this.data = data;
        }

        @Override
        public MyNavigationAdapter.MyCustomView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyNavigationAdapter.MyCustomView(LayoutInflater
                    .from(getBaseContext()).inflate(R.layout.layout_search, null));
        }

        @Override
        public void onBindViewHolder(MyNavigationAdapter.MyCustomView holder, final int position) {

            holder.Title.setText(data.get(position).getTitle());
            holder.Date.setText(data.get(position).getDate());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyCustomView extends RecyclerView.ViewHolder {

            private TextView Title, Date;

            public MyCustomView(View itemView) {
                super(itemView);
                Title = itemView.findViewById(R.id.Search_Title);
                Date = itemView.findViewById(R.id.Search_Date);

            }
        }
    }

    private void criticalRequest(String StartDate, String Type, String EndDate) {
        Log.e("GetPhoneResponse", "sgbjhsjZ" + " |");
        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST, Globals.APIURL + "/serachobjpost",
                response -> {
                    Log.e("GetPhoneResponse", response + " |");

                    try {
                        JSONObject object = new JSONObject(response);
//                        Critical_Loading.setVisibility(View.GONE);
                        if (object.getString("status").equals("true")) {
//                            enabledEditText();
//                            clear();

                            JSONArray array = object.getJSONArray("result");
                            List<SearchObjModel> obj = new ArrayList<>();

                            for (int i = 0 ; i < array.length() ; i++) {

                                SearchObjModel searchObject = new SearchObjModel();
                                searchObject.setTitle(array.getJSONObject(0).getString("rd_name"));
                                searchObject.setDate(array.getJSONObject(0).getString("rd_timeobject"));
                                obj.add(searchObject);
                            }

                            Layout.toggle(true);
                            Recycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
                            Recycler.setAdapter(new MyNavigationAdapter(obj));

                        } else if (object.getString("status").equals("true")) {
//                            enabledEditText();
                            Toast.makeText(getBaseContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("GetPhoneError", e.toString() + " |");
//                        enabledEditText();
                    }
                },
                error -> {
                    Log.e("GetPhoneError", error.toString() + " |");
//                    Critical_Loading.setVisibility(View.GONE);
//                    enabledEditText();
                }) {

            @Override
            protected Map<String, String> getParams() {
                Log.e("GetPhoneResponse", "dfgdgdfdgdgfdgd" + " |");
                Map<String, String> map = new HashMap<>();
                map.put("startdate", StartDate);
                map.put("enddate", EndDate);
                map.put("type", "6214");
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

    public void configureStartDate() {
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
                        StartDate = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();
                        Start.setText(StartDate);
                        StartDateCalender.setPersianDate(persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
                    }

                    @Override
                    public void onDismissed() {

                    }
                });
        picker.show();

    }

    public void configureEndDate() {
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
                        EndDate = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();
                        End.setText(EndDate);
                        EndDateCalender.setPersianDate(persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
                    }

                    @Override
                    public void onDismissed() {

                    }
                });
        picker.show();

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


}
