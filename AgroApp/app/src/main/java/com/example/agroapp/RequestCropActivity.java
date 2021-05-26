package com.example.agroapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestCropActivity extends AppCompatActivity {

    private EditText e1, e2;
    private AutoCompleteTextView sp1;
    private ArrayAdapter<String> a1;
    private Button submit;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_crop);

        e1 = (EditText) findViewById(R.id.requestcropname);
        e2 = (EditText) findViewById(R.id.requestcropquantity);
        submit = (Button) findViewById(R.id.requestcropsubmit);
        sp1 = (AutoCompleteTextView) findViewById(R.id.requestcroptype);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Requests");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        String buyerid,buyername,buyermobile,buyeraddress,cropname,croptype,quantity,requestdate,acceptstatus;
                        buyerid= dataSnapshot.getKey();
                        buyername=dataSnapshot.child("name").getValue(String.class);
                        buyermobile=dataSnapshot.child("mobile").getValue(String.class);
                        buyeraddress=dataSnapshot.child("address").getValue(String.class);
                        cropname=e1.getText().toString();
                        croptype=sp1.getText().toString();
                        quantity=e2.getText().toString();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        requestdate= dateFormat.format(date);
                        acceptstatus= "FALSE";

                        if(cropname.isEmpty()){
                            e1.setError("Commodity name cannot be empty");
                            e1.requestFocus();
                        }
                        else if(quantity.isEmpty()){
                            e2.setError("Quantity cannot be empty");
                            e2.requestFocus();
                        }
                        else if(cropname.isEmpty()&&quantity.isEmpty()){
                            Toast.makeText(RequestCropActivity.this,"Fields are empty!",Toast.LENGTH_SHORT).show();
                        }
                        else if(!(cropname.isEmpty()&&quantity.isEmpty())){
                            CropRequest crop=new CropRequest(cropname,croptype,quantity,buyername,buyermobile,buyeraddress,buyerid,requestdate,acceptstatus);
                            dbRef.push().setValue(crop);
                            Toast.makeText(RequestCropActivity.this,"Request successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RequestCropActivity.this,RequestCropActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(RequestCropActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RequestCropActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onResume() {
        super.onResume();

        a1 = new ArrayAdapter<>(this,R.layout.dropdown_item,getResources().getStringArray(R.array.crop_type));
        sp1.setText(a1.getItem(0).toString(),false);
        sp1.setAdapter(a1);
    }
}