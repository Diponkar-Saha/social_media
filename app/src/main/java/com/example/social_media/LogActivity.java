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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LogActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN=100;
    private EditText memail,mpass;
    private Button msign;
    private TextView msignUp,forgot;
    private ProgressBar progressbar;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    SignInButton msignInButton;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("231507815310-kreubc9r7clp6kvdo1vlc8hv9ue63rec.apps.googleusercontent.com")
                .requestEmail()
                .build();





        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);

        memail=findViewById(R.id.emailET);
        mpass=findViewById(R.id.passwordET);
        msign=findViewById(R.id.signinBtn);
        msignUp=findViewById(R.id.signupTV);
        mAuth = FirebaseAuth.getInstance();
        progressbar=findViewById(R.id.progressbar);
        msignInButton=findViewById(R.id.googlelogin);
        forgot=findViewById(R.id.forgot);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        progressbar.setVisibility(View.INVISIBLE);

        msign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                }
        });
        msignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

        msignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogActivity.this, RegisterActivity.class));
                finish();

            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogActivity.this, ResetPasswordActivity.class));
                finish();

            }
        });



    }
    private void createUser(){
        final String email=memail.getText().toString().trim();
        final String password=mpass.getText().toString().trim();
        if(email.isEmpty()){
            memail.setError("Email Required");
            memail.requestFocus();
            memail.setFocusable(true);
            return;
        }
        if(password.isEmpty()){
            mpass.setError("Password Required");
            mpass.requestFocus();
            mpass.setFocusable(true);
            return;
        }
        if(password.length()<6){
            mpass.setError("Password Should be atleast six character");
            mpass.requestFocus();
            mpass.setFocusable(true);
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressbar.setVisibility(View.VISIBLE);

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LogActivity.this,"Login Success ",Toast.LENGTH_SHORT).show();
                            startProfileActivity();

                        } else {

                            progressbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LogActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }
    private void startProfileActivity(){
        Intent intent =new Intent(this, DashboardActivity.class);
        startActivity(intent);

    }

   /* private void sendUserdata(){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(mAuth.getUid());
        UserProfile userProfile=new UserProfile(age,name,email);
        myRef.setValue(userProfile);

    }*/
   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

       // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
       if (requestCode == RC_SIGN_IN) {
           Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
           try {
               // Google Sign In was successful, authenticate with Firebase
               GoogleSignInAccount account = task.getResult(ApiException.class);
               //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
               firebaseAuthWithGoogle(account.getIdToken());
           } catch (ApiException e) {
               // Google Sign In failed, update UI appropriately
               //Log.w(TAG, "Google sign in failed", e);
               // ...
           }
       }
   }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                //FirebaseUser user = mAuth.getCurrentUser();
                                String email=user.getEmail();
                                String uid=user.getUid();
                                HashMap<Object,String> hashMap=new HashMap<>();
                                hashMap.put("email",email);
                                hashMap.put("uid",uid);
                                hashMap.put("name","");
                                hashMap.put("onlineStatus","online");
                                hashMap.put("typingTo","noOne");
                                hashMap.put("phone","");
                                hashMap.put("image","");
                                hashMap.put("cover","");
                                FirebaseDatabase database=FirebaseDatabase.getInstance();

                                DatabaseReference reference=database.getReference("Users");
                                reference.child(uid).setValue(hashMap);

                            }
                            //updateUI(user);
                            Toast.makeText(LogActivity.this,"Login Success ",Toast.LENGTH_SHORT).show();
                            startProfileActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LogActivity.this,"google login failed ",Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                });
    }


    }
