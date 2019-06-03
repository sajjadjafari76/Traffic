package irstit.transport.Citizens.Account;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import irstit.transport.DataBase.DBManager;
import irstit.transport.Drivers.Login.GetPhone;
import irstit.transport.R;

public class CitizensAccountActivity extends AppCompatActivity {

    @Override
    public void onStart() {
        super.onStart();
        if (DBManager.getInstance(this).getCitizenInfo().getUserPhone() != null){
            if (!DBManager.getInstance(this).getCitizenInfo().getUserPhone().isEmpty()){
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizens_account);


//        Bundle bundle = new Bundle();
//        if (getIntent().getExtras() != null
//                && getIntent().getExtras().getString("state").equals("ChangePass")) {
//            bundle.putString("state","ChangePass");
//        }else {
//            bundle.putString("state","null");
//        }

        // Going to getPhone fragment to get phone number

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CitizenGetPhone getPhone = new CitizenGetPhone();
//        getPhone.setArguments(bundle);
        transaction.replace(R.id.CitizenActivity_Frame, getPhone);
        transaction.commit();


        TextView Login = findViewById(R.id.CitizenAccount_Login);
        Login.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), CitizenLogin.class));
        });

    }
}
