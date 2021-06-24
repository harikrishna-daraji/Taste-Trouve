package com.example.tastetrouverestaurantowner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tastetrouverestaurantowner.Activity.AddItem;
import com.example.tastetrouverestaurantowner.Activity.ViewItemsActivity;


public class HomeFragment extends Fragment {

  LinearLayout createItem,viewItems;

    public HomeFragment() {
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
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        createItem=(LinearLayout)view.findViewById(R.id.createItem);
        viewItems=(LinearLayout)view.findViewById(R.id.viewItems);
        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddItem.class);
                getActivity().startActivity(intent);
            }
        });

        viewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ViewItemsActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return  view;
    }
}