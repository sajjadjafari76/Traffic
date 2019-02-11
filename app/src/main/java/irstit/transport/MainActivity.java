package irstit.transport;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import irstit.transport.AppController.AppController;
import irstit.transport.Citizens.Criticals_Suggestion;
import irstit.transport.Citizens.RegisterObject;
import irstit.transport.Citizens.SearchObject;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.NavModel;
import irstit.transport.DataModel.NewsModel;
import irstit.transport.DataModel.ReqVacationModel;
import irstit.transport.Drivers.DriversMainActivity;
import irstit.transport.Drivers.Login.ActivityLogin;
import irstit.transport.Drivers.VacationSearch;
import irstit.transport.Views.CustomTextView;
import irstit.transport.Views.Utilities;
import irstit.transport.Views.Utils;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout InfoNavigation;
    private RelativeLayout LoginNavigation;
    private TextView Navigation_Info_Name, MainActivity_Login_Text;
    private CustomTextView Navigation_Info_Code, MainActivity_Date;
    private DrawerLayout drawer;
    public static List<NewsModel> mNews = new ArrayList<>();
    public List<NewsModel> mNewsNew = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();

        if (DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone() != null && !DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone().equals("")) {
            InfoNavigation.setVisibility(View.VISIBLE);
            LoginNavigation.setVisibility(View.GONE);

            Navigation_Info_Name.setText(DBManager.getInstance(getBaseContext()).getDriverInfo().getName().concat(" " + DBManager.getInstance(getBaseContext()).getDriverInfo().getFamily()));
            Navigation_Info_Code.setText("  کد خودرو : ".concat(DBManager.getInstance(getBaseContext()).getDriverInfo().getBirthCertificate()));

            MainActivity_Login_Text.setText(getResources().getString(R.string.MainActivity_Login_Text_2));
        } else {
            MainActivity_Login_Text.setText(getResources().getString(R.string.MainActivity_Login_Text_1));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Slider slider = findViewById(R.id.MainActivity_Slider);
        RelativeLayout Login = findViewById(R.id.MainActivity_Login);
        LinearLayout complaint = findViewById(R.id.MainActivity_Complaint);
        LinearLayout registerObject = findViewById(R.id.MainActivity_RegisterObject);
        LinearLayout search = findViewById(R.id.MainActivity_Search);
        ImageView iconDrawer = findViewById(R.id.MainActivity_NavigatorIcon);
        TextView additionalNews = findViewById(R.id.MainActivity_Additional);
        MainActivity_Date = findViewById(R.id.MainActivity_Date);
        drawer = findViewById(R.id.MainActivity_Drawer);
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
        LoginNavigation.setOnClickListener(this);


        Slider.init(new PicassoImageLoadingService(getApplicationContext()));
        slider.setAdapter(new SliderAdapter());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        RecyclerView navigation_Recycler = findViewById(R.id.MainActivity_Navigation_RecyClerView);
        navigation_Recycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        navigation_Recycler.setAdapter(new MyNavigationAdapter(navigationData()));


        getNews();


        MainActivity_Date.setText(Utilities.getCurrentShamsidate());
    }

    private List<NavModel> navigationData() {
        List<NavModel> data = new ArrayList<>();
        for (int i = 0 ; i <= 8 ; i++) {

            if (i == 0) {
                NavModel model = new NavModel();
                model.setName("درخواست مرخصی");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_vacation));
                data.add(model);
            } else if (i == 1) {
                NavModel model = new NavModel();
                model.setName("ثبت اشیا گمشده");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_lostobj));
                data.add(model);
            } else if (i == 2) {
                NavModel model = new NavModel();
                model.setName("جستجو اشیا گمشده");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_search));
                data.add(model);
            } else if (i == 3) {
                NavModel model = new NavModel();
                model.setName("ثبت شکایات");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_complaint));
                data.add(model);
            } else if (i == 4) {
                NavModel model = new NavModel();
                model.setName("نمایش نرخ نامه");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_price));
                data.add(model);
            } else if (i == 5) {
                NavModel model = new NavModel();
                model.setName("پیشنهادات و انتقادات");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_critical));
                data.add(model);
            } else if (i == 6) {
                NavModel model = new NavModel();
                model.setName("تماس با ما");
                model.setImage(ContextCompat.getDrawable(getBaseContext(), R.drawable.img_contact));
                data.add(model);
            } else if (i == 7) {
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
                startActivity(new Intent(getBaseContext(), Criticals_Suggestion.class));
                break;
            case R.id.Navigation_Login:
                startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
                break;
            case R.id.MainActivity_Login:
                if (DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone() != null) {
                    if (DBManager.getInstance(getBaseContext()).getDriverInfo().getTelephone().equals("")) {
                        startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), DriversMainActivity.class));
                    }
                } else {
                    startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
                }
                break;
            case R.id.MainActivity_RegisterObject:
                startActivity(new Intent(getApplicationContext(), RegisterObject.class));
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

    private class SliderAdapter extends ss.com.bannerslider.adapters.SliderAdapter {

        @Override
        public int getItemCount() {
            return 3;
        }

        @Override
        public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
            switch (position) {
                case 0:
                    imageSlideViewHolder.bindImageSlide("http://traffictakestan.ir/images/photo_2019-01-27_05-26-38.jpg");
                    break;
                case 1:
                    imageSlideViewHolder.bindImageSlide("https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg");
                    break;
                case 2:
                    imageSlideViewHolder.bindImageSlide("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png");
                    break;
            }
        }
    }

    public class PicassoImageLoadingService implements ImageLoadingService {
        public Context context;

        public PicassoImageLoadingService(Context context) {
            this.context = context;
        }

        @Override
        public void loadImage(String url, ImageView imageView) {
            Picasso.with(context).load(url).into(imageView);
        }

        @Override
        public void loadImage(int resource, ImageView imageView) {
            Picasso.with(context).load(resource).into(imageView);
        }

        @Override
        public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
            Picasso.with(context).load(url).placeholder(placeHolder).error(errorDrawable).into(imageView);
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
                    .resize(200, 200)
                    .into(holder.imageView);

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
                        Intent intent = new Intent(getBaseContext(), DriversMainActivity.class);
                        intent.putExtra("RequestVacation", "true");
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        startActivity(new Intent(getBaseContext(), ConnectToUs.class));
                        break;
                    case 7:

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

                                for (int i = 0 ; i < array.length() ; i++) {

                                    JSONObject myObject = array.getJSONObject(i);

                                    NewsModel model = new NewsModel();
                                    model.setContent(myObject.getString("n_body"));
                                    model.setDate(myObject.getString("n_date"));
                                    model.setImage(myObject.getString("n_thumb"));
                                    model.setTopic(myObject.getString("n_title"));

                                    mNews.add(model);

                                    if (i <= 2 ) {
                                        mNewsNew.add(model);
                                    }

                                }

                                RecyclerView recyclerView = findViewById(R.id.MainActivity_Recycler);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
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


}
//https://github.com/Blogcat/Android-ExpandableTextView