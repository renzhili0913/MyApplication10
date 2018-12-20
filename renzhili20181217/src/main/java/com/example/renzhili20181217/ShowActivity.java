package com.example.renzhili20181217;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        Toast.makeText(ShowActivity.this,intent.getStringExtra("name"),Toast.LENGTH_SHORT).show();
    }
}
