package com.example.tastetrouverestaurantowner.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tastetrouverestaurantowner.Activity.UpdateActivity;
import com.example.tastetrouverestaurantowner.R;


public class SettingsFragment extends Fragment {

LinearLayout manageProfileLinear;
    public SettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_settings, container, false);
        manageProfileLinear=view.findViewById(R.id.manageProfileLinear);
        manageProfileLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), UpdateActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return  view;
    }
}