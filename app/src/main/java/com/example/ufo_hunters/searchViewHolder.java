package com.example.ufo_hunters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class searchViewHolder extends RecyclerView.ViewHolder {

    ImageView profilePic;
    TextView name,email;

    public searchViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.usr_name);
        email = itemView.findViewById(R.id.usr_email);
    }
}
