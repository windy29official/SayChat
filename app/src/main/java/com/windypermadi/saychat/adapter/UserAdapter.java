package com.windypermadi.saychat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.windypermadi.saychat.MessageFireBase;
import com.windypermadi.saychat.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mContext;
    private List<User> mUsers;

    public UserAdapter(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_user, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final User user = mUsers.get(i);
        holder.text_user.setText(user.getUsername());
        holder.cv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MessageFireBase.class);
                i.putExtra("userid", user.getId());
                mContext.startActivity(i);
    }
});
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text_user;
        public LinearLayout cv_user;

        public ViewHolder(View itemView) {
            super(itemView);
            text_user = itemView.findViewById(R.id.text_user);
            cv_user = itemView.findViewById(R.id.cv_user);
        }
    }
}
