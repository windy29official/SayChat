package com.windypermadi.saychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DaftarFireBase extends AppCompatActivity {
    EditText username, password;
    Button registerButton;
    String user, pass;
    TextView login;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_fire_base);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DaftarFireBase.this, LoginFireBase.class);
                startActivity(i);
            }
        });

        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_username = username.getText().toString();
                String text_pass = password.getText().toString();

                if (TextUtils.isEmpty(text_username) || TextUtils.isEmpty(text_pass)){
                    Toast.makeText(DaftarFireBase.this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (text_pass.length() < 6){
                    Toast.makeText(DaftarFireBase.this, "Password harus lebih dari 6 karakter", Toast.LENGTH_SHORT).show();
                } else {
                    register(text_username, text_pass);
                }
            }
        });
    }

    private void register(final String username, String pass){
        auth.createUserWithEmailAndPassword(username, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent i = new Intent(DaftarFireBase.this, LoginFireBase.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(DaftarFireBase.this, "Tidak dapat mendaftar", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(DaftarFireBase.this, "Tidak dapat mendaftar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
