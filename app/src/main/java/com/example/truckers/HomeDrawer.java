package com.example.truckers;

import android.content.Intent;
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

        auth = FirebaseAuth.getInstance();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    public void showViewAlarms(View v){
        Intent intent = new Intent(HomeDrawer.this, AlarmActivity.class);
        startActivity(intent);
    }

    public void showViewMaps(View v){
        Intent intent = new Intent(HomeDrawer.this, MapsActivity.class);
        startActivity(intent);
    }
}
