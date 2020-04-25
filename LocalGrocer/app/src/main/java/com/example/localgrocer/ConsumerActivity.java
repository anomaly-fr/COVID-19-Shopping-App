package com.example.localgrocer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
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
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity = this;
        context = this;
        //FloatingActionButton fab = findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //Contact Us AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View customLayout = getLayoutInflater().inflate(R.layout.contact_us_alertdialog_layout, null);
        builder.setView(customLayout);
        builder.setCancelable(true);
        Button contact_team = customLayout.findViewById(R.id.contact_us_send_email);
        final EditText message_body = customLayout.findViewById(R.id.contact_us_message_body);
        contact_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(message_body.getText().toString().trim());
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
                message_body.setText("");
            }
        });


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
                        dialog.show();
                        //sendEmail();
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

   public void sendEmail(String body){
       String[] to = {"pcshopapp@gmail.com"};
       Intent emailIntent = new Intent(Intent.ACTION_SEND);
       emailIntent.setData(Uri.parse("mailto:"));
       //emailIntent.setType("text/plain");
       emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
       emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Local Grocer Query - " + "Phone Number");
       emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Local Grocer Team,\n\n" + body + "\n\nThanks and Regards,\n" + "Name");
       emailIntent.setType("message/rfc822");
       try {
           startActivity(Intent.createChooser(emailIntent, "Contact the Local Grocer Team"));
           //finish();
       } catch (android.content.ActivityNotFoundException ex) {
           Toast.makeText(activity, "There is no email client installed.", Toast.LENGTH_SHORT).show();
       }
   }
}
