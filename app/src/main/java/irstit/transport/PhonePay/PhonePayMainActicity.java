package irstit.transport.PhonePay;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import irstit.transport.R;

public class PhonePayMainActicity extends AppCompatActivity {

    private BottomNavigationView pyn;
    private FragmentManager th;


    ProfileFragment profile = new ProfileFragment();
    Increasing_balance increase = new Increasing_balance();
    JavaWallet wall = new JavaWallet();
    PhonePayMainFragment ma = new PhonePayMainFragment();
    Fragment fragmentActive = wall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_pay_main_acticity);

        th = getSupportFragmentManager();
        pyn = findViewById(R.id.ponebottonnav);
        pyn.setSelectedItemId(R.id.wallet);

        th.beginTransaction().add(R.id.FrameLayoutForPhonePayMainActivity, profile).hide(profile).commit();
        th.beginTransaction().add(R.id.FrameLayoutForPhonePayMainActivity, increase).hide(increase).commit();
        th.beginTransaction().add(R.id.FrameLayoutForPhonePayMainActivity, ma).hide(ma).commit();
        th.beginTransaction().add(R.id.FrameLayoutForPhonePayMainActivity, wall).commit();


        pyn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()) {

                    case R.id.phone_profile:

                        th.beginTransaction().hide(fragmentActive).show(profile).commit();
                        fragmentActive = profile;
                        return true;

                    case R.id.comment:

                        th.beginTransaction().hide(fragmentActive).show(increase).commit();
                        fragmentActive = increase;

                        //  Toast.makeText(getBaseContext(), "این قسمت در حال ساخت است", Toast.LENGTH_LONG).show();
                        return true;

                    case R.id.wallet:
                        th.beginTransaction().hide(fragmentActive).show(wall).commit();
                        fragmentActive = wall;

                        return true;


                    case R.id.square:
                        th.beginTransaction().hide(fragmentActive).show(ma).commit();
                        fragmentActive = ma;
                        return true;
                }
                return false;
            }
        });


//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("main");
//        JavaWallet walle = new JavaWallet();
//        fragmentTransaction.replace(R.id.FrameLayoutForPhonePayMainActivity, walle);
//        fragmentTransaction.commit();

    }
}
