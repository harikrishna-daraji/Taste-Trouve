package com.example.tastetrouvedriver.Helper.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouvedriver.Helper.APIClient;
import com.example.tastetrouvedriver.Helper.Model.PastOrderModel;
import com.example.tastetrouvedriver.MyEarningsActivity;
import com.example.tastetrouvedriver.PastOrdersAdapter;
import com.example.tastetrouvedriver.R;
import com.example.tastetrouvedriver.SIgnInActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    RecyclerView addressRecycle;
    LinearLayout myEarnings,myOrders, manageProfileLinear,logoutLInear;
    Switch themeSwitch;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor  editor;

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
        View view= inflater.inflate(R.layout.settings_fragment_xml, container, false);
        myEarnings=view.findViewById(R.id.myEarnings);
        logoutLInear=view.findViewById(R.id.logoutLinear);
        logoutLInear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });
        myEarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyEarningsActivity.class);
                getActivity().startActivity(intent);

            }
        });

        return  view;
    }

    private void logoutDialog() {

        try {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            dialog.dismiss();



                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("ownerId","");

                            editor.clear();
                            editor.apply();
                            Intent intent = new Intent(getContext(), SIgnInActivity.class);
                            startActivity(intent);
                            getActivity().finishAffinity();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(getResources()
                    .getString(R.string.logout_warning_messagae))
                    .setPositiveButton(R.string.yes, dialogClickListener)
                    .setNegativeButton(R.string.no, dialogClickListener)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
