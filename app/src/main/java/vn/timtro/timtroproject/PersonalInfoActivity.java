package vn.timtro.timtroproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalInfoActivity extends AppCompatActivity {
    private Toolbar toolbarPersonalInfo;
    private ImageView imgPersonalMainInfoAvatar;
    private TextView tvPersonalMainInfoName;
    private TextView tvPersonalMainInfoTaikhoan;
    private TextView tvPersonalMainInfoSex;
    private TextView tvPersonalMainInfoNumberPhone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main_info);

        anhXa();
        setSupportActionBar(toolbarPersonalInfo);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void anhXa() {
        toolbarPersonalInfo = findViewById(R.id.toolbar_personal_info);
        imgPersonalMainInfoAvatar = findViewById(R.id.img_personal_main_info_avatar);
        tvPersonalMainInfoName = findViewById(R.id.tv_personal_main__info_name);
        tvPersonalMainInfoTaikhoan = findViewById(R.id.tv_personal_main__info_Taikhoan);
        tvPersonalMainInfoSex = findViewById(R.id.tv_personal_main__info_sex);
        tvPersonalMainInfoNumberPhone = findViewById(R.id.tv_personal_main__info_numberPhone);
    }
}