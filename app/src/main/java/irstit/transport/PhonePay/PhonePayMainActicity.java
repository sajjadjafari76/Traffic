package irstit.transport.PhonePay;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import irstit.transport.R;

public class PhonePayMainActicity extends AppCompatActivity {

    private BottomNavigationView pyn;
    private FragmentTransaction th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_pay_main_acticity);


        pyn = findViewById(R.id.ponebottonnav);

        pyn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                th = getSupportFragmentManager().beginTransaction();

                switch (menuItem.getItemId()) {

                    case R.id.phone_profile:

                        ProfileFragment profile = new ProfileFragment();
                        th.replace(R.id.FrameLayoutForPhonePayMainActivity, profile);
                        th.commit();
                        return true;

                    case R.id.comment:
                        Increasing_balance increase = new Increasing_balance();
                        th.replace(R.id.FrameLayoutForPhonePayMainActivity,increase);
                        th.commit();
                      //  Toast.makeText(getBaseContext(), "این قسمت در حال ساخت است", Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.wallet:
                        JavaWallet wall = new JavaWallet();
                        th.replace(R.id.FrameLayoutForPhonePayMainActivity, wall);
                        th.commit();
                        return true;


                    case R.id.square:
                        PhonePayMainFragment ma = new PhonePayMainFragment();
                        th.replace(R.id.FrameLayoutForPhonePayMainActivity, ma);
                        th.commit();
                        return true;
                }

                return false;
            }
        });


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("main");
        JavaWallet walle = new JavaWallet();
        fragmentTransaction.replace(R.id.FrameLayoutForPhonePayMainActivity, walle);
        fragmentTransaction.commit();

    }
}
