package com.example.myduan1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myduan1.PostPersonalActivity;
import com.example.myduan1.R;

public class ProfileFragment extends Fragment {
    private TextView tvPersonalMainNotification;
    private ImageView imgPersonalMainAvatar;
    private ImageView imgPersonalMainBaidang;
    private ImageView imgPersonalMainInfo;
    private ImageView imgPersonalMainChangInfo;
    private ImageView imgPersonalMainUpdatePass;
    private Button btnPersonalMainDangxuat;
    public static ProfileFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_main,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        setClickImageView();

    }


    private void anhXa(View view) {
        tvPersonalMainNotification = view.findViewById(R.id.tv_personal_main_notification);
        imgPersonalMainAvatar = view.findViewById(R.id.img_personal_main_avatar);
        imgPersonalMainBaidang = view.findViewById(R.id.img_personal_main_baidang);
        imgPersonalMainInfo = view.findViewById(R.id.img_personal_main_info);
        imgPersonalMainChangInfo = view.findViewById(R.id.img_personal_main_changInfo);
        imgPersonalMainUpdatePass = view.findViewById(R.id.img_personal_main_updatePass);
        btnPersonalMainDangxuat = view.findViewById(R.id.btn_personal_main_dangxuat);

    }

    private void setClickImageView() {
        imgPersonalMainBaidang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PostPersonalActivity.class));
            }
        });
    }
}
