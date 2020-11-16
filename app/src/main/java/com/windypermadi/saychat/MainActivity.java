package com.windypermadi.saychat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.windypermadi.saychat.fragment.ChatFragment;
import com.windypermadi.saychat.fragment.ContactFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private ImageView img_more, img_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        img_more = findViewById(R.id.img_more);
        img_search = findViewById(R.id.img_search);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Tombol();
    }

    private void Tombol() {
        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Apakah mau keluar akun?")
                        .setCancelable(false)
                        .setNegativeButton("Tidak", null)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(MainActivity.this, StartFireBase.class));
                                finish();
                            }
                        })
                        .show();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatFragment(), "CHAT");
        adapter.addFragment(new ContactFragment(), "CONTACT");
        viewPager.setAdapter(adapter);
    }
}
