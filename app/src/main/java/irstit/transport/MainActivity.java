package irstit.transport;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.Citizens.Complaint;
import irstit.transport.Citizens.ComplaintTrack;
import irstit.transport.Citizens.Criticals_Suggestion;
import irstit.transport.Citizens.RegisterObject;
import irstit.transport.Citizens.SearchObject;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.NavModel;
import irstit.transport.DataModel.NewsModel;
import irstit.transport.Drivers.DriversMainActivity;
import irstit.transport.Drivers.FoundedObjectActivity;
import irstit.transport.Drivers.Login.ActivityLogin;
import irstit.transport.Views.CustomTextView;
import irstit.transport.Views.Utilities;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout InfoNavigation;
    private RelativeLayout LoginNavigation;
    private TextView Navigation_Info_Name, MainActivity_Login_Text, MainActivity_Txt1, MainActivity_Txt3, Navigation_Enter_name;
    private CustomTextView Navigation_Info_Code, MainActivity_Date;
    private DrawerLayout drawer;
    public static List<NewsModel> mNews = new ArrayList<>();
    public List<NewsModel> mNewsNew = new ArrayList<>();
    private List<String> Image = new ArrayList<>();
    private SliderLayout sliderLayout;

    @Override
    protected void onStart() {
        super.onStart();


       /*     SharedPreferences sh = getSharedPreferences("complaint", MODE_PRIVATE);
            String objectForDriverName = sh.getString("complaintArray", "-1");
            JSONObject jsonObject = new JSONObject(objectForDriverName);
            JSONObject name = new JSONObject(jsonObject.getString("userdata"));
            Navigation_Enter_name.setText(name.getString("d_name"));
            Log.e("nameFromhere", name.toString());

    //this code is  in if   //
           */

        if (DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone() != null && !DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone().equals("")) {

            Log.e("telephone", DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone() + " | ");
            InfoNavigation.setVisibility(View.VISIBLE);
            LoginNavigation.setVisibility(View.GONE);

            Navigation_Info_Name.setText(DBManager.getInstance(getBaseContext()).getDriverInfo().getName().concat(" " + DBManager.getInstance(getBaseContext()).getDriverInfo().getFamily())
            );


            if (DBManager.getInstance(getBaseContext()).getDriverInfo().getVehicleCode() != null) {
                Navigation_Info_Code.setText("  کد خودرو : ".concat(DBManager.getInstance(getBaseContext()).getDriverInfo().getVehicleCode()));
            } else {
                Navigation_Info_Code.setVisibility(View.INVISIBLE);
            }

            MainActivity_Login_Text.setText(getResources().getString(R.string.MainActivity_Login_Text_2));

            if (DBManager.getInstance(getBaseContext()).getDriverInfo().getOwnerId() != null) {
                if (!DBManager.getInstance(getBaseContext()).getDriverInfo().getIsTaxi().equals("0")) {
                    MainActivity_Txt1.setText("ثبت مرخصی");
                }
                MainActivity_Txt3.setText("ثبت اشیا یافت شده");
            }
        } else {
            MainActivity_Login_Text.setText(getResources().getString(R.string.MainActivity_Login_Text_1));

            MainActivity_Txt1.setText("نمایش نرخ نامه");
            MainActivity_Txt3.setText("شکایات");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderLayout = findViewById(R.id.MainActivity_Slider);
//        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
//        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderLayout.setAutoScrolling(false);

        RelativeLayout Login = findViewById(R.id.MainActivity_Login);
        LinearLayout complaint = findViewById(R.id.MainActivity_Complaint);
        LinearLayout registerObject = findViewById(R.id.MainActivity_RegisterObject);
        LinearLayout search = findViewById(R.id.MainActivity_Search);
        ImageView iconDrawer = findViewById(R.id.MainActivity_NavigatorIcon);
        TextView additionalNews = findViewById(R.id.MainActivity_Additional);
        MainActivity_Date = findViewById(R.id.MainActivity_Date);
        drawer = findViewById(R.id.MainActivity_Drawer);
        MainActivity_Txt1 = findViewById(R.id.MainActivity_Txt1);
        MainActivity_Txt3 = findViewById(R.id.MainActivity_Txt3);
        iconDrawer.setOnClickListener(this);
        complaint.setOnClickListener(this);
        Login.setOnClickListener(this);
        registerObject.setOnClickListener(this);
        search.setOnClickListener(this);
        additionalNews.setOnClickListener(this);
        NavigationView navigationView = findViewById(R.id.MAinActivity_NavigationView);

        Toolbar toolbar = findViewById(R.id.MainActivity_Toolbar);
        setSupportActionBar(toolbar);

        View view = navigationView.getHeaderView(0);
        LoginNavigation = view.findViewById(R.id.Navigation_Login);
        InfoNavigation = view.findViewById(R.id.Navigation_Info);
        Navigation_Info_Name = view.findViewById(R.id.Navigation_Info_Name);
        MainActivity_Login_Text = findViewById(R.id.MainActivity_Login_Text);
        Navigation_Info_Code = view.findViewById(R.id.Navigation_Info_Code);
        Navigation_Enter_name = view.findViewById(R.id.Navigation_Enter);
        LoginNavigation.setOnClickListener(this);

        configSlider();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        RecyclerView navigation_Recycler = findViewById(R.id.MainActivity_Navigation_RecyClerView);
        navigation_Recycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        navigation_Recycler.setAdapter(new MyNavigationAdapter(navigationData()));

//        getNews();

        configNews();



        MainActivity_Date.setText(Utilities.getCurrentShamsidate());
    }

    private List<NavModel> navigationData() {
        List<NavModel> data = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {

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
            }

        }

        return data;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.MainActivity_Complaint:
                if (DBManager.getInstance(getBaseContext()).getDriverInfo().getOwnerId() == null) {
                    Intent intent = new Intent(getBaseContext(), Complaint.class);
                    intent.putExtra("data", getIntent().getExtras().getString("data"));
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getBaseContext(), FoundedObjectActivity.class));
                }
                break;
            case R.id.Navigation_Login:
                startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
                break;
            case R.id.MainActivity_Login:
                if (DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone() != null) {
                    if (DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone().equals("")) {
                        startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
                    } else {

                        try {
                            Intent intent = new Intent(getApplicationContext(), DriversMainActivity.class);
                            if (getIntent().getExtras() != null && getIntent().getExtras().getString("data") != null) {

                                JSONObject object = new JSONObject(getIntent().getExtras().getString("data"));
                                intent.putExtra("dataVacation", object.getJSONObject("fulltime").toString());

                            }
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                } else {
                    startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
                }
                break;
            case R.id.MainActivity_RegisterObject:
                if (DBManager.getInstance(getBaseContext()).getDriverInfo() == null) {
                    startActivity(new Intent(getApplicationContext(), LetterRate.class));
                    Log.e("data1", "123");
                } else {
                    if (DBManager.getInstance(getBaseContext()).getDriverInfo().getOwnerId() != null) {
                        if (!DBManager.getInstance(getBaseContext()).getDriverInfo().getIsTaxi().equals("0")) {
                            Intent intent1 = new Intent(getBaseContext(), DriversMainActivity.class);
                            intent1.putExtra("RequestVacation", "true");
                            startActivity(intent1);
                            Log.e("data1", "321");
                        }
                    } else {
                        startActivity(new Intent(getApplicationContext(), LetterRate.class));
                        Log.e("data1", "112233");
                    }
                }
                break;
            case R.id.MainActivity_NavigatorIcon:
                drawer.openDrawer(Gravity.RIGHT);
                break;
            case R.id.MainActivity_Search:
                startActivity(new Intent(getApplicationContext(), SearchObject.class));
                break;
            case R.id.MainActivity_Additional:
                startActivity(new Intent(getApplicationContext(), News.class));
                break;
        }
    }

    private class MyPagerAdapter extends android.support.v4.view.PagerAdapter {

        int[] myImage = {R.drawable.img1, R.drawable.img2, R.drawable.img3};

        @Override
        public int getCount() {
            return myImage.length;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_pager, null);
            ImageView imageView = view.findViewById(R.id.Pager_Image);
            imageView.setBackground(getResources().getDrawable(myImage[position]));

            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return (view == o);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyCustomView> {

        List<NewsModel> data;

        public MyCustomAdapter(List<NewsModel> data) {
            this.data = data;
        }

        @Override
        public MyCustomAdapter.MyCustomView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyCustomAdapter.MyCustomView(LayoutInflater
                    .from(getBaseContext()).inflate(R.layout.layout_recycler_mainactivity, null));
        }

        @Override
        public void onBindViewHolder(MyCustomAdapter.MyCustomView holder, final int position) {

            holder.textView.setText(data.get(position).getTopic());

            Picasso.with(getBaseContext())
                    .load(Globals.APIURLIMAGE + data.get(position).getImage())
//                    .resize(200, 200)
                    .fit()
                    .into(holder.imageView);

            holder.cardView.setOnClickListener(v -> {

                Intent intent = new Intent(getApplicationContext(), News.class);
                intent.putExtra("position", String.valueOf(position));
                startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyCustomView extends RecyclerView.ViewHolder {

            private CardView cardView;
            private CustomTextView textView;
            private ImageView imageView;

            public MyCustomView(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.RecyclerMainActivity_Root);
                textView = itemView.findViewById(R.id.RecyclerMainActivity_Text);
                imageView = itemView.findViewById(R.id.RecyclerMainActivity_Image);

            }
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

    private void getNews() {
        StringRequest getPhoneRequest = new StringRequest(Request.Method.GET, Globals.APIURL + "/news",
                response -> {
                    Log.e("NewsResponse", response + " |");

                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("true")) {

                            JSONArray array = object.getJSONArray("news");

                            if (array.length() == 0) {

                            } else {
                                mNews.clear();

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject myObject = array.getJSONObject(i);

                                    NewsModel model = new NewsModel();
                                    model.setContent(myObject.getString("n_body"));
                                    model.setDate(myObject.getString("n_date"));
                                    model.setImage(myObject.getString("n_thumb"));
                                    model.setTopic(myObject.getString("n_title"));

                                    mNews.add(model);

                                    if (i <= 2) {
                                        mNewsNew.add(model);
                                    }

                                }

                                RecyclerView recyclerView = findViewById(R.id.MainActivity_Recycler);
                                recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 3, LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(new MyCustomAdapter(mNewsNew));

                            }
                        } else if (object.getString("status").equals("false")) {

                        }
                    } catch (Exception e) {
                        Log.e("ListLeavesError1", e.toString() + " |");

                    }
                },
                error -> {
                    Log.e("ListLeavesError2", error.toString() + " |");

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("phone", "9190467144");
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("token", "df837016d0fc7670f221197cd92439b5");
                return super.getHeaders();
            }
        };

        getPhoneRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(getPhoneRequest);

    }

    void configNews() {
        try {

            if (getIntent().getExtras() != null && !getIntent().getExtras().getString("data").isEmpty()) {
                JSONObject object = new JSONObject(getIntent().getExtras().getString("data"));
                if (object.getString("status").equals("true")) {

                    JSONArray array = object.getJSONArray("news");

                    if (array.length() == 0) {

                    } else {
                        mNews.clear();

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject myObject = array.getJSONObject(i);

                            NewsModel model = new NewsModel();
                            model.setContent(myObject.getString("n_body"));
                            model.setDate(myObject.getString("n_date"));
                            model.setImage(myObject.getString("n_thumb"));
                            model.setTopic(myObject.getString("n_title"));

                            mNews.add(model);

                            if (i <= 2) {
                                mNewsNew.add(model);
                            }

                        }

                        RecyclerView recyclerView = findViewById(R.id.MainActivity_Recycler);
                        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 3, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(new MyCustomAdapter(mNewsNew));

                    }
                } else if (object.getString("status").equals("false")) {

                }
            }
        } catch (Exception e) {
            Log.e("ListLeavesError111", e.toString() + " |");

        }
    }

    void configSlider() {
        try {
            if (getIntent().getExtras() != null && !getIntent().getExtras().getString("data").isEmpty()) {
                JSONObject object = new JSONObject(getIntent().getExtras().getString("data"));
                JSONArray array = object.getJSONArray("slider");


                for (int i = 0; i < array.length(); i++) {
                    JSONObject myObject = array.getJSONObject(i);
                    Image.add(myObject.getString("s_fileaddress"));
                    DefaultSliderView sliderView = new DefaultSliderView(getBaseContext());

                    sliderView.setImageUrl(myObject.getString("s_fileaddress"));

                    sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
//                    sliderView.setDescription("setDescription " + (i + 1));
                    final int finalI = i;
                    sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(SliderView sliderView) {

                        }
                    });

                    //at last add this view in your layout :
                    sliderLayout.addSliderView(sliderView);


                }


            } else {

                DefaultSliderView sliderView = new DefaultSliderView(getBaseContext());
                sliderView.setImageDrawable(R.drawable.slider_bk);
                sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

                sliderLayout.addSliderView(sliderView);

            }

        } catch (Exception e) {
            DefaultSliderView sliderView = new DefaultSliderView(getBaseContext());
            sliderView.setImageDrawable(R.drawable.slider_bk);
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            sliderLayout.addSliderView(sliderView);
        }
    }

    void closeDrawer() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawers();
        }
    }

}

