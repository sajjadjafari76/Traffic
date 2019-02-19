package irstit.transport.Drivers;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import irstit.transport.DataBase.DBManager;
import irstit.transport.DataModel.DriverInfoModel;
import irstit.transport.Drivers.Login.ActivityLogin;
import irstit.transport.Drivers.Login.GetPhone;
import irstit.transport.R;
import irstit.transport.Views.EditnameDialogFragment;
import irstit.transport.Views.MyDialogManager;

public class Profile extends Fragment {

    private AlertDialog.Builder alertDialog;
    private AlertDialog dialog;
    private AlertDialog.Builder Dialog;
    private TextView Name, NullInfoDriver;
    private ExpansionLayout Vehicle, Human;
    private ExpansionHeader VehicleInfo, HumanInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        alertDialog = new AlertDialog.Builder(getContext());
        Dialog = new AlertDialog.Builder(getActivity());
        Name = view.findViewById(R.id.Profile_Name);
        NullInfoDriver = view.findViewById(R.id.Profile_NullInfoDriver);
        Vehicle = view.findViewById(R.id.Profile_Vehicle);
        Human = view.findViewById(R.id.Profile_Human);
        VehicleInfo = view.findViewById(R.id.Profile_VehicleInfo);
        HumanInfo = view.findViewById(R.id.Profile_HumanInfo);
        Name.setText(DBManager.getInstance(getContext()).getDriverInfo().getName() + " " + DBManager.getInstance(getContext()).getDriverInfo().getFamily());
        QrCode();


        ExpansionLayoutCollection expansionLayoutCollection = new ExpansionLayoutCollection();
        expansionLayoutCollection.add(Vehicle);
        expansionLayoutCollection.add(Human);

        expansionLayoutCollection.openOnlyOne(true);

//
//        Vehicle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Info.isExpanded()) {
//                    Info.toggle(true);
//                }
//            }
//        });
//
//        Human.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Human.isExpanded()) {
//                    Human.toggle(true);
//                }
//            }
//        });



        RecyclerView recyclerInfo = view.findViewById(R.id.SearchObject_RecyclerInfo);
        RecyclerView recyclerVehicle = view.findViewById(R.id.SearchObject_RecyclerVehicle);
        TextView logOut = view.findViewById(R.id.Profile_Logout);
        TextView changePass = view.findViewById(R.id.Profile_ChangePass);
        ImageView picture = view.findViewById(R.id.Profile_Picture);

        ProfileInfoAdapter infoAdapter = new ProfileInfoAdapter(DBManager.getInstance(getContext()).getDriverInfo());
        recyclerInfo.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        recyclerInfo.setAdapter(infoAdapter);

        if (DBManager.getInstance(getContext()).getDriverInfo().getVehicleCode() != null) {
            NullInfoDriver.setVisibility(View.GONE);
            recyclerVehicle.setVisibility(View.VISIBLE);
            ProfileVehicleAdapter vehicleAdapter = new ProfileVehicleAdapter(DBManager.getInstance(getContext()).getDriverInfo());
            recyclerVehicle.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
            recyclerVehicle.setAdapter(vehicleAdapter);
        }else {
            NullInfoDriver.setVisibility(View.VISIBLE);
            recyclerVehicle.setVisibility(View.GONE);
        }

        logOut.setOnClickListener(v -> {

            alertDialog.setCancelable(false);
            alertDialog.setMessage("آیا مطمئن هستید؟");
            alertDialog.setNegativeButton("خیر", (dialog, which) -> dialog.dismiss());
            alertDialog.setPositiveButton("بله", (dialog, which) -> {
                logOut();
                getActivity().finish();
            });
            alertDialog.show();

        });

        changePass.setOnClickListener(v -> {

//            dialog.show();

//            showEditDialog();

            Intent intent = new Intent(getContext(), ActivityLogin.class);
            intent.putExtra("state", "ChangePass");

            startActivity(intent);

        });


        if (DBManager.getInstance(getContext()).getDriverInfo().getPicture() != null) {

            Picasso.with(getContext())
                    .load("http://cpanel.traffictakestan.ir/" + DBManager.getInstance(getContext()).getDriverInfo().getPicture())
                    .error(R.drawable.noimage)
                    .into(picture);
        } else {
            picture.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.no_image));
        }
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
                    case 4:
                        holder.Text.setText(" نوع مالکیت : ".concat(data.getOwner()));
                        break;

                }
            } catch (Exception e) {
                Log.e("ProfileError", e.toString() + " | ");
            }

        }

        @Override
        public int getItemCount() {
            return 5;
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
                        PersianCalendar date = new PersianCalendar(
                                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(data.getRegisterDate()).getTime());
                        holder.Text.setText(" تاریخ ثبت : ".concat(date.getPersianYear() + "/" + date.getPersianMonth() + "/" + date.getPersianDay()));
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

    private void logOut() {
        DBManager.getInstance(getContext()).deleteDrivers();
    }


    private void QrCode() {

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.layout_change_pass, null);
        Dialog.setView(dialogLayout);
        Dialog.setCancelable(false);
        Dialog.setMessage("تغییر شماره موبایل");
        Dialog.setPositiveButton("بعداً", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
//                AppController.getInstance().getRequestQueue().cancelAll("Dialog");
//
//                Log.e("dialog11", AppController.getInstance().getRequestQueue().getSequenceNumber() + "|");

            }
        });
        Dialog.setNegativeButton("انجام دادم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                getApps(keyMain);
//                Log.e("dialog11", AppController.getInstance().getRequestQueue().getSequenceNumber() + "|");
            }
        });
        dialog = Dialog.create();
//        textDialog = dialogLayout.findViewById(R.id.text);
//
//        FragmentTransaction transaction = getActivity()
//                .getSupportFragmentManager().beginTransaction().addToBackStack(null);
//        transaction.replace(R.id.ChangePass_Container, new GetPhone());
//        transaction.commit();

//        progressBarDoalog = dialogLayout.findViewById(R.id.progress);
//    textcode = dialogLayout.findViewById(R.id.textcode);

//    Dialog.show();

//            AlertDialog dialog = builder.create();
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }

//    private void showEditDialog() {
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        EditnameDialogFragment editNameDialogFragment = EditnameDialogFragment.newInstance("Some Title");
//        editNameDialogFragment.show(fm, "fragment_edit_name");
//    }



}




