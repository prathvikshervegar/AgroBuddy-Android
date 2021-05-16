package com.example.agroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuyCropActivity extends AppCompatActivity {

    private Spinner s1;
    private RecyclerView r1;

    private DatabaseReference dbRef;
    BuyCropAdapter adapter;
    ArrayList<CropBuy> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_crop);

        s1 = (Spinner) findViewById(R.id.buycropspinner);
        r1 = (RecyclerView) findViewById(R.id.buycroplist);
        dbRef = FirebaseDatabase.getInstance().getReference("Crops");

        r1.setHasFixedSize(true);
        r1.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new BuyCropAdapter(this,list);
        r1.setAdapter(adapter);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String croptype = dataSnapshot.child("croptype").getValue(String.class);
                            String cropid = dataSnapshot.getKey();
                            String farmerid = dataSnapshot.child("farmerid").getValue(String.class);
                            String farmername = dataSnapshot.child("farmername").getValue(String.class);
                            String farmermobile = dataSnapshot.child("farmermobile").getValue(String.class);
                            String cropname = dataSnapshot.child("cropname").getValue(String.class);
                            String quantity = dataSnapshot.child("availablequantity").getValue(String.class);
                            String price = dataSnapshot.child("price").getValue(String.class);
                            if(croptype.equals(parent.getItemAtPosition(position).toString()) && Integer.parseInt(quantity)>0) {
                                CropBuy crop = new CropBuy(cropname, quantity, price, cropid, farmername,farmermobile,farmerid);
                                list.add(crop);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        ///
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ///
            }
        });
    }
}