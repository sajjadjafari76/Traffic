package irstit.transport.Drivers.Login;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import irstit.transport.R;

public class ActivityLogin extends AppCompatActivity {

    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = new Bundle();


        if (getIntent().getExtras() != null
                && getIntent().getExtras().getString("state").equals("ChangePass")) {
            bundle.putString("state","ChangePass");
        }else {
            bundle.putString("state","null");
        }

        // Going to getPhone fragment to get phone number

        transaction = getSupportFragmentManager().beginTransaction();

        GetPhone getPhone = new GetPhone();
        getPhone.setArguments(bundle);

        transaction.replace(R.id.Toolbar_Frame, getPhone);
        transaction.commit();

    }
}
