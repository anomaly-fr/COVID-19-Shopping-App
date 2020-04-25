package com.example.localgrocer;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.localgrocer.ui.Consumer.ConsumerFragment;
import com.example.localgrocer.ui.MyProfile.MyProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ConsumerActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private  DrawerLayout drawer;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity = this;
        //FloatingActionButton fab = findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_consumer_fragment, R.id.nav_requestshop_fragment, R.id.nav_manage_shop_fragment, R.id.nav_my_profile_fragment )
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                menuItem.setChecked(true);
                drawer.closeDrawers();

                switch (id) {
                    case R.id.nav_my_profile:
                        /*MyProfileFragment myProfileFragment = new MyProfileFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.host_frame, myProfileFragment, myProfileFragment.getTag()).commit();
                        setTitle(menuItem.getTitle());*/
                        navController.navigate(R.id.nav_my_profile_fragment);
                        break;
                    case R.id.nav_consumer:
                        navController.navigate(R.id.nav_consumer_fragment);
                        break;
                    case R.id.nav_requestshop:
                        navController.navigate(R.id.nav_requestshop_fragment);
                        break;
                    case R.id.nav_manageshop:
                        navController.navigate(R.id.nav_manage_shop_fragment);
                        break;
                    case R.id.nav_contact:
                        menuItem.setChecked(false);
                        break;
                    case R.id.nav_log_out:
                        menuItem.setChecked(false);
                        break;
                }

                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        //navigationView.getMenu().findItem(R.id.nav_requestshop).setVisible(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.consumer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        menuItem.setChecked(true);
        drawer.closeDrawers();

        switch (id) {
            case R.id.nav_my_profile:

                MyProfileFragment myProfileFragment = new MyProfileFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_my_profile_fragment, myProfileFragment, myProfileFragment.getTag()).commit();
                drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }*/
}
