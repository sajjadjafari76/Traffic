package irstit.transport.ViewPager.ViewInfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import irstit.transport.R;
import irstit.transport.Views.CustomTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataTab extends Fragment {


    public DataTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_data_tab, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.DataTab_Recycler);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new MyCustomAdapter(getdata()));

        return view;
    }


    private List<String> getdata() {
        List<String> data = new ArrayList<>();

        for (int i = 0; i < 11 ; i++) {
            if (i==0) {
                String text = "بخشنامه 1";
                data.add(text);
            }else if (i==1) {
                String text =  "بخشنامه 2";
                data.add(text);
            }else if (i==2) {
                String text = "بخشنامه 3";
                data.add(text);
            }else if (i==3) {
                String text = "بخشنامه 4";
                data.add(text);
            }else if (i==4) {
                String text = "بخشنامه 5";
                data.add(text);
            } else if (i==5) {
                String text = "بخشنامه 6";
                data.add(text);
//            }else if (i==6) {
//                String text = new String();
////                text = "بنای عمارت میراث فرهنگی";
////                data.add(text);
//            }else if (i==6) {
//                String text = "بخش خرمدشت";
//                data.add(text);
//            }else if (i==7) {
//                String text ="بخش ضیاءآباد";
//                data.add(text);
//            }else if (i==8) {
//                String text =  "بخش اسفرورین";
//                data.add(text);
//            }

            }
        }
        return data;
    }

    private class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyCustomView> {

        List<String> data;
        MyCustomAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public MyCustomAdapter.MyCustomView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyCustomAdapter.MyCustomView(LayoutInflater
                    .from(getActivity()).inflate(R.layout.layout_electronic_item,null));
        }

        @Override
        public void onBindViewHolder(MyCustomAdapter.MyCustomView holder, final int position) {

            if ( (position % 2) == 0 ) {
                holder.view2.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.green_50));
            }

            holder.view1.setVisibility(View.GONE);
            holder.view2.setVisibility(View.VISIBLE);

            holder.textView.setText(data.get(position).toString());
            switch (position) {
                case 0:
                    holder.image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.baget));
                    break;
                case 1:
                    holder.image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.baget));
                    break;
                case 2:
                    holder.image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.baget));
                    break;
                case 3:
                    holder.image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.baget));
                    break;
                case 4:
                    holder.image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.baget));
                    break;
                case 5:
                    holder.image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.baget));
                    break;
                case 6:
                    holder.image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.baget));
                    break;
                case 7:
                    holder.image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.baget));
                    break;
                case 8:
                    holder.image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.baget));
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyCustomView extends RecyclerView.ViewHolder {

            private CardView cardView;
            private TextView textView;
            private ImageView image;
            private RelativeLayout view1, view2;

            MyCustomView(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.ElectronicItem_Card);
                textView = itemView.findViewById(R.id.ElectronicItem_Text1);
                image = itemView.findViewById(R.id.ElectronicItem_Image);
                view1 = itemView.findViewById(R.id.ElectronicItem_View1);
                view2 = itemView.findViewById(R.id.ElectronicItem_View2);
            }
        }
    }


}
