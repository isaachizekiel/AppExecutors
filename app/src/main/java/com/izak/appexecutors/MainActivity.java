package com.izak.appexecutors;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends FragmentActivity implements Crypto2.Init.InitListener {
    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int NUM_SLIDER_PAGES = 2;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });

    ViewPager2 viewPager;
    ScreenSlidePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySetContentView(0);
        AppExecutors.getInstance().cryptoIO().execute(new Crypto2.Init(this));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    Log.e(TAG, "onRequestPermissionsResult: ");
                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;

            default:
                // Other 'case' lines to check for other
                // permissions this app might request.
                Log.e(TAG, "onRequestPermissionsResult: ");
        }
    }

    @Override
    public void onInitStatus(String status) {
        runOnUiThread(() -> {
            TextView textView = findViewById(R.id.progress_status);
            textView.setText(status);
        });
    }

    @Override
    public void onInitDone() {
        runOnUiThread(() -> mySetContentView(1));
    }

    void mySetContentView(int  res) {
        switch (res) {
            case 1:
                setContentView(R.layout.activity_main);
                viewPager = findViewById(R.id.pager);
                pagerAdapter = new ScreenSlidePagerAdapter(this);
                viewPager.setAdapter(pagerAdapter);
                break;
            case 0:
            default:
                setContentView(R.layout.progress);
                break;

        }
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            //performAction(...);
        } /*else if (shouldShowRequestPermissionRationale(...)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            // showInContextUI(...);
        } */else {
            // You can directly ask for the permission.
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }


    // sub classes and interfaces
    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    Log.e(TAG, "createFragment: "+ position );
                    return new MainFragment();
                case 1:
                    Log.e(TAG, "createFragment: "+ position );
                    return new ScanFragment();
                default:
                    Log.e(TAG, "Invalid fragment");
                    return new MainFragment();

            }
        }
        @Override
        public int getItemCount() {
            return NUM_SLIDER_PAGES;
        }
    }
}