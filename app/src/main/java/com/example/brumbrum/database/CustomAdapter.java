package com.example.brumbrum.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brumbrum.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<String> score_id, score_value;
    public CustomAdapter(Context context, ArrayList<String> score_id, ArrayList<String> score_value) {
        this.context = context;
        this.score_id = score_id;
        this.score_value = score_value;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.score_id_text.setText(String.valueOf(score_id.get(position)));
        holder.score_value_text.setText(String.valueOf(score_value.get(position)));
    }

    @Override
    public int getItemCount() {
        return score_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView score_id_text,score_value_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            score_id_text = itemView.findViewById(R.id.score_id_text);
            score_value_text = itemView.findViewById(R.id.score_value_text);
        }
    }
}
