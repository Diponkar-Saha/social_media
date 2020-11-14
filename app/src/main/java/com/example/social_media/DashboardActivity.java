package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.social_media.ui.HomeFragment;
import com.example.social_media.ui.ProfileFragment;
import com.example.social_media.ui.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        auth = FirebaseAuth.getInstance();


        auth=FirebaseAuth.getInstance();
        BottomNavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        //default

        HomeFragment homeFragment=new HomeFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,homeFragment,"");
        transaction.commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_home:

                            HomeFragment homeFragment=new HomeFragment();
                            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.content,homeFragment,"");
                            transaction.commit();
                            return true;
                        case R.id.nav_profile:

                            ProfileFragment profileFragment=new ProfileFragment ();
                            FragmentTransaction transaction1=getSupportFragmentManager().beginTransaction();
                            transaction1.replace(R.id.content,profileFragment,"");
                            transaction1.commit();
                            return true;
                        case R.id.nav_users:

                            UsersFragment usersFragment=new UsersFragment ();
                            FragmentTransaction transaction2=getSupportFragmentManager().beginTransaction();
                            transaction2.replace(R.id.content,usersFragment,"");
                            transaction2.commit();

                            return true;
                    }
                    return false;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
            }

        }
        return super.onOptionsItemSelected(item);
    }
    private  void Logout(){
        auth.signOut();
        finish();
        startActivity(new Intent(DashboardActivity.this,MainActivity.class));


    }
}