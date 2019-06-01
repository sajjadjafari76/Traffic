package irstit.transport;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anychart.scales.Linear;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import at.blogc.android.views.ExpandableTextView;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import irstit.transport.AppController.AppController;
import irstit.transport.DataModel.NewsModel;
import irstit.transport.Views.CustomTextView;

public class News extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private ImageView News_Backwards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        News_Backwards = findViewById(R.id.News_Back);
        recyclerView = findViewById(R.id.News_Recycler);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        MainAdapter adapter = new MainAdapter(getApplicationContext(), MainActivity.mNews);
        LinearLayoutManager layout = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        if (getIntent().getStringExtra("position")!= null) {
            layout.scrollToPositionWithOffset(Integer.parseInt(getIntent().getStringExtra("position")), 20);
        }
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);


        News_Backwards.setOnClickListener(v -> finish());

    }


    public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

        private Context mContext;
        private List<NewsModel> mDataSet;

        public MainAdapter(Context context, List<NewsModel> dataSet) {
            mContext = context;
            mDataSet = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.news_layout, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            try {
                Picasso.with(mContext)
                        .load(Globals.APIURLIMAGE + mDataSet.get(position).getImage())
                        .error(R.drawable.no_image)
                        .fit()
                        .into(holder.Image);

                PersianCalendar fromCalendar = new PersianCalendar(
                        new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(mDataSet.get(position).getDate()).getTime());

                holder.Topic.setText(mDataSet.get(position).getTopic());
                String content = String.valueOf(Html.fromHtml(mDataSet.get(position).getContent()));
                        holder.Content.setText(content);
                holder.Date.setText(fromCalendar.getPersianYear() + "/" + fromCalendar.getPersianMonth() + "/" + fromCalendar.getPersianDay());

                holder.Content.setAnimationDuration(1550L);

                // set interpolators for both expanding and collapsing animations
//                holder.Content.setInterpolator(new OvershootInterpolator());

// or set them separately
                holder.Content.setExpandInterpolator(new OvershootInterpolator());
                holder.Content.setCollapseInterpolator(new OvershootInterpolator());

                holder.Expand.setOnClickListener(v -> {
                    holder.Expand.setText(holder.Content.isExpanded() ? "ادامه خبر" : "پایان");
                    holder.Content.toggle();
                });
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("errorNews1", e.toString());
            }
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        public void remove(int position) {
            mDataSet.remove(position);
            notifyItemRemoved(position);
        }

        public void add(NewsModel text, int position) {
            mDataSet.add(position, text);
            notifyItemInserted(position);
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView Image;
            public CustomTextView Topic, Date;
            public ExpandableTextView Content;
            public TextView Expand;

            public ViewHolder(View itemView) {
                super(itemView);
                Image = itemView.findViewById(R.id.NewsLayout_Image);
                Topic = itemView.findViewById(R.id.NewsLayout_Topic);
                Content = itemView.findViewById(R.id.NewsLayout_Content);
                Date = itemView.findViewById(R.id.NewsLayout_Date);
                Expand = itemView.findViewById(R.id.NewsLayout_Expand);
            }
        }
    }

}
