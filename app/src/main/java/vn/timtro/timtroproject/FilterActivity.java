package vn.timtro.timtroproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;

import vn.timtro.timtroproject.adapter.ProvinceAdapter;

public class FilterActivity extends AppCompatActivity {
    private static final String TAG = "AAA";
    private Toolbar toolbarFilter;
    private CheckBox cbFilterGeneral;
    private CheckBox cbFilterSelfManager;
    private CheckBox cbFilterAppartment;
    private CheckBox cbFilterAppartmantMini;
    private CheckBox cbFilterHomEmstay;
    private EditText svFilterArena;
    private RadioButton rbFilterNew;
    private RadioButton rbFilterPrice;
    private EditText edtFilterMoneyMin;
    private EditText edtFilterMoneyMax;
    private RadioButton rbFilterMoneySuggest1;
    private RadioButton rbFilterMoneySuggest2;
    private RadioButton rbFilterMoneySuggest3;
    private EditText edtFilterAcreageMin;
    private EditText edtFilterAcreageMax;
    private RadioButton rbFilterAcreageSuggest1;
    private RadioButton rbFilterAcreageSuggest2;
    private RadioButton rbFilterAcreageSuggest3;
    private Button btnFilterConfirm;
    private Button btnFilterCancel;
    private RadioGroup rgFilterMoney;
    private RadioGroup rgFilterAcreage;
    private ListView lvFilter;


    final String[] arrProvince = {"Hà Giang", "Cao Bằng", "Bắc Kạn", "Lạng Sơn", "Tuyên Quang",
            "Thái Nguyên", "Phú Thọ", "Bắc Giang", "Quảng Ninh", "Lào Cai", "Yên Bái", "Điện Biên",
            "Hòa Bình", "Lai Châu", "Sơn La", "Bắc Ninh", "Hà Nam", "Hà Nội", "Hải Dương", "Hưng Yên",
            "Hải Phòng", "Nam Định", "Ninh Bình", "Thái Bình", "Vĩnh Phúc", "Thanh Hoá", "Nghệ An",
            "Hà Tĩnh", "Quảng Bình", "Quảng Trị", "Thừa Thiên-Huế", "Đà Nẵng", "Quảng Nam", "Quảng Ngãi",
            "Bình Định", "Phú Yên", "Khánh Hoà", "Ninh Thuận", "Bình Thuận", "Kon Tum", "Gia Lai",
            "Đắc Lắc", "Đắc Nông", "Lâm Đồng", "Bình Phước", "Bình Dương", "Đồng Nai", "Tây Ninh",
            "Bà Rịa-Vũng Tàu", "Hồ Chí Minh", "Long An", "Đồng Tháp", "Tiền Giang", "An Giang",
            "Bến Tre", "Vĩnh Long", "Trà Vinh", " Hậu Giang", "Kiên Giang", "Sóc Trăng", " Bạc Liêu",
            "Cà Mau", "Cần Thơ"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        anhXa();
        setSupportActionBar(toolbarFilter);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        ImageButton ivArrow = view.findViewById(R.id.iv_arrow_2);
        TextView tvCustomActionBar = view.findViewById(R.id.tv_custom_action_bar_2);
        tvCustomActionBar.setText("Bộ lọc");
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnFilterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ArrayList<String> listProvince = new ArrayList<>();
        listProvince.addAll(Arrays.asList(arrProvince));
        final ProvinceAdapter provinceAdapter = new ProvinceAdapter(FilterActivity.this, listProvince);
        lvFilter.setVisibility(View.INVISIBLE);
        lvFilter.setAdapter(provinceAdapter);
        lvFilter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                view.onTouchEvent(motionEvent);

                return true;
            }

        });
        lvFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: " + listProvince.get(i));
                svFilterArena.setText(listProvince.get(i));

                lvFilter.setVisibility(View.INVISIBLE);


            }
        });
        edtFilterMoneyMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rbFilterMoneySuggest1.setChecked(false);
                rbFilterMoneySuggest2.setChecked(false);
                rbFilterMoneySuggest3.setChecked(false);
            }
        });
        edtFilterAcreageMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rbFilterAcreageSuggest1.setChecked(false);
                rbFilterAcreageSuggest2.setChecked(false);
                rbFilterAcreageSuggest3.setChecked(false);

            }
        });


        rgFilterMoney.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                Log.d(TAG, "onCheckedChanged: " + radioButton.getId() + " - " + rbFilterMoneySuggest1.getId());
                if (radioButton.getId() == rbFilterMoneySuggest1.getId()) {
                    edtFilterMoneyMin.setText("100000");
                    edtFilterMoneyMax.setText("800000");
                } else if (radioButton.getId() == rbFilterMoneySuggest2.getId()) {
                    edtFilterMoneyMin.setText("800000");
                    edtFilterMoneyMax.setText("2000000");
                } else if (radioButton.getId() == rbFilterMoneySuggest3.getId()) {
                    edtFilterMoneyMin.setText("2000000");
                    edtFilterMoneyMax.setText("3200000");
                }
            }
        });

        rgFilterAcreage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                if (radioButton.getId() == rbFilterAcreageSuggest1.getId()) {
                    edtFilterAcreageMin.setText("0");
                    edtFilterAcreageMax.setText("15");
                } else if (radioButton.getId() == rbFilterAcreageSuggest2.getId()) {
                    edtFilterAcreageMin.setText("15");
                    edtFilterAcreageMax.setText("30");
                } else if (radioButton.getId() == rbFilterAcreageSuggest3.getId()) {
                    edtFilterAcreageMin.setText("30");
                    edtFilterAcreageMax.setText("70");
                }
            }
        });
        svFilterArena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lvFilter.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    lvFilter.setVisibility(View.INVISIBLE);
                }else{
                    lvFilter.setVisibility(View.VISIBLE);
                    provinceAdapter.filter(charSequence.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //lvFilter.setVisibility(View.INVISIBLE);
            }
        });
        btnFilterConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("filter", MODE_PRIVATE);
        cbFilterSelfManager.setChecked(sharedPreferences.getBoolean("brandSelfChecked", false));
        cbFilterHomEmstay.setChecked(sharedPreferences.getBoolean("brandHomestayChecked", false));
        cbFilterGeneral.setChecked(sharedPreferences.getBoolean("brandGeneralChecked", false));
        cbFilterAppartment.setChecked(sharedPreferences.getBoolean("brandAppartmentChecked", false));
        cbFilterAppartmantMini.setChecked(sharedPreferences.getBoolean("brandMiniChecked", false));
        if (!sharedPreferences.getString("place", "").trim().equals("")) {
            svFilterArena.setHint(sharedPreferences.getString("place", ""));
        }
        rbFilterNew.setChecked(true);
        rbFilterNew.setChecked(sharedPreferences.getBoolean("sortNewChecked", true));
        rbFilterPrice.setChecked(sharedPreferences.getBoolean("sortPriceChecked", false));
        edtFilterAcreageMin.setText(sharedPreferences.getString("acreageMin", null));
        edtFilterAcreageMax.setText(sharedPreferences.getString("acreageMax", null));
        edtFilterMoneyMin.setText(sharedPreferences.getString("moneyMin", null));
        edtFilterMoneyMax.setText(sharedPreferences.getString("moneyMax", null));


    }

    private void filter() {
        Intent intent = new Intent();
        SharedPreferences sharedPreferences = getSharedPreferences("filter", MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        if (cbFilterAppartmantMini.isChecked()) {
            editor.putString("brandMini", cbFilterAppartmantMini.getText().toString().trim());
            editor.putBoolean("brandMiniChecked", true);
            intent.putExtra("brandMini", cbFilterAppartmantMini.getText().toString().trim());
        } else {
            editor.putString("brandMini", "");
            editor.putBoolean("brandMiniChecked", false);
        }
        if (cbFilterAppartment.isChecked()) {
            editor.putString("brandAppartment", cbFilterAppartment.getText().toString().trim());
            editor.putBoolean("brandAppartmentChecked", true);
            intent.putExtra("brandAppartment", cbFilterAppartment.getText().toString().trim());
        } else {
            editor.putString("brandAppartment", "");
            editor.putBoolean("brandAppartmentChecked", false);
        }
        if (cbFilterGeneral.isChecked()) {
            editor.putString("brandGeneral", cbFilterGeneral.getText().toString().trim());
            editor.putBoolean("brandGeneralChecked", true);
            intent.putExtra("brandGeneral", cbFilterGeneral.getText().toString().trim());
        } else {
            editor.putString("brandGeneral", "");
            editor.putBoolean("brandGeneralChecked", false);
        }
        if (cbFilterHomEmstay.isChecked()) {
            editor.putString("brandHomestay", cbFilterHomEmstay.getText().toString().trim());
            editor.putBoolean("brandHomestayChecked", true);
            intent.putExtra("brandHomestay", cbFilterHomEmstay.getText().toString().trim());
        } else {
            editor.putString("brandHomestay", "");
            editor.putBoolean("brandHomestayChecked", false);
        }
        if (cbFilterSelfManager.isChecked()) {
            editor.putString("brandSelf", cbFilterSelfManager.getText().toString().trim());
            editor.putBoolean("brandSelfChecked", true);
            intent.putExtra("brandSelf", cbFilterSelfManager.getText().toString().trim());
        } else {
            editor.putString("brandSelf", "");
            editor.putBoolean("brandSelfChecked", false);
        }
        editor.putString("place", svFilterArena.getText().toString().trim());
        intent.putExtra("place", svFilterArena.getText().toString().trim());

        if (rbFilterNew.isChecked()) {
            editor.putString("sort", rbFilterNew.getText().toString().trim());
            editor.putBoolean("sortNewChecked", true);
            intent.putExtra("sort", rbFilterNew.getText().toString().trim());
        } else {
            editor.putBoolean("sortNewChecked", false);
        }
        if (rbFilterPrice.isChecked()) {
            editor.putString("sort", rbFilterPrice.getText().toString().trim());
            editor.putBoolean("sortPriceChecked", true);
            intent.putExtra("sort", rbFilterPrice.getText().toString().trim());
        } else {
            editor.putBoolean("sortPriceChecked", false);
        }
        editor.putString("moneyMin", edtFilterMoneyMin.getText().toString().trim());
        editor.putString("moneyMax", edtFilterMoneyMax.getText().toString().trim());
        editor.putString("acreageMin", edtFilterAcreageMin.getText().toString().trim());
        editor.putString("acreageMax", edtFilterAcreageMax.getText().toString().trim());



        intent.putExtra("moneyMin", edtFilterMoneyMin.getText().toString());
        intent.putExtra("moneyMax", edtFilterMoneyMax.getText().toString());
        intent.putExtra("acreageMin", edtFilterAcreageMin.getText().toString());
        intent.putExtra("acreageMax", edtFilterAcreageMax.getText().toString());
        setResult(123, intent);
        editor.putString("unknown", "not null");
        editor.apply();


        finish();


    }

    private void anhXa() {
        toolbarFilter = findViewById(R.id.toolbar_filter);
        cbFilterGeneral = findViewById(R.id.cb_filter_general);
        cbFilterSelfManager = findViewById(R.id.cb_filter_self_manager);
        cbFilterAppartment = findViewById(R.id.cb_filter_appartment);
        cbFilterAppartmantMini = findViewById(R.id.cb_filter_appartmant_mini);
        cbFilterHomEmstay = findViewById(R.id.cb_filter_homestay);
        svFilterArena = findViewById(R.id.sv_filter_arena);
        RadioGroup rgFilterSort = findViewById(R.id.rg_filter_sort);
        rbFilterNew = findViewById(R.id.rb_filter_new);
        rbFilterPrice = findViewById(R.id.rb_filter_price);
        edtFilterMoneyMin = findViewById(R.id.edt_filter_money_min);
        edtFilterMoneyMax = findViewById(R.id.edt_filter_money_max);
        rbFilterMoneySuggest1 = findViewById(R.id.rb_filter_money_suggest_1);
        rbFilterMoneySuggest2 = findViewById(R.id.rb_filter_money_suggest_2);
        rbFilterMoneySuggest3 = findViewById(R.id.rb_filter_money_suggest_3);
        edtFilterAcreageMin = findViewById(R.id.edt_filter_acreage_min);
        edtFilterAcreageMax = findViewById(R.id.edt_filter_acreage_max);
        rbFilterAcreageSuggest1 = findViewById(R.id.rb_filter_acreage_suggest_1);
        rbFilterAcreageSuggest2 = findViewById(R.id.rb_filter_acreage_suggest_2);
        rbFilterAcreageSuggest3 = findViewById(R.id.rb_filter_acreage_suggest_3);
        btnFilterConfirm = findViewById(R.id.btn_filter_confirm);
        btnFilterCancel = findViewById(R.id.btn_filter_cancel);
        rgFilterMoney = findViewById(R.id.rg_filter_money);
        rgFilterAcreage = findViewById(R.id.rg_filter_acreage);
        lvFilter = findViewById(R.id.lv_filter);
    }

}