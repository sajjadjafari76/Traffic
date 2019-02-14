package irstit.transport.Citizens;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.anychart.core.stock.indicators.EMA;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.DataModel.LetterRateModel;
import irstit.transport.Globals;
import irstit.transport.LetterRate;
import irstit.transport.R;

public class Complaint extends AppCompatActivity {

    private EditText Text, Name, Family, Email, Phone, CarCode, CarPelake;
    private Spinner Category;
    private spinnerAdapter adapter;
    private Button Btn;
    private String type = "";
    private RelativeLayout Loading;

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


        for (int i = 0 ; i < getCategory().size() ; i++) {
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


        Btn.setOnClickListener(v -> {

            if (type.isEmpty() || Text.getText().toString().isEmpty()) {
                Toast.makeText(this, "فیلد های شرح پیام نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
            } else if(Name.getText().toString().isEmpty() || Family.getText().toString().isEmpty() || Email.getText().toString().isEmpty()) {
                Toast.makeText(this, "فیلد های اطلاعات نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();

            }else {

                complaintRequest(Name.getText().toString(), Family.getText().toString(),
                        Email.getText().toString(), Text.getText().toString(), type,
                        CarCode.getText().toString(), CarPelake.getText().toString(), Phone.getText().toString());

                Loading.setVisibility(View.VISIBLE);
                Disabled();
            }
        });


    }


    List<LetterRateModel> getCategory() {
        List<LetterRateModel> data = new ArrayList<>();
        try {
            if (getIntent().getExtras().getString("data") != null && !getIntent().getExtras().getString("data").isEmpty()) {
                JSONObject object = new JSONObject(getIntent().getExtras().getString("data"));

                if (object.getString("status").equals("true")) {

                    JSONArray array = new JSONArray(object.getString("complainttype"));


                    for (int i = 0 ; i < array.length() ; i++) {
                        LetterRateModel model = new LetterRateModel();
                        model.setImage(array.getJSONObject(i).getString("ct_code"));
                        model.setText(array.getJSONObject(i).getString("ct_name"));
                        data.add(model);
                    }
                }
            } else {

            }
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


    private void complaintRequest(String Fname, String LName, String Email, String Message,
                                  String Type, String VehicleCode, String VehiclePelack, String Mobile) {
        StringRequest complaintRequest = new StringRequest(Request.Method.POST, Globals.APIURL + "/Complaint",
                response -> {
                    Log.e("ListLeavesResponse", response + " |");

                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("true")) {

                            Toast.makeText(this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            Loading.setVisibility(View.GONE);
                            Enabled();
                            Clear();

                        } else if (object.getString("status").equals("false")) {
                            Loading.setVisibility(View.VISIBLE);
                            Enabled();
                        }
                    } catch (Exception e) {
                        Log.e("ListLeavesError1", e.toString() + " |");
                        Loading.setVisibility(View.GONE);
                        Enabled();
                    }
                },
                error -> {
                    Log.e("ListLeavesError2", error.toString() + " |");
                    Loading.setVisibility(View.GONE);
                    Enabled();
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("fname", Fname);
                map.put("lname", LName);
                map.put("email", Email);
                map.put("mobile", Mobile);
                map.put("message", Message);
                map.put("typecomplaint", Type);
                map.put("vehiclecode", VehicleCode);
                map.put("vehiclepluck", VehiclePelack);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("token", "df837016d0fc7670f221197cd92439b5");
                return map;
            }
        };

        complaintRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(complaintRequest);

    }

    void Clear() {
        Name.setText("");
        Family.setText("");
        Phone.setText("");
        Email.setText("");
        CarPelake.setText("");
        CarCode.setText("");
        Text.setText("");
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


}
