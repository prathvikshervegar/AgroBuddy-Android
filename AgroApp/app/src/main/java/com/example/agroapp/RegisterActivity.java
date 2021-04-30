package com.example.agroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ProgressBar progressBar;

    private long backPressedTime;
    private Toast backToast;

    private EditText e1, e2, e3;
    private RadioGroup rg;
    private RadioButton r1,r2;
    private RadioButton radioSelected;
    private Button registerbtn;
    private TextView alreadyuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        e1 = (EditText) findViewById(R.id.registeruser);
        e2 = (EditText) findViewById(R.id.registeremail);
        e3 = (EditText) findViewById(R.id.registerpass);
        rg = (RadioGroup) findViewById(R.id.radiogroup);
        r1 = (RadioButton) findViewById(R.id.farmer);
        r2 = (RadioButton) findViewById(R.id.supplier);
        alreadyuser = (TextView) findViewById(R.id.alreadyuserlink);
        registerbtn = (Button) findViewById(R.id.registerbtn);

        progressBar = (ProgressBar) findViewById(R.id.registerprogress);
        progressBar.setVisibility(View.GONE);

        mAuth=FirebaseAuth.getInstance();

        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelected= (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                String username,usertype,email,pass;
                if(radioSelected==r1)
                    usertype="Farmer";
                else
                    usertype="Supplier";
                username=e1.getText().toString();
                email=e2.getText().toString();
                pass=e3.getText().toString();
                if(username.isEmpty()){
                    e1.setError("Please enter your full name");
                    e1.requestFocus();
                }
                else if(email.isEmpty()){
                    e2.setError("Please enter EmailId");
                    e2.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    e2.setError("Please enter valid EmailId");
                    e2.requestFocus();
                }
                else if(pass.isEmpty()){
                    e3.setError("Please enter password");
                    e3.requestFocus();
                }
                else if(username.isEmpty()&&email.isEmpty()&&pass.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Fields are empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!(username.isEmpty()&&email.isEmpty()&&pass.isEmpty())){
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Member member=new Member(username,email,usertype);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(member).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(),"Registration Successful, Please Login.",Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this,"Registration unsuccessful: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this,"Registration unsuccessful: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(RegisterActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
                }
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
            backToast=Toast.makeText(RegisterActivity.this,"Press back again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime=System.currentTimeMillis();
    }
}