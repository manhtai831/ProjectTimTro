package vn.timtro.timtroproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "AAA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handleCheckConnect();


    }

    private void handleCheckConnect() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isInternetAvailable()) {
                    Log.d(TAG, "onCreate: co ket noi");
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();
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

    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }

}