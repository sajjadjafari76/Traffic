package irstit.transport.annoucment;

import android.content.Context;
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
    private Context context;

    public AnnouncementRecyclerAdoptor(List<AnnoucmentContorol> li, Context k) {
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
        custom.link.setText(li.get(i).getLink());
        custom.date.setText(li.get(i).getDate());
        Picasso.with(context).load(li.get(i).getImage()).into(custom.mainImage);

    }

    @Override
    public int getItemCount() {
        return li.size();
    }


    class custom extends RecyclerView.ViewHolder{
        private TextView title,date,des,link;
        private ImageView mainImage;
        public custom(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.annoucment_title);
            des = itemView.findViewById(R.id.annoucment_text);
            date = itemView.findViewById(R.id.annoucment_date);
            link = itemView.findViewById(R.id.annoucment_link);
            mainImage = itemView.findViewById(R.id.annoucment_image);
        }
    }
}
