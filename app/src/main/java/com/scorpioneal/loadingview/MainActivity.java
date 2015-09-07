package com.scorpioneal.loadingview;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mContainer;

    private Fragment  mLoadingFragment;
    private FragmentTransaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainer = (FrameLayout)findViewById(R.id.container);

        mLoadingFragment = new LoadingFragment();

        mTransaction = getFragmentManager().beginTransaction();
        mTransaction.add(R.id.container, mLoadingFragment);
        mTransaction.commit();
    }

}
