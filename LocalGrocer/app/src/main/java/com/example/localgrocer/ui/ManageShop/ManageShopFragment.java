package com.example.localgrocer.ui.ManageShop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.localgrocer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManageShopFragment extends Fragment {
    private Fragment childFragment;
    private FragmentTransaction transaction;

    private BottomNavigationView bottomNavigationView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_manageshop, container, false);
        bottomNavigationView = root.findViewById(R.id.manageshop_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.manageshop_item_list:
                        childFragment = new ManageShop_ItemList_Fragment();
                        transaction = getChildFragmentManager().beginTransaction();
                        transaction.replace(R.id.manageshop_framelayout, childFragment).commit();
                        break;
                    case R.id.manageshop_pending_orders:
                        childFragment = new ManageShop_PendingOrders_Fragment();
                        transaction = getChildFragmentManager().beginTransaction();
                        transaction.replace(R.id.manageshop_framelayout, childFragment).commit();
                        break;
                    case R.id.manageshop_completed_orders:
                        childFragment = new ManageShop_CompletedOrders_Fragment();
                        transaction = getChildFragmentManager().beginTransaction();
                        transaction.replace(R.id.manageshop_framelayout, childFragment).commit();
                        break;
                }
                return true;
            }
        });

        //Set Pending Orders as Default Page
        childFragment = new ManageShop_PendingOrders_Fragment();
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.manageshop_framelayout, childFragment).commit();
        bottomNavigationView.getMenu().findItem(R.id.manageshop_pending_orders).setChecked(true);

        return root;
    }
}
