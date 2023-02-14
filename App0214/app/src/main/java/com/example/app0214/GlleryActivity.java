package com.example.app0214;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class GlleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gllery);

        Button bt_prev = findViewById(R.id.bt_prev);
        Button bt_next = findViewById(R.id.bt_next);
        GalleryView galleryView =findViewById(R.id.galleryView);

        bt_next.setOnClickListener((v)->{
            galleryView.nextImg();
        });
    }
}




