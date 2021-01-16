package vn.timtro.timtroproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.timtro.timtroproject.fragment.AddPostFragment;
import vn.timtro.timtroproject.fragment.HomeFragment;
import vn.timtro.timtroproject.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navView;
    private long backPress;
    private static final int TIME_OUT = 2000;



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
                if (item.getItemId() == R.id.navigation_home){
                    fragment = HomeFragment.newInstance();
                }
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
        navView = findViewById(R.id.nav_view);
    }


    @Override
    public void onBackPressed() {
        if(backPress + TIME_OUT > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
        }else{
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        backPress = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("filter",MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        sharedPreferences.edit().putString("unknown",null).apply();
    }


}