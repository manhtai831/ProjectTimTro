package com.example.myduan1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class PostPersonalActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPostManager;
    private Toolbar toolbarPostPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main_quanlibaidang);
        recyclerViewPostManager = findViewById(R.id.recycler_view_post_manager);
        toolbarPostPersonal = findViewById(R.id.toolbar_post_personal);

        setSupportActionBar(toolbarPostPersonal);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
}