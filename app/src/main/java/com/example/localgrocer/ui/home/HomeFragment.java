package com.example.localgrocer.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.localgrocer.GetLocationActivity;
import com.example.localgrocer.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel; //AIzaSyAAEBVkdX8-p0Cge3NRCeqgay__tz65roA
    private ImageView goToMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        goToMap = root.findViewById(R.id.go_to_map);
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Okay",Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getActivity(), GetLocationActivity.class));
            }
        });

//
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
