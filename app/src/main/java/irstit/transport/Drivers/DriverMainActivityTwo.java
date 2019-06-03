package irstit.transport.Drivers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import irstit.transport.AboutUs;
import irstit.transport.Citizens.complaint.Complaint;
import irstit.transport.Citizens.RegisterObject;
import irstit.transport.Citizens.SearchObject;
import irstit.transport.ConnectToUs;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.NavModel;
import irstit.transport.MainPage;
import irstit.transport.R;
import irstit.transport.ViewPager.MainPager;
import irstit.transport.Views.CFProvider;
import irstit.transport.Views.CustomButton;

public class DriverMainActivityTwo extends AppCompatActivity {

    private DrawerLayout drawer;
    private AlertDialog alertDialog;
    private  TextView driverName;
    private  RelativeLayout rel;
    NavigationView navigationView;
    TextView navUsername;
    public  static  String sharingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main_two);


        LinearLayout regVacation = findViewById(R.id.DriverMainTwo_RegVacation);
        LinearLayout regObj = findViewById(R.id.DriverMainTwo_RegObj);
        LinearLayout vacationHistory = findViewById(R.id.DriverMainTwo_VacationHistory);
        LinearLayout followUp = findViewById(R.id.DriverMainTwo_VacationFollowUp);
        TextView Name = findViewById(R.id.DriverMainTwo_Name);
        rel = findViewById(R.id.MainActivity_Login_Driver_profile_name);

        if(DBManager.getInstance(getBaseContext()).getDriverInfo().getName()!=null){

            rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getBaseContext(),DriversMainActivity.class);
                    intent.putExtra("Driver_profile","true");
                    startActivity(intent);
                }
            });
        }


        Name.setText(DBManager.getInstance(getBaseContext()).getDriverInfo().getName());

        regVacation.setOnClickListener(v -> {

            Intent intent = new Intent(getBaseContext(), DriversMainActivity.class);
            intent.putExtra("RequestVacation","true");
            startActivity(intent);

        });
        regObj.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), RegisterObject.class));
        });
        vacationHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), DriversMainActivity.class);
            intent.putExtra("VacationSearch","true");
            startActivity(intent);

        });
        followUp.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), DriversMainActivity.class);
            intent.putExtra("VacationSearch","true");
            startActivity(intent);
        });


        LinearLayout site = findViewById(R.id.DriverMainTwo_Site);
        LinearLayout dial = findViewById(R.id.DriverMainTwo_Dial1);
        dial.setOnClickListener(v -> {

            Intent dial1 = new Intent();
            dial1.setAction("android.intent.action.DIAL");
            dial1.setData(Uri.parse("tel:" + "02835237500"));
            startActivity(dial1);

        });
        site.setOnClickListener(v -> {

            Uri uri = Uri.parse("http://www.traffictakestan.ir"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });

        // change  default profile's name  to user's log-in name
          changeDefaultuser_Name();



        drawer = findViewById(R.id.DriverMainTwo_Drawer);
        ImageView iconDrawer = findViewById(R.id.DriverMainTwo_NavigatorIcon);
        iconDrawer.setOnClickListener(v-> drawer.openDrawer(Gravity.END));


        RecyclerView navigation_Recycler = findViewById(R.id.DriverMainTwo_RecyClerView);
        navigation_Recycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        navigation_Recycler.setAdapter(new MyNavigationAdapter(navigationData()));

//         this textView is getting it's value from inner Person's name

        NavigationView navigationView = findViewById(R.id.DriverMainTwo_NavigationView);
        View headerView = navigationView.getHeaderView(0);
        RelativeLayout relativeLayout = headerView.findViewById(R.id.Navigation_Login);

        TextView navUsername = headerView.findViewById(R.id.Navigation_Enter);
        navUsername.setText(DBManager.getInstance(getBaseContext()).getDriverInfo().getName());
        if(DBManager.getInstance(getBaseContext()).getDriverInfo().getName()!=null){

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(),DriversMainActivity.class);
                    intent.putExtra("Driver_profile","true");
                    startActivity(intent);
                }
            });
        }



    }


    private void changeDefaultuser_Name() {

        try {

            SharedPreferences sh = getSharedPreferences("complaint", MODE_PRIVATE);
            String objectForDriverName = sh.getString("complaintArray", "-1");
            JSONObject jsonObject = new JSONObject(objectForDriverName);
            JSONObject name = new JSONObject(jsonObject.getString("userdata"));
            Log.e("nameFromhere", name.toString());


            if(name.getString("d_tel")!=null) {

                navUsername.setText(MainPager.incomingName);
//                sharingName = DBManager.getInstance(getBaseContext()).getDriverInfo().getName();

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void deleteSavedDefaultUsername(){
        try {

            SharedPreferences sh = getSharedPreferences("complaint", MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            editor.clear();
            editor.commit();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class MyNavigationAdapter extends RecyclerView.Adapter<MyNavigationAdapter.MyCustomView> {

        List<NavModel> data;

        public MyNavigationAdapter(List<NavModel> data) {
            this.data = data;
        }

        @Override
        public MyNavigationAdapter.MyCustomView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyNavigationAdapter.MyCustomView(LayoutInflater
                    .from(getBaseContext()).inflate(R.layout.layout_navigation_recycler, null));
        }

        @Override
        public void onBindViewHolder(MyNavigationAdapter.MyCustomView holder, final int position) {

            holder.textView.setText(data.get(position).getName());
            holder.imageView.setImageDrawable(data.get(position).getImage());

            holder.Root.setOnClickListener(v -> {

                switch (position) {
                    case 0:
                        Intent intent = new Intent(getBaseContext(), SearchObject.class);
//                        intent.putExtra("RequestVacation", "true");
                        startActivity(intent);
                        closeDrawer();
                        break;
                    case 1:
                        Intent intent1 = new Intent(getBaseContext(), RegisterObject.class);
//                        intent1.putExtra("data", getIntent().getExtras().getString("data"));
                        startActivity(intent1);
                        closeDrawer();
                        break;
                    case 2:
                        Intent intent12 = new Intent(getBaseContext(), Complaint.class);
                        if (getIntent().getExtras() != null && getIntent().getExtras().getString("data") != null) {
                            intent12.putExtra("data", getIntent().getExtras().getString("data"));
                        }
                        startActivity(intent12);
                        closeDrawer();
                        break;

                    case 3:
                        startActivity(new Intent(getBaseContext(), ConnectToUs.class));
                        closeDrawer();
                        break;
                    case 4:
                        startActivity(new Intent(getBaseContext(),AboutUs.class));
                        break;
                    case 5:
                        deleteSavedDefaultUsername();
                        ShowDialog();
                        break;
                }

            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyCustomView extends RecyclerView.ViewHolder {

            private RelativeLayout Root;
            private TextView textView;
            private ImageView imageView;

            public MyCustomView(View itemView) {
                super(itemView);
                Root = itemView.findViewById(R.id.NavigationRecycler_Root);
                textView = itemView.findViewById(R.id.NavigationRecycler_Text);
                imageView = itemView.findViewById(R.id.NavigationRecycler_Image);

            }
        }
    }


    private List<NavModel> navigationData() {
        List<NavModel> data = new ArrayList<>();
        for (int i = 0 ; i <= 5 ; i++) {

            if (i == 0) {
                NavModel model = new NavModel();
                model.setName("جستجو اشیا یافت شده");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_search));
                data.add(model);
            } else if (i == 1) {
                NavModel model = new NavModel();
                model.setName("ثبت شی یافت شده");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_complaint));
                data.add(model);
            } else if (i == 2) {
                NavModel model = new NavModel();
                model.setName("پیشنهادات و انتقادات");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_critical));
                data.add(model);
            } else if (i == 3) {
                NavModel model = new NavModel();
                model.setName("تماس با ما");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_contact));
                data.add(model);
            } else if (i == 4) {
                NavModel model = new NavModel();
                model.setName("درباره ی ما");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_about_us));
                data.add(model);
            } else if (i == 5) {
                NavModel model = new NavModel();
                model.setName("خروج");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_about_us));
                data.add(model);
            }

        }

        return data;
    }


    private void ShowDialog() {

        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(DriverMainActivityTwo.this);
            dialog.setView(R.layout.exit_permission);

            alertDialog = dialog.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater inflater = getLayoutInflater();
            alertDialog.setContentView(inflater.inflate(R.layout.exit_permission, null));
            alertDialog.setCancelable(false);
            alertDialog.show();

            CustomButton Yes = (CustomButton) alertDialog.findViewById(R.id.CustomPermission_Yes);
            CustomButton Cancel = (CustomButton) alertDialog.findViewById(R.id.CustomPermission_Cancel);
            if (Yes != null && Cancel != null) {
                Yes.setTypeface(CFProvider.getIRANIANSANS(getBaseContext()));
                Cancel.setTypeface(CFProvider.getIRANIANSANS(getBaseContext()));

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DBManager.getInstance(getApplicationContext()).deleteDrivers();
                        DBManager.getInstance(getApplicationContext()).deleteCitizen();

                        alertDialog.dismiss();
                        Intent intent = new Intent(getBaseContext(), MainPage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    void closeDrawer() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawers();
        }
    }

}
