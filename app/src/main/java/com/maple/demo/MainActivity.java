package com.maple.demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author : shaoshuai
 * @date ：2020/08/17
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_in_activity).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityTestActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.bt_in_fragment).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FragmentTestActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.bt_in_any_view).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AnyViewTestActivity.class);
            startActivity(intent);
        });
    }

}
