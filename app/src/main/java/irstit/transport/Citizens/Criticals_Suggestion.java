package irstit.transport.Citizens;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.DataModel.SpinnerModel;
import irstit.transport.Globals;
import irstit.transport.R;

public class Criticals_Suggestion extends AppCompatActivity {

    private spinnerAdapter adapter ;
    private List<SpinnerModel> category = new ArrayList<>();
    private String type = "";
    private RelativeLayout Critical_Loading;
    private EditText Name, Phone, Description;
    private Spinner Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criticals);

        Button Critical_Btn = findViewById(R.id.Critical_Btn);
        ImageView Back = findViewById(R.id.Critical_Back);
        Back.setOnClickListener(view -> finish());
        Critical_Loading = findViewById(R.id.Critical_Loading);
        Name = findViewById(R.id.Critical_Name);
        Title = findViewById(R.id.Critical_Title);
        Phone = findViewById(R.id.Critical_Phone);
        Description = findViewById(R.id.Critical_Description);
        Critical_Btn.setOnClickListener(view -> {

            if (Name.getText().toString().isEmpty()) {
                Toast.makeText(this, "نام نمی تواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else if (Description.getText().toString().isEmpty()) {
                Toast.makeText(this, "متن شکایت نمی تواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else {
                criticalRequest(Name.getText().toString(),
                        type,
                        Phone.getText().toString(),
                        Description.getText().toString());


                disabledEditText();
                TransitionManager.beginDelayedTransition(Critical_Loading);
                Critical_Loading.setVisibility(View.VISIBLE);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        adapter = new spinnerAdapter(this, R.layout.layout_custom_spinner);

        for (int i = 0 ; i < getCategory().size() ; i++) {
            adapter.add(getCategory().get(i).getName());
        }
        adapter.add("یک موضوع را انتخاب کنید!");
        Title.setAdapter(adapter);
        Title.setSelection(adapter.getCount());
        Title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > (category.size() - 1)) {

                }else {
                    type = String.valueOf(getCategory().get(position).getId());
                    Log.e("GetPhoneResponse", String.valueOf(getCategory().get(position).getId()) + " |");
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
                    model.setId(1);
                    model.setName("پیشنهاد");
                    data.add(model);
                    break;
                case 1:
                    SpinnerModel model2 = new SpinnerModel();
                    model2.setId(0);
                    model2.setName("انتقاد");
                    data.add(model2);
                    break;
            }
        }
        return data;
    }

    private void criticalRequest(String Name, String Type, String Phone, String Description) {

        StringRequest getPhoneRequest = new StringRequest(Request.Method.POST, Globals.APIURL + "/offers",
                response -> {
                    Log.e("GetPhoneResponse", response + " |");

                    try {
                        JSONObject object = new JSONObject(response);
                        Critical_Loading.setVisibility(View.GONE);
                        if (object.getString("status").equals("true")) {
                            enabledEditText();
                            clear();
                            Toast.makeText(getBaseContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

                        } else if (object.getString("status").equals("true")) {
                            enabledEditText();
                            Toast.makeText(getBaseContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("GetPhoneError", e.toString() + " |");
                        enabledEditText();
                    }
                },
                error -> {
                    Log.e("GetPhoneError", error.toString() + " |");
                    Critical_Loading.setVisibility(View.GONE);
                    enabledEditText();
                }) {

            @Override
            protected Map<String, String> getParams() {

                Log.e("parameter", Name + " | " + Type + " | " + Phone + " | " + Description);
                Map<String, String> map = new HashMap<>();
                map.put("fname", Name);
                map.put("type", Type);
                map.put("mobile", Phone);
                map.put("comment", Description);
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

    private void disabledEditText() {
        Name.setEnabled(false);
        Phone.setEnabled(false);
        Description.setEnabled(false);
        Title.setEnabled(false);
    }

    private void enabledEditText() {
        Name.setEnabled(true);
        Phone.setEnabled(true);
        Description.setEnabled(true);
        Title.setEnabled(true);
    }

    private void clear() {
        Name.setText("");
        Phone.setText("");
        Description.setText("");
        Title.setSelection(0);
    }

}
