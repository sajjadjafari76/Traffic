package irstit.transport.Drivers;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import irstit.transport.DataModel.ReqVacationModel;
import irstit.transport.R;

public class DriversMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TabLayout tab_layout;
    private BottomNavigationView bottomNavigationMenuView;
    public static List<ReqVacationModel> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_main);
        bottomNavigationMenuView = findViewById(R.id.DriversMainActivity_BottomNavigation);
        bottomNavigationMenuView.setOnNavigationItemSelectedListener(this);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        fragmentTransaction.replace(R.id.DriversMainActivity_Container, new Profile());
        fragmentTransaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        switch (menuItem.getItemId()) {
            case R.id.DriversMainMenu_Profile:
                fragmentTransaction.replace(R.id.DriversMainActivity_Container, new Profile());
                fragmentTransaction.commit();
                return true;
            case R.id.DriversMainMenu_ReqVacation:
                fragmentTransaction.replace(R.id.DriversMainActivity_Container, new RequestVacation());
                fragmentTransaction.commit();
                return true;
            case R.id.DriversMainMenu_VacationSearch:
                fragmentTransaction.replace(R.id.DriversMainActivity_Container, new VacationSearch());
                fragmentTransaction.commit();
                return true;
        }
        return false;
    }
}
