package com.example.careconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class JobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<AddJob> jobList;

    public JobAdapter(List<AddJob> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_parenthistory, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddJob job = jobList.get(position);
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.bind(job);
        }
    }


    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtDate;
        private TextView txtHours;
        private TextView txtPay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
            txtHours = itemView.findViewById(R.id.txtHours);
            txtPay = itemView.findViewById(R.id.txtPay);
        }

        public void bind(AddJob job) {

            // Convert Calendar object to String representation
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String selectedDate = dateFormat.format(job.getJobDate().getTime());
            txtDate.setText(selectedDate);
            txtHours.setText(String.valueOf(job.getTotalHours()));
            txtPay.setText(String.valueOf(job.getPayPerHour()));
        }
    }
}
