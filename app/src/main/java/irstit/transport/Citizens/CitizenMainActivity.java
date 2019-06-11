package irstit.transport.Citizens;

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
import irstit.transport.Citizens.complaint.ComplaintTrack;
import irstit.transport.ConnectToUs;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.NavModel;
import irstit.transport.LetterRate;
import irstit.transport.MainPage;
import irstit.transport.R;
import irstit.transport.ViewPager.MainPager;
import irstit.transport.Views.CFProvider;
import irstit.transport.Views.CustomButton;

public class CitizenMainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private AlertDialog alertDialog;

    public  static  String sharinCitizenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_main);

        LinearLayout regCompliant = findViewById(R.id.CitizenMain_regCompliant);
        LinearLayout SearchObj = findViewById(R.id.CitizenMain_SearchObj);
        LinearLayout Price = findViewById(R.id.CitizenMain_Price);
        LinearLayout regObj = findViewById(R.id.CitizenMain_regObj);
        TextView Name = findViewById(R.id.CitizenMain_Name);

        Name.setText(DBManager.getInstance(getBaseContext()).getCitizenInfo().getUserName());


        regCompliant.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), Complaint.class));
        });
        SearchObj.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), SearchObject.class));
        });
        Price.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), LetterRate.class));
        });
        regObj.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), RegisterObject.class));
        });


        drawer = findViewById(R.id.CitizenMain_Drawer);
        ImageView iconDrawer = findViewById(R.id.CitizenMain_NavigatorIcon);
        iconDrawer.setOnClickListener(v -> drawer.openDrawer(Gravity.END));


        RecyclerView navigation_Recycler = findViewById(R.id.CitizenMain_RecyClerView);
        navigation_Recycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        navigation_Recycler.setAdapter(new MyNavigationAdapter(navigationData()));


        LinearLayout Site = findViewById(R.id.CitizenMain_Site);
        LinearLayout Dial = findViewById(R.id.CitizenMain_Dial1);

        Dial.setOnClickListener(v -> {

            Intent dial1 = new Intent();
            dial1.setAction("android.intent.action.DIAL");
            dial1.setData(Uri.parse("tel:" + "02835237500"));
            startActivity(dial1);

        });
        Site.setOnClickListener(v -> {

            Uri uri = Uri.parse("http://www.traffictakestan.ir"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });

        NavigationView navigationView = findViewById(R.id.MAinActivity_NavigationView_citizen);
        View headerView = navigationView.getHeaderView(0);
        TextView textView = headerView.findViewById(R.id.Navigation_Enter);
        textView.setText(DBManager.getInstance(getBaseContext()).getCitizenInfo().getUserName()+" ");


        changeDefaultUsername();
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
                        startActivity(new Intent(getBaseContext(), ComplaintTrack.class));
                        closeDrawer();
                        break;
                    case 4:
                        startActivity(new Intent(getBaseContext(), Criticals_Suggestion.class));
                        closeDrawer();
                        break;
                    case 5:
                        startActivity(new Intent(getBaseContext(), ConnectToUs.class));
                        closeDrawer();
                        break;
                    case 6:
                        startActivity(new Intent(getBaseContext(),AboutUs.class));
                        break;
                    case 7:
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


     private  void  changeDefaultUsername(){


         try {
             SharedPreferences sharedPreferences = getSharedPreferences("complaint",MODE_PRIVATE);
             String getStringObject = sharedPreferences.getString("complaintArray","-1");
             JSONObject jsonObject = new JSONObject(getStringObject);
             JSONObject name = new JSONObject(jsonObject.getString("userdata"));



             }

         catch (Exception e){
              e.printStackTrace();
         }


     }
    private List<NavModel> navigationData() {
        List<NavModel> data = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {

            if (i == 0) {
                NavModel model = new NavModel();
                model.setName("جستجو اشیا گمشده");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_search));
                data.add(model);
            } else if (i == 1) {
                NavModel model = new NavModel();
                model.setName("ثبت شی گمشده");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_complaint));
                data.add(model);
            } else if (i == 2) {
                NavModel model = new NavModel();
                model.setName("شکایات");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_complaint));
                data.add(model);
            } else if (i == 3) {
                NavModel model = new NavModel();
                model.setName("پیگیری شکایت");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_complaint));
                data.add(model);
            } else if (i == 4) {
                NavModel model = new NavModel();
                model.setName("پیشنهادات و انتقادات");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_critical));
                data.add(model);
            } else if (i == 5) {
                NavModel model = new NavModel();
                model.setName("تماس با ما");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_contact));
                data.add(model);
            } else if (i == 6) {
                NavModel model = new NavModel();
                model.setName("درباره ی ما");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_about_us));
                data.add(model);
            } else if (i == 7) {
                NavModel model = new NavModel();
                model.setName("خروج");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_exit));
                data.add(model);
            }

        }

        return data;
    }

    void closeDrawer() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawers();
        }
    }


    private void ShowDialog() {

        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(CitizenMainActivity.this);
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
