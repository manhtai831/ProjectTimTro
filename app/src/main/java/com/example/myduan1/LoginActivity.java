package com.example.myduan1;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;


import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText edLoginUsername;
    private TextInputEditText edLoginPass;
    private CheckBox cbLoginLuuMK;
    private Button btnLoginLogin;
    private Button btnLoginSignUp;
    private Button btnQuenMK;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        anhXa();

        btnLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void anhXa() {
        edLoginUsername = (TextInputEditText) findViewById(R.id.ed_login_Username);
        edLoginPass = (TextInputEditText) findViewById(R.id.ed_login_Pass);
        cbLoginLuuMK = (CheckBox) findViewById(R.id.cb_login_LuuMK);
        btnLoginLogin = (Button) findViewById(R.id.btn_login_Login);
        btnLoginSignUp = (Button) findViewById(R.id.btn_login_SignUp);
        btnQuenMK = (Button) findViewById(R.id.btnQuenMK);

    }


}