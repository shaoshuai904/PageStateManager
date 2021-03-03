package com.maple.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Activity 中套一个 Fragment
 *
 * @author : shaoshuai
 * @date ：2020/08/17
 */
public class FragmentTestActivity extends AppCompatActivity {
    String tag = "fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_content, new NormalFragment(), tag)
                    .commit();
        }
    }
}
