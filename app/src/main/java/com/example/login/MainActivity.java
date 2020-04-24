package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    boolean singleBack = false;
    DrawerLayout mNavDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView  navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

      //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Menu1()).commit();

        Toolbar tbar=findViewById(R.id.toolbar);
        setSupportActionBar(tbar);

        mNavDrawer=(DrawerLayout)findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(
                this,mNavDrawer,tbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu:
                getSupportFragmentManager().beginTransaction()
                     .replace(R.id.fragment_container,new Menu1()).commit();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
        }
        mNavDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mNavDrawer.isDrawerOpen(GravityCompat.START)) {
            mNavDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (singleBack) {
                super.onBackPressed();
                return;
            }
            this.singleBack = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    singleBack = false;
                }
            }, 2000);
        }
    }
}