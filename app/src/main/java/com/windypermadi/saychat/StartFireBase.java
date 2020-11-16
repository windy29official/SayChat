package com.windypermadi.saychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartFireBase extends AppCompatActivity {
    private Button btn_login, btn_daftar;

    FirebaseUser firebaseUser;
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //check if user null
        if (firebaseUser != null){
            Intent i = new Intent(StartFireBase.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_fire_base);

        btn_login = findViewById(R.id.btn_login);
        btn_daftar = findViewById(R.id.btn_daftar);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartFireBase.this, LoginFireBase.class);
                startActivity(i);
                finish();
            }
        });
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartFireBase.this, DaftarFireBase.class);
                startActivity(i);
                finish();
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
}
