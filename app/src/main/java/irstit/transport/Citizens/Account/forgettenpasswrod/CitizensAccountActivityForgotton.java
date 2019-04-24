package irstit.transport.Citizens.Account.forgettenpasswrod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import irstit.transport.Citizens.Account.CitizenGetPhone;
import irstit.transport.Citizens.Account.CitizenLogin;
import irstit.transport.R;

public class CitizensAccountActivityForgotton extends AppCompatActivity {

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
        CitizenGetPhoneForgotten getPhone = new CitizenGetPhoneForgotten();
        //getPhone.setArguments(bundle);
        transaction.replace(R.id.CitizenActivity_Frame, getPhone);
        transaction.commit();



        TextView Login = findViewById(R.id.CitizenAccount_Login);
        Login.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), CitizenLogin.class));
        });

    }
}
