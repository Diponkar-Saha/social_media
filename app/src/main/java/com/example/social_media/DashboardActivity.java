package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.social_media.ui.ChatListFragment;
import com.example.social_media.ui.HomeFragment;
import com.example.social_media.ui.ProfileFragment;
import com.example.social_media.ui.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");



        firebaseAuth = FirebaseAuth.getInstance();


        firebaseAuth =FirebaseAuth.getInstance();
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
                            actionBar.setTitle("Home");

                            HomeFragment homeFragment=new HomeFragment();
                            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.content,homeFragment,"");
                            transaction.commit();
                            return true;
                        case R.id.nav_profile:
                            actionBar.setTitle("Profile");

                            ProfileFragment profileFragment=new ProfileFragment ();
                            FragmentTransaction transaction1=getSupportFragmentManager().beginTransaction();
                            transaction1.replace(R.id.content,profileFragment,"");
                            transaction1.commit();
                            return true;
                        case R.id.nav_users:
                            actionBar.setTitle("Users");

                            UsersFragment usersFragment=new UsersFragment ();
                            FragmentTransaction transaction2=getSupportFragmentManager().beginTransaction();
                            transaction2.replace(R.id.content,usersFragment,"");
                            transaction2.commit();

                            return true;

                        case R.id.nav_chat:

                            actionBar.setTitle("Chat");
                            ChatListFragment chatListFragment=new ChatListFragment ();
                            FragmentTransaction transaction3=getSupportFragmentManager().beginTransaction();
                            transaction3.replace(R.id.content,chatListFragment,"");
                            transaction3.commit();

                            return true;
                    }
                    return false;
                }
            };


}