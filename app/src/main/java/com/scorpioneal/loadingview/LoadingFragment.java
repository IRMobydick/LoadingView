package com.scorpioneal.loadingview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * Created by ScorpioNeal on 15/7/29.
 */
public class LoadingFragment extends Fragment{

    private static final String TAG = LoadingFragment.class.getSimpleName();

    private Button mStartBtn, mEndBtn;
    private LoadingView mLoadingView;
    private SeekBar mSeekBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loading_layout, container, false);
        mLoadingView = (LoadingView)view.findViewById(R.id.loadview);
        mStartBtn = (Button)view.findViewById(R.id.start);
        mEndBtn = (Button)view.findViewById(R.id.end);
        mSeekBar = (SeekBar)view.findViewById(R.id.seekbar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "progress" + progress);
                mLoadingView.scaleView((float)progress/100f);
                mLoadingView.roundView((float)progress/100f);

                if(progress == 100) {
                    mLoadingView.start();
                } else if (progress == 0) {
                    mLoadingView.end();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingView.demoStartLoading();
            }
        });
        mEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingView.demoEndLoading();
            }
        });
        return view;
    }

}
