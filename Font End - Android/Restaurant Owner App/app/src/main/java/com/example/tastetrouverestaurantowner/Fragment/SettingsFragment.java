package com.example.tastetrouverestaurantowner.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tastetrouverestaurantowner.Activity.LogInActivity;
import com.example.tastetrouverestaurantowner.Activity.UpdateActivity;
import com.example.tastetrouverestaurantowner.R;


public class SettingsFragment extends Fragment {

LinearLayout manageProfileLinear,logoutLinear;
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
        logoutLinear=view.findViewById(R.id.logoutLinear);
        manageProfileLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), UpdateActivity.class);
                getActivity().startActivity(intent);
            }
        });

        logoutLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
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
                            editor.putBoolean("signUpDone",false);
                            editor.clear();
                            editor.apply();
                            Intent intent = new Intent(getContext(), LogInActivity.class);
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