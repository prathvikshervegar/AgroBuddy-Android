package com.example.agroapp;

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
    private AutoCompleteTextView sp;
    private ArrayAdapter arrayAdapter;
    private Button submit;

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_crop);

        e1 = (EditText) findViewById(R.id.sellcropname);
        e2 = (EditText) findViewById(R.id.sellcropquantity);
        e3 = (EditText) findViewById(R.id.sellcropprice);
        submit = (Button) findViewById(R.id.sellcropsubmit);
        sp = (AutoCompleteTextView) findViewById(R.id.sellcroptype);

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
                        croptype=sp.getText().toString();
                        quantity=e2.getText().toString();
                        price=e3.getText().toString();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        selldate= dateFormat.format(date);

                        if(cropname.isEmpty()){
                            e1.setError("Commodity name cannot be empty");
                            e1.requestFocus();
                        }
                        else if(quantity.isEmpty()){
                            e2.setError("Quantity cannot be empty");
                            e2.requestFocus();
                        }
                        else if(Integer.parseInt(quantity)<=0){
                            e2.setError("Quantity must be greater than zero");
                            e2.requestFocus();
                        }
                        else if(price.isEmpty()){
                            e3.setError("Cost cannot be empty");
                            e3.requestFocus();
                        }
                        else if(cropname.isEmpty()&&quantity.isEmpty()&&price.isEmpty()){
                            Toast.makeText(SellCropActivity.this,"Fields are empty!",Toast.LENGTH_SHORT).show();
                        }
                        else if(!(cropname.isEmpty()&&quantity.isEmpty()&&Integer.parseInt(quantity)<=0&&price.isEmpty())){
                            CropSell crop=new CropSell(cropname,croptype,quantity,quantity,price,farmername,farmermobile,farmerid,selldate);
                            dbRef.push().setValue(crop);
                            Toast.makeText(SellCropActivity.this,"Crop has been listed",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SellCropActivity.this,SellCropActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(SellCropActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onResume() {
        super.onResume();
        arrayAdapter = new ArrayAdapter(this,R.layout.dropdown_item,getResources().getStringArray(R.array.crop_type));
        sp.setText(arrayAdapter.getItem(0).toString(),false);
        sp.setAdapter(arrayAdapter);
    }
}