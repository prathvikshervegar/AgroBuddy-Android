package com.example.agroapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class BuyCropAdapter extends RecyclerView.Adapter<BuyCropAdapter.MyViewHolder> {

    Context context;
    ArrayList<CropBuy> list;

    public BuyCropAdapter(Context context, ArrayList<CropBuy> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.buy_crop_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CropBuy crop = list.get(position);
        holder.t1.setText(crop.getCropname());
        holder.t2.setText(crop.getQuantity());
        holder.t3.setText(crop.getPrice());
        holder.t4.setText(crop.getFarmername());
        holder.buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt = new EditText(v.getContext());
                edt.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                AlertDialog.Builder buyalertbuilder = new AlertDialog.Builder(v.getContext());
                buyalertbuilder.setCancelable(false)
                        .setTitle("Buy Crop")
                        .setMessage("Enter the quantity:")
                        .setView(edt)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ///
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog buyalert= buyalertbuilder.create();
                buyalert.show();
                buyalert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String qty = edt.getText().toString();
                        if(qty.isEmpty()){
                            edt.setError("Quantity cannot be empty");
                            edt.requestFocus();
                        }
                        else if(Integer.parseInt(qty)>Integer.parseInt(crop.getQuantity())){
                            edt.setError("Please check available quantity");
                            edt.requestFocus();
                        }
                        else{

                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                @Override
                                public void onSuccess(DataSnapshot dataSnapshot) {
                                    String newqty = String.valueOf(Integer.parseInt(crop.getQuantity()) - Integer.parseInt(qty));
                                    FirebaseDatabase.getInstance().getReference("Crops").child(crop.getCropid()).child("availablequantity").setValue(newqty);

                                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Trades");
                                    String amount = String.valueOf(Integer.parseInt(qty)*100*Integer.parseInt(crop.getPrice()));
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    String tradedate= dateFormat.format(date);
                                    String supplierid= dataSnapshot.getKey();
                                    String suppliername= dataSnapshot.child("name").getValue(String.class);
                                    String suppliermobile= dataSnapshot.child("mobile").getValue(String.class);

                                    CropTrade cropTrade = new CropTrade(crop.getCropname(),qty,amount,crop.getFarmername(),crop.getFarmermobile(),crop.getFarmerid(),suppliername,suppliermobile,supplierid,tradedate);
                                    dbRef.push().setValue(cropTrade);
                                    buyalert.dismiss();

                                    Toast.makeText(v.getContext(),qty+" quintal of "+crop.getCropname()+" is purchased\nPlease contact seller.",Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context,SupplierTradeActivity.class));
                                }
                            });
                        }
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView t1,t2,t3,t4;
        Button buybtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.buycropname);
            t2 = itemView.findViewById(R.id.buycropquantity);
            t3 = itemView.findViewById(R.id.buycropprice);
            t4 = itemView.findViewById(R.id.buycropcontact);
            buybtn = itemView.findViewById(R.id.buycropbtn);
        }
    }

}
