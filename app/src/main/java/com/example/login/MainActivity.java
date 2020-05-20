package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean singleBack = false;
    DrawerLayout mNavDrawer;
    TextView tname,home;
    ImageView IVphoto;
    FirebaseAuth FAuth;
    FirebaseFirestore FStore;
    String UserId;
    View hview;
    String Name;
    Button location;
    FusedLocationProviderClient fusedLocationProviderClient;
   LocationRequest locationRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();




       fusedLocationProviderClient = getFusedLocationProviderClient(this);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check permission
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //when permission granted
                    getLocation();
                } else {
                    //when permission denied
                    ActivityCompat.requestPermissions(MainActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        //Initializing photo and name from DB.
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        hview=navigationView.getHeaderView(0);
        tname=(TextView)hview.findViewById(R.id.name);
        IVphoto=(ImageView)hview.findViewById(R.id.photo);

        //Displaying name from firebase in nav_header.
        DocumentReference documentRef= FStore.collection("Users").document("UserId" );
       documentRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Name=documentSnapshot.getString("Fname");
                tname.setText(Name); } });

        //Removing App Name from Toolbar
        Toolbar tbar = findViewById(R.id.toolbar);
        //setSupportActionBar(tbar);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        tbar.setLogo(R.drawable.locatio_icon);
       // tbar.setTitle("");
        //tbar.setSubtitle("");


        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(
                this, mNavDrawer, tbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void init() {
        //  for Location
        home=(TextView)findViewById(R.id.city);
        location = (Button) findViewById(R.id.loc);
        //For Firebase Authentication
        FAuth=FirebaseAuth.getInstance();
        FStore=FirebaseFirestore.getInstance();
        UserId=FAuth.getCurrentUser().getUid();
        //Navigation Drawer
        mNavDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);


    }

    private void getLocation(){
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location local=task.getResult();
                if(local!=null){
                    try {
                        //Initialize geoCoder
                        Geocoder geocoder = new Geocoder(MainActivity.this,
                                Locale.getDefault());
                        //Initialize address List
                        List<Address> addresses = geocoder.getFromLocation(
                                local.getLatitude(), local.getLongitude(), 1
                        );
                        // Set Locality on TextView
                    home.setText(Html.fromHtml(
                            "<font color='#6200EE' ><b>Location :</b></br></font>"
                            +addresses.get(0).getAddressLine(0)
                    ));
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

//On Menu Item Selected In NavigationBar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new Menu1()).addToBackStack("MainActivity").commit();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
        }
        mNavDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // On Back Pressed
    @Override
    public void onBackPressed()
    {
        if (mNavDrawer.isDrawerOpen(GravityCompat.START)) {
            mNavDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0)
                getFragmentManager().popBackStack();

            else
                super.onBackPressed();
        }
    }
}