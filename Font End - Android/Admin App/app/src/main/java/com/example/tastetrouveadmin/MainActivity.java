package com.example.tastetrouveadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ApiInterface api;
    ArrayList<UData> uDataArrayList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Call<List<UData>> call = APIClient.getInstance().getApi().getUser();

        call.enqueue(new Callback<List<UData>>() {
            @Override
            public void onResponse(Call<List<UData>> call, Response<List<UData>> response) {

                List<UData> heroList = response.body();


                uDataArrayList = new ArrayList<>();
                for(int i=0; i <heroList.size(); i++) {
                    uDataArrayList.add(new UData(heroList.get(i).getRestaurantName(),heroList.get(i).getEmail(),heroList.get(i).getPhoneNumber(),heroList.get(i).getAddress(),heroList.get(i).get_id()));

                }
                Log.i("TAG","TAG: Size "+heroList.size());
                UAdapter customAdapter= new UAdapter(uDataArrayList,MainActivity.this);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<UData>> call, Throwable t) {
                Log.d("aa", t.getMessage());
            }


        });

    }
}