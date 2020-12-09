package vn.timtro.timtroproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.InetAddress;
import java.net.UnknownHostException;

import vn.timtro.timtroproject.model.User;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "AAA";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView tvVersion = findViewById(R.id.tv_version);

        tvVersion.setText("Phiên bản hiện tại: " + BuildConfig.VERSION_NAME);
        handleCheckConnect();


    }

    private void handleCheckConnect() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isInternetAvailable()) {

                    autoLogin();

                } else {

                    Log.d(TAG, "run: Khong co ket noi");
                    Looper.prepare();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
                            builder.setTitle("Thông báo");
                            builder.setMessage("Không có kết nối internet. Vui lòng kiểm tra lại");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    handleCheckConnect();
                                }
                            });
                            builder.show();
                        }
                    });
                }
            }
        });

        thread.start();
    }

    private void autoLogin() {
        if(getSharedPreferences("userLog",0).getString("userName","").equals("") ||
                getSharedPreferences("userLog",0).getString("password","").equals("")){
            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            finish();
        }else{
            databaseReference
                    .child("user")
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            if(getSharedPreferences("userLog",0).getString("userName","").trim().equals(snapshot.getKey())){
                                databaseReference.child("user").child(snapshot.getKey()).addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                        getSharedPreferences("userLog",0).edit().putString("idLog",snapshot.getKey()).apply();
                                        User user = snapshot.getValue(User.class);

                                        if(getSharedPreferences("userLog",0).getString("password","").trim().equals(user.getPassword())){
                                            startActivity(new Intent(SplashScreen.this,MainActivity.class));
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }


    }

    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException ignored) {

        }
        return false;
    }

}