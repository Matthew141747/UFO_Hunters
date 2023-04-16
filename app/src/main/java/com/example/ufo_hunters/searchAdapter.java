package com.example.ufo_hunters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Context;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class searchAdapter extends RecyclerView.Adapter<searchViewHolder> {

    Context context;

    List<userObj> users;

    public searchAdapter(Context context, List<userObj> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public searchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new searchViewHolder(LayoutInflater.from(context).inflate(R.layout.user_result,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull searchViewHolder holder, int position) {
        holder.name.setText(users.get(position).getFullName());
        holder.email.setText(users.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
