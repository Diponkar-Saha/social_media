package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText memail,mpass;
    private Button signUpBtn;
    private TextView signInTV;
    private ProgressBar progressbar;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        memail=findViewById(R.id.emailET);
        mpass=findViewById(R.id.passwordET);
        signUpBtn=findViewById(R.id.signUpBtn);
        signInTV=findViewById(R.id.signInTV);
        mAuth = FirebaseAuth.getInstance();
        progressbar=findViewById(R.id.progressbar);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);


        progressbar.setVisibility(View.INVISIBLE);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });




    }

    private void createUser(){
        final String email=memail.getText().toString().trim();
        final String password=mpass.getText().toString().trim();
        if(email.isEmpty()){
            memail.setError("Email Required");
            memail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            mpass.setError("Password Required");
            mpass.requestFocus();
            return;
        }
        if(password.length()<6){
            mpass.setError("Password Should be atleast six character");
            mpass.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressbar.setVisibility(View.VISIBLE);

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email=user.getEmail();
                            String uid=user.getUid();
                            HashMap<Object,String> hashMap=new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("name","");
                            hashMap.put("phone","");
                            hashMap.put("image","");
                            hashMap.put("cover","");
                            FirebaseDatabase database=FirebaseDatabase.getInstance();

                            DatabaseReference reference=database.getReference("Users");
                            reference.child(uid).setValue(hashMap);
                            startProfileActivity();
                            Toast.makeText(RegisterActivity.this,"Wellcome  "+user.getEmail(),Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.

                                progressbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    private void startProfileActivity(){
        Intent intent =new Intent(this, DashboardActivity.class);
        startActivity(intent);

    }
}