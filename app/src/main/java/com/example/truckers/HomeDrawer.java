package com.example.truckers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.truckers.view.ui.MapsActivity;
import com.example.truckers.view.ui.RegisterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.FrameLayout;

public class HomeDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static AppBarConfiguration mAppBarConfiguration;

    public DrawerLayout drawerLayout;

    /*public FrameLayout frameLayout;

    public NavigationView navigationView;*/

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton llamada = (FloatingActionButton) findViewById(R.id.fab);

        llamada.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View arg0) {
                                           String number = "123";
                                           Intent callIntent = new Intent(Intent.ACTION_CALL);
                                           callIntent.setData(Uri.parse("tel:" + number));
                                           startActivity(callIntent);
                                       }
                                   });


            auth = FirebaseAuth.getInstance();

        /*frameLayout = (FrameLayout) findViewById(R.id.nav_view);*/

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_routes,
                R.id.nav_profile, R.id.nav_alerts, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
        if (ContextCompat.checkSelfPermission(HomeDrawer.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeDrawer.this, new String[]{Manifest.permission.CALL_PHONE},1);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_routes:
                startActivity(new Intent(HomeDrawer.this, MapsActivity.class));
                return true;
            case R.id.nav_alerts:
                startActivity(new Intent(HomeDrawer.this, AlarmActivity.class));
                return true;

            case R.id.nav_logout:
                auth.signOut();
                Intent intent = new Intent(HomeDrawer.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }

    }



    public void callEmergency(View v){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+123));//change the number
        startActivity(callIntent);
    }

    public void showViewAlarms(View v){
        Intent intent = new Intent(HomeDrawer.this, AlarmActivity.class);
        startActivity(intent);
    }

    public void showViewMaps(View v){
        Intent intent = new Intent(HomeDrawer.this, MapsActivity.class);
        startActivity(intent);
    }
}
