package com.windypermadi.saychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.windypermadi.saychat.adapter.Chat;
import com.windypermadi.saychat.adapter.MessageAdapter;
import com.windypermadi.saychat.adapter.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageFireBase extends AppCompatActivity {
    private TextView username;
    private EditText et_pesan;
    private FloatingActionButton btn_kirim;

    FirebaseUser fuser;
    DatabaseReference reference;

    Intent i;

    MessageAdapter messageAdapter;
    List<Chat> mChat;
    private RecyclerView rv_pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_fire_base);

        username = findViewById(R.id.username);
        rv_pesan = findViewById(R.id.rv_pesan);
        et_pesan = findViewById(R.id.et_pesan);
        btn_kirim = findViewById(R.id.btn_kirim);

        rv_pesan.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        rv_pesan.setLayoutManager(linearLayoutManager);

        i = getIntent();
        final String userid = i.getStringExtra("userid");

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());

                readMessage(fuser.getUid(), userid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes = et_pesan.getText().toString();
                if (mes.length() == 0){
                    et_pesan.setError("Tidak Ada Pesan Dituliskan");
                } else {
                    sendMessage(fuser.getUid(), userid, mes);
                }
                et_pesan.setText("");
            }
        });
    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(final String myid, final String userid) {
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                        mChat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(MessageFireBase.this, mChat);
                    rv_pesan.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
