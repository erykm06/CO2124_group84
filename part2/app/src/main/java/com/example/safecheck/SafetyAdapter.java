package com.example.safecheck;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safecheck.data.SafetyCheck;

import java.util.List;

public class SafetyAdapter extends RecyclerView.Adapter<SafetyAdapter.ViewHolder> {

    private List<SafetyCheck> list;
    private Context context;

    public SafetyAdapter(List<SafetyCheck> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void set(List<SafetyCheck> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView tv = new TextView(context);
        tv.setPadding(30, 30, 30, 30);
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SafetyCheck check = list.get(position);

        // TODO: get real count from db
        int defectCount = 0;

        holder.text.setText(
                check.date + " - " +
                        check.vehicleRegistration + " - " +
                        defectCount + " Defects"
        );

        holder.text.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", check.checkId);
            intent.putExtra("vehicle", check.vehicleRegistration);
            context.startActivity(intent);
        });

        holder.text.setOnLongClickListener(v -> {
            new Thread(() -> {
                com.example.safecheck.data.SafetyRepository repo =
                        new com.example.safecheck.data.SafetyRepository(context);
                repo.deleteCheck(check);
            }).start();

            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        ViewHolder(TextView itemView) {
            super(itemView);
            text = itemView;
        }
    }
}