package irstit.transport.Views;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import irstit.transport.R;

public class MyDialogManager extends DialogFragment {
    public static MyDialogManager newInstance(int myIndex) {
        MyDialogManager yourDialogFragment = new MyDialogManager();

        //example of passing args
        Bundle args = new Bundle();
        args.putInt("anIntToSend", myIndex);
        yourDialogFragment.setArguments(args);

        return yourDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //read the int from args
        int myInteger = getArguments().getInt("anIntToSend");

        View view = inflater.inflate(R.layout.layout_change_pass, null);

        //here read the different parts of your layout i.e :
        //tv = (TextView) view.findViewById(R.id.yourTextView);
        //tv.setText("some text")

        return view;
    }
}
