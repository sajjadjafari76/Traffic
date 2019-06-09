package irstit.transport.annoucment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import irstit.transport.R;

public class AnnouncementRecyclerAdoptor extends RecyclerView.Adapter<AnnouncementRecyclerAdoptor.custom> {

    private List<AnnoucmentContorol> li;
    private Activity context;

    public AnnouncementRecyclerAdoptor(List<AnnoucmentContorol> li, Activity k) {
        this.li = li;
        this.context = k;
    }

    @NonNull
    @Override
    public AnnouncementRecyclerAdoptor.custom onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AnnouncementRecyclerAdoptor.custom(LayoutInflater.from(context).inflate(R.layout.annoucment_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementRecyclerAdoptor.custom custom, int i) {

        custom.title.setText(li.get(i).getTitle());
        custom.des.setText(li.get(i).getContent());
        custom.week.setText(li.get(i).getWeek());

        custom.link.setOnClickListener(v -> {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(li.get(i).getLink()));
            context.startActivity(browserIntent);

        });


        if (li.get(i).getLink().equals("nolink")) {
//            not thing happens here
            custom.link.setText("");
        } else {
            custom.link.setText(li.get(i).getLink());
        }


        custom.date.setText(li.get(i).getDate());

        if (li.get(i).getState() == "0") {

//          not thing happens here

        } else if (li.get(i).getState() == "1") {

            custom.role.setText("(ویژه راننده)");

        } else {

            custom.role.setText("(ویژه شهروند)");

        }
        Picasso.with(context)
                .load(li.get(i).getImage())
                .error(R.drawable.etelaye)
                .into(custom.mainImage);

    }

    @Override
    public int getItemCount() {
        return li.size();
    }


    class custom extends RecyclerView.ViewHolder {
        private TextView date, role, week;
        private TextView title, des, link;
        private ImageView mainImage;

        public custom(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.annoucment_title);
            des = itemView.findViewById(R.id.annoucment_text);
            date = itemView.findViewById(R.id.annoucment_date);
            link = itemView.findViewById(R.id.annoucment_link);
            mainImage = itemView.findViewById(R.id.annoucment_image);
            role = itemView.findViewById(R.id.announcement_refers);
            week = itemView.findViewById(R.id.annoucment_week);
        }
    }
}
