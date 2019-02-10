package irstit.transport.Drivers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.DriverInfoModel;
import irstit.transport.R;

public class Profile extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        RecyclerView recyclerInfo = view.findViewById(R.id.SearchObject_RecyclerInfo);
        RecyclerView recyclerVehicle = view.findViewById(R.id.SearchObject_RecyclerVehicle);

        ProfileInfoAdapter infoAdapter = new ProfileInfoAdapter(DBManager.getInstance(getContext()).getDriverInfo());
        recyclerInfo.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        recyclerInfo.setAdapter(infoAdapter);

        ProfileVehicleAdapter vehicleAdapter = new ProfileVehicleAdapter(DBManager.getInstance(getContext()).getDriverInfo());
        recyclerVehicle.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        recyclerVehicle.setAdapter(vehicleAdapter);


        return view;
    }


    private class ProfileInfoAdapter extends RecyclerView.Adapter<ProfileInfoAdapter.MyCustomView> {

        DriverInfoModel data;

        public ProfileInfoAdapter(DriverInfoModel data) {
            this.data = data;
        }

        @Override
        public MyCustomView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyCustomView(LayoutInflater
                    .from(getContext()).inflate(R.layout.layout_profile_info, null));
        }

        @Override
        public void onBindViewHolder(MyCustomView holder, final int position) {

            try {
                switch (position) {
                    case 0:
                        holder.Text.setText(" نام پدر : ".concat(data.getParent()));
                        break;
                    case 1:
                        holder.Text.setText(" کد ملی : ".concat(data.getNationalCode()));
                        break;
                    case 2:
                        holder.Text.setText(" تلفن همراه : ".concat(data.getTelephone()));
                        break;
                    case 3:
                        holder.Text.setText(" ش.شناسنامه : ".concat(data.getBirthCertificate()));
                        break;

                }
            } catch (Exception e) {
                Log.e("ProfileError", e.toString() + " | ");
            }

        }

        @Override
        public int getItemCount() {
            return 4;
        }


        class MyCustomView extends RecyclerView.ViewHolder {

            private TextView Text;
//            private CardView Root;

            public MyCustomView(View itemView) {
                super(itemView);
                Text = itemView.findViewById(R.id.ProfileInfo_Text);
//                Root = itemView.findViewById(R.id.RequestVacation_Root);

            }
        }

    }


    private class ProfileVehicleAdapter extends RecyclerView.Adapter<ProfileVehicleAdapter.MyCustomView> {

        DriverInfoModel data;

        public ProfileVehicleAdapter(DriverInfoModel data) {
            this.data = data;
        }

        @Override
        public MyCustomView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyCustomView(LayoutInflater
                    .from(getContext()).inflate(R.layout.layout_profile_info, null));
        }

        @Override
        public void onBindViewHolder(MyCustomView holder, final int position) {

            try {
                switch (position) {
                    case 0:
                        holder.Text.setText(" پلاک خودرو : ".concat(data.getVehiclePelak()));
                        break;
                    case 1:
                        holder.Text.setText(" کد خودرو : ".concat(data.getVehicleCode()));
                        break;
                    case 2:
                        holder.Text.setText(" نوع خودرو : ".concat(data.getVehicleType()));
                        break;
                    case 3:
                        holder.Text.setText(" مدل خودرو : ".concat(data.getVehicleModel()));
                        break;
                    case 4:
                        holder.Text.setText(" نوع خط : ".concat(data.getLineType()));
                        break;
                    case 5:
                        holder.Text.setText(" تاریخ ثبت : ".concat(data.getRegisterDate()));
                        break;

                }
            } catch (Exception e) {
                Log.e("ProfileError", e.toString() + " | ");
            }

        }

        @Override
        public int getItemCount() {
            return 6;
        }


        class MyCustomView extends RecyclerView.ViewHolder {

            private TextView Text;
//            private CardView Root;

            public MyCustomView(View itemView) {
                super(itemView);
                Text = itemView.findViewById(R.id.ProfileInfo_Text);
//                Root = itemView.findViewById(R.id.RequestVacation_Root);

            }
        }

    }

}




