package com.example.myduan1;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myduan1.Fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private BottomNavigationView navView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        setBottomNavigation();

    }

    private void setBottomNavigation() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,HomeFragment.newInstance()).commit();
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        fragment = HomeFragment.newInstance();
                        break;
                    case R.id.navigation_post:
                        break;
                    case R.id.navigation_profile:
                        break;
                    default:
                        fragment = HomeFragment.newInstance();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
                return true;
            }
        });
    }

    private void anhXa() {
        frameLayout = findViewById(R.id.frame_layout);
        navView = findViewById(R.id.nav_view);
    }


}