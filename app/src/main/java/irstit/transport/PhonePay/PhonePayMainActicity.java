package irstit.transport.PhonePay;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import irstit.transport.R;

public class PhonePayMainActicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_pay_main_acticity);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        fragmentTransaction.replace(R.id.FrameLayoutForPhonePayMainActivity,profileFragment);
        fragmentTransaction.commit();

    }
}
