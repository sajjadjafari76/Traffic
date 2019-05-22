package irstit.transport.PhonePay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anychart.graphics.vector.Image;

import java.util.List;

import irstit.transport.R;

public class TransctionRecycler extends RecyclerView.Adapter<TransctionRecycler.RecycleItem> {

    private List<TransctionModel> list;
    private Context context;

    public TransctionRecycler(List<TransctionModel> list, Context bb) {
        this.context = bb;
        this.list = list;
    }

    @Override
    public RecycleItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        return new TransctionRecycler.RecycleItem(LayoutInflater.from(context).inflate(R.layout.transction_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleItem recycleItem, int i) {

        recycleItem.pirce.setText(list.get(i).getPrice());
        recycleItem.tranctionnumber.setText(list.get(i).getTransctionNumber());
        recycleItem.date.setText(list.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleItem extends RecyclerView.ViewHolder {
        private TextView pirce, tranctionnumber,date;
        private ImageView image;

        public RecycleItem(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.transctionsearchimage);
            pirce = itemView.findViewById(R.id.transction_price);
            tranctionnumber = itemView.findViewById(R.id.transctoinnumber);
            date = itemView.findViewById(R.id.transction_Date);

        }
    }
}
