package irstit.transport.Drivers.Login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import irstit.transport.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetPhone extends Fragment implements View.OnClickListener {


    public GetPhone() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_phone, container, false);

        Button sendInfo = view.findViewById(R.id.GetPhone_Btn);
        sendInfo.setOnClickListener(this);

        return view;    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.GetPhone_Btn) {
            //after getting phone number we must going to GetSms Class
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("GetSms");
            transaction.replace(R.id.Toolbar_Frame, new GetSms());
            transaction.commit();
        }
    }
}
