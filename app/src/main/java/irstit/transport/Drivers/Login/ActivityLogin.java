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

        // Going to getPhone fragment to get phone number
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Toolbar_Frame, new GetPhone());
        transaction.commit();

    }
}
