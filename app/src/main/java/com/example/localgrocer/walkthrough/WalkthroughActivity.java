package com.example.localgrocer.walkthrough;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.localgrocer.LoginActivity;
import com.example.localgrocer.R;
import com.viewpagerindicator.CirclePageIndicator;

public class WalkthroughActivity extends AppCompatActivity {

    ViewPager mVPWalkThrough;
    CirclePageIndicator mIndicator;
    CustomAdapter mCustomAdapter;
    Button mWalkthroughButton;
    private int mResources[]={R.drawable.ic_walkthroughimage_1,R.drawable.ic_walkthroughimage_1,R.drawable.ic_walkthroughimage_1};
    private int[] mTextName={R.string.walkthrough_text_title1, R.string.walkthrough_text_title2,R.string.walkthrough_text_title3};
    private  int[] mTextBody={R.string.walkthrough_text_body1,R.string.walkthrough_text_body2,R.string.walkthrough_text_body3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        mCustomAdapter=new CustomAdapter(this);
        initViews();
        mWalkthroughButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent loginactivity=new Intent(WalkthroughActivity.this, LoginActivity.class);
               startActivity(loginactivity);
               finish();
            }
        });
    }

    private void initViews() {//Initializing view
        mVPWalkThrough=(ViewPager) findViewById(R.id.xVPWalkThrough);
        mIndicator=(CirclePageIndicator) findViewById(R.id.xIndicator);
        mWalkthroughButton=(Button) findViewById(R.id.xWalkthroughButton);
        mVPWalkThrough.setAdapter(mCustomAdapter);
        mIndicator.setViewPager(mVPWalkThrough);
    }



    public class CustomAdapter extends PagerAdapter {//Coverts

        Context mContext;
        LayoutInflater mLayoutInflator;
        CustomAdapter(Context mContext){
            this.mContext=mContext;
            mLayoutInflator=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

            return view== ((RelativeLayout) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View itemView=mLayoutInflator.inflate(R.layout.layout_walkthrough_items,container,false);
            ImageView imageView=itemView.findViewById(R.id.xIVWalthroughImage);
            TextView mTvname=itemView.findViewById(R.id.xTVWalkthroughTitle);
            TextView mTvBody=itemView.findViewById(R.id.xTVWalkthroughBody);

            mTvname.setText(mTextName[position]);
            mTvBody.setText(mTextBody[position]);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((RelativeLayout) object);
        }
    }



}
