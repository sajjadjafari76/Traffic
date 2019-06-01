package irstit.transport.PhonePay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import irstit.transport.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JavaWallet extends Fragment {

    private RecyclerView navigation_Recycler = null;
    private List<TransctionModel> listme= new ArrayList<>();

       //TransctionRecycler re = null;

    public JavaWallet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_java_wallet, container, false);

        navigation_Recycler = view.findViewById(R.id.TransctionRecyclerjava);

        TransctionModel  ob1 = new TransctionModel();
        ob1.setCheckImage("image0");
        ob1.setTransctionNumber("تراکنش شماره یک");
        ob1.setPrice(" 5000 تومان");
        ob1.setDate(" 1398/02/3");

        TransctionModel  ob2 = new TransctionModel();
        ob2.setCheckImage("image1");
        ob2.setTransctionNumber("تراکنش شماره دو");
        ob2.setPrice("10000 تومان");
        ob2.setDate("1398/12/5");

        TransctionModel  ob3 = new TransctionModel();
        ob3.setCheckImage("image2");
        ob3.setTransctionNumber("تراکنش شماره سه");
        ob3.setPrice("90000 تومان");
        ob3.setDate("1398/12/25");



        listme.add(ob1);
        listme.add(ob2);
        listme.add(ob3);

        navigation_Recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        navigation_Recycler.setAdapter(new TransctionRecycler(listme,getActivity()));


        // Inflate the layout for this fragment

        return view;
    }

}
