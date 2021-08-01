package com.example.tastetrouve.Fragments;

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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouve.Activities.MapActivity;
import com.example.tastetrouve.Activities.MyOrdersActivity;
import com.example.tastetrouve.Activities.SignIn;
import com.example.tastetrouve.Adapters.AddressRecycleAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Interfaces.AddressInterface;
import com.example.tastetrouve.Models.AddressModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    private View root;
    RecyclerView addressRecycle;
    List<AddressModel> addressModelList = new ArrayList<>();
    LinearLayout addressListLinear,myOrders;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.setting_fragment_xml, container, false);
        initUI();
        getAddressList();
        return root;
    }

    private void initUI() {
        addressListLinear = root.findViewById(R.id.addressListLinear);
        myOrders = root.findViewById(R.id.myOrders);
        addressRecycle = root.findViewById(R.id.addressRecycle);
        addressRecycle.setLayoutManager(new LinearLayoutManager(getContext()));


        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyOrdersActivity.class);
                getActivity().startActivity(intent);
            }
        });

        root.findViewById(R.id.addNewAddressLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                getActivity().startActivityForResult(intent, GlobalObjects.MAP_REQUEST_CODE);
            }
        });

        root.findViewById(R.id.manageProfileLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        root.findViewById(R.id.manageAddressLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addressListLinear.getVisibility() == View.VISIBLE) {
                    addressListLinear.setVisibility(View.GONE);
                } else {
                    addressListLinear.setVisibility(View.VISIBLE);
                }
            }
        });
        root.findViewById(R.id.logoutLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });

    }

    private void logoutDialog() {
        try {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            dialog.dismiss();



                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("AuthenticationTypes", getContext().MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token","");
                            editor.putBoolean("signUpDone",false);
                            editor.putString("phone","");
                            editor.clear();
                            editor.apply();
                            Intent intent = new Intent(getContext(), SignIn.class);
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

    private String getUserToken() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("AuthenticationTypes",getContext().MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("signUpDone",false);
        if(isLoggedIn) {
            String token = sharedPreferences.getString("token","");
            return token;
        } else {
            return "";
        }
    }

    private void getAddressList() {
        addressModelList.clear();
        addressRecycle.setAdapter(new AddressRecycleAdapter(getActivity(),addressModelList));
        String token = getUserToken();
        if(!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getAddressList(token).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                            if(response.code() == 200) {
                                JSONArray jsonArray = new JSONArray(response.body().string());
                                for(int index=0;index<jsonArray.length();index++) {
                                    AddressModel addressModel = new AddressModel(jsonArray.getJSONObject(index));
                                    addressModelList.add(addressModel);
                                }
                                if(addressModelList.size() > 0) {
                                    addressRecycle.setAdapter(new AddressRecycleAdapter(getActivity(),addressModelList));
                                }
                            }
                        } catch (Exception ex) {
                            Log.i("TAG","TAG: Exception: "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("TAG","TAG:  Server Failure: "+t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG","TAG Exception: "+ex.getMessage());
            }
        }
    }

    public void deleteAddress(String address) {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.deleteAddress(address).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if(response.code() == 200) {
                            getAddressList();
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG ");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i("TAG","TAG "+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == GlobalObjects.MAP_REQUEST_CODE && data.getBooleanExtra("addressStored",false)) {
            getAddressList();
        }
    }
}
