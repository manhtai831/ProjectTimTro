package vn.timtro.timtroproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.timtro.timtroproject.fragment.AddPostFragment;
import vn.timtro.timtroproject.fragment.HomeFragment;
import vn.timtro.timtroproject.fragment.ProfileFragment;

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
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, HomeFragment.newInstance()).commit();
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.navigation_home)
                    fragment = HomeFragment.newInstance();
                else if (item.getItemId() == R.id.navigation_post)
                    fragment = AddPostFragment.newInstance();
                else if (item.getItemId() == R.id.navigation_profile)
                    fragment = ProfileFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                return true;
            }
        });
    }

    private void anhXa() {
        frameLayout = findViewById(R.id.frame_layout);
        navView = findViewById(R.id.nav_view);
    }


}