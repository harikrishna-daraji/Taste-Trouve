package com.example.tastetrouverestaurantowner.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tastetrouverestaurantowner.Activity.AcceptedOrderActivity;
import com.example.tastetrouverestaurantowner.Activity.AddItem;
import com.example.tastetrouverestaurantowner.Activity.DeliveredOrdersActivity;
import com.example.tastetrouverestaurantowner.Activity.DriversActivity;
import com.example.tastetrouverestaurantowner.Activity.PendingOrdersActivity;
import com.example.tastetrouverestaurantowner.Activity.ReportActivity;
import com.example.tastetrouverestaurantowner.Activity.ViewItemsActivity;
import com.example.tastetrouverestaurantowner.R;


public class HomeFragment extends Fragment {

  LinearLayout createItem,viewItems,pendingOrder,deliveredOrders,acceptedactivity,reportlayout;

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
        pendingOrder=(LinearLayout)view.findViewById(R.id.pendingOrder);
        deliveredOrders=(LinearLayout)view.findViewById(R.id.deliveredOrders);
        acceptedactivity=(LinearLayout)view.findViewById(R.id.acceptedactivity);
        reportlayout=(LinearLayout)view.findViewById(R.id.reportlayout);

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

        pendingOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PendingOrdersActivity.class);
                getActivity().startActivity(intent);
            }
        });


        deliveredOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DeliveredOrdersActivity.class);
                getActivity().startActivity(intent);
            }
        });


        acceptedactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AcceptedOrderActivity.class);
                getActivity().startActivity(intent);
            }
        });

        reportlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReportActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return  view;
    }
}