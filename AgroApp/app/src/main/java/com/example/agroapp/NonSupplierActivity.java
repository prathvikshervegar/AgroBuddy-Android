package com.example.agroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NonSupplierActivity extends AppCompatActivity {

    private TextView t1;
    private LinearLayout l1, l2, logout;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_supplier);

        t1=(TextView)findViewById(R.id.welcomesupplier);
        l1=(LinearLayout) findViewById(R.id.traderequest);
        l2=(LinearLayout) findViewById(R.id.tradehistorynonsupplier);
        logout=(LinearLayout) findViewById(R.id.nonsupplierlogout);

        DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dbRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String username=dataSnapshot.child("name").getValue(String.class);
                t1.setText("Hello, "+username);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ///
            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NonSupplierActivity.this,RequestCropActivity.class));
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NonSupplierActivity.this,NonSupplierTradeActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(NonSupplierActivity.this,"Logged out successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NonSupplierActivity.this,MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(backPressedTime+2000>System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            backToast=Toast.makeText(NonSupplierActivity.this,"Press back again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime=System.currentTimeMillis();
    }
}