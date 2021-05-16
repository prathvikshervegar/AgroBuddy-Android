package com.example.agroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SellCropActivity extends AppCompatActivity {

    private EditText e1, e2, e3;
    private Spinner sp;
    private Button submit;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_crop);

        e1 = (EditText) findViewById(R.id.sellcropname);
        e2 = (EditText) findViewById(R.id.sellcropquantity);
        e3 = (EditText) findViewById(R.id.sellcropprice);
        sp = (Spinner) findViewById(R.id.sellcroptype);
        submit = (Button) findViewById(R.id.sellcropsubmit);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Crops");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        String farmerid,farmername,farmermobile,cropname,croptype,quantity,price,selldate;
                        farmerid= dataSnapshot.getKey();
                        farmername=dataSnapshot.child("name").getValue(String.class);
                        farmermobile=dataSnapshot.child("mobile").getValue(String.class);
                        cropname=e1.getText().toString();
                        croptype=sp.getSelectedItem().toString();
                        quantity=e2.getText().toString();
                        price=e3.getText().toString();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        selldate= dateFormat.format(date);

                        CropSell crop=new CropSell(cropname,croptype,quantity,quantity,price,farmername,farmermobile,farmerid,selldate);
                        dbRef.push().setValue(crop);
                    }
                });

                Toast.makeText(SellCropActivity.this,"Crop has been listed",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SellCropActivity.this,SellCropActivity.class));
                finish();
            }
        });

    }
}