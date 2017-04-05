package com.home.rhounsell.example.fbloginexample.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.rhounsell.example.fbloginexample.R;
import com.home.rhounsell.example.fbloginexample.datasource.UserDataSource;
import com.home.rhounsell.example.fbloginexample.model.User;

/**
 * Created by RHounsell on 04/04/2017.
 */

public class HelloFragment extends Fragment {
    private UserDataSource uDS;
    private long userId;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle userBundle = getArguments();
        userId =(Long) userBundle.get("user_id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello_layout,container, false);
        uDS= new UserDataSource(getActivity());
        uDS.open();

        User user = uDS.getUser(userId);

        TextView userName = (TextView) view.findViewById(R.id.user_name);
        userName.setText(user.getName());



        return view;
    }
}
