package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class UserDashboard extends AppCompatActivity {

    LinearLayout notification,review,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        notification = findViewById(R.id.notification);
        review = findViewById(R.id.review);
        profile = findViewById(R.id.profile);
        //Navigates to notification screen
        notification.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                launchNotification();
            }
        });

        //Navigates to review screen
        review.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                launchReview();
            }
        });

        //Navigates to edit profile screen
        profile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                launchEditProfile();
            }
        });



        // Inside UserDashboard activity
        DBHelper dbHelper = new DBHelper(this);


        TextView parent = findViewById(R.id.parent);
        //parent.setText(getIntent().getIntExtra("USER_ID", -1));
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }

        parent.setText(String.valueOf(userId));

        List<AddJob> jobList = dbHelper.getAllJobs();

        LinearLayout jobContainer = findViewById(R.id.jobContainer);

        for (AddJob job : jobList) {
            View jobView = LayoutInflater.from(this).inflate(R.layout.jobitemlayout, jobContainer, false);

            TextView jobTitle = jobView.findViewById(R.id.jobTitle);
            TextView jobDate = jobView.findViewById(R.id.jobDate);
            TextView jobTime = jobView.findViewById(R.id.jobTime);
            TextView address = jobView.findViewById(R.id.address);
            TextView pay = jobView.findViewById(R.id.pay);
            TextView noOfKids = jobView.findViewById(R.id.noOfKids);
            TextView hours = jobView.findViewById(R.id.hours);
             int jobId = job.getJobId();
             int totalHours = job.getTotalHours();
            jobTitle.setText(job.getTitle());
            //jobDate.setText("Date: " + job.getJobDate());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateString = format.format(job.getJobDate().getTime());
            jobDate.setText("Date: " + dateString);

            jobTime.setText("Time: " + job.getJobTime());
            address.setText("Address: " + job.getAddress());
            pay.setText("Pay: $" + job.getPayPerHour() + " per hour");
            noOfKids.setText("Number of Kids: " + job.getNoOfKids());
            hours.setText("Hours: " + job.getTotalHours());

                // Set OnClickListener for the card
                jobView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Start the next activity and pass job details
                        int userId = -1; // Default value if the extra is not found or not an integer

                        Bundle extras = getIntent().getExtras();
                        if (extras != null && extras.containsKey("USER_ID")) {
                            userId = extras.getInt("USER_ID", -1);
                        }
                        Intent intent = new Intent(UserDashboard.this, JobDetails.class);
                        if (userId != -1) {
                            intent.putExtra("userId", userId);
                        }

                        intent.putExtra("jobId", job.getJobId());
                        intent.putExtra("jobTitle", job.getTitle());
                        intent.putExtra("jobDate", job.getJobDate().getTimeInMillis());
                        intent.putExtra("jobTime", job.getJobTime());

                        intent.putExtra("jobAddress",job.getAddress());
                        intent.putExtra("totalHours",job.getTotalHours());
                        intent.putExtra("noOfKids", job.getNoOfKids());
                        intent.putExtra("payPerHour", job.getPayPerHour());
                        startActivity(intent);
                    }
                });

                // Set other TextViews as before

                // ...

                // Add the jobView to the jobContainer
                jobContainer.addView(jobView);
            }

        }

    private void launchEditProfile() {
        Intent intent7 = new Intent(UserDashboard.this, UserEdit.class);
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }

        intent7.putExtra("USER_ID",userId);
        startActivity(intent7);
    }

    private void launchReview() {
        Intent intent6 = new Intent(UserDashboard.this, UserReview.class);
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }

        intent6.putExtra("USER_ID",userId);
        startActivity(intent6);
    }

    private void launchNotification() {
        Intent intent5 = new Intent(UserDashboard.this, UserNotification.class);
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }

        intent5.putExtra("USER_ID",userId);
        startActivity(intent5);
    }


}
