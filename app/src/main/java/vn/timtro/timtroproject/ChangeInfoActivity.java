package vn.timtro.timtroproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChangeInfoActivity extends AppCompatActivity {
    private Toolbar toolbarUpdateInfo;
    private EditText edtHoTen;
    private EditText edtTenTaiKhoan;
    private RadioGroup rgGioiTinh;
    private RadioButton rbGtNam;
    private RadioButton rbGtNu;
    private EditText edtSoDienThoai;
    private Button btnChangeInfoSua;
    private Button btnChangeInfoHuy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        anhXa();

        setSupportActionBar(toolbarUpdateInfo);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void anhXa() {
        toolbarUpdateInfo = findViewById(R.id.toolbar_update_info);
        edtHoTen = findViewById(R.id.edt_ho_ten);
        edtTenTaiKhoan = findViewById(R.id.edt_ten_tai_khoan);
        rgGioiTinh = findViewById(R.id.rg_gioi_tinh);
        rbGtNam = findViewById(R.id.rb_gt_nam);
        rbGtNu = findViewById(R.id.rb_gt_nu);
        edtSoDienThoai = findViewById(R.id.edt_so_dien_thoai);
        btnChangeInfoSua = findViewById(R.id.btn_change_info_sua);
        btnChangeInfoHuy = findViewById(R.id.btn_change_info_huy);
    }
}