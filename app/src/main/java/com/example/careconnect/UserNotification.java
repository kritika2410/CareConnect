package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserNotification extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);

        dbHelper = new DBHelper(this);
        //Getting user id
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }


        // Get jobId based on userId
        int jobId = dbHelper.getJobIdForUser(userId);

        if (jobId != -1) {
            int jobFlag = dbHelper.getJobFlag(jobId);

            if (jobFlag == 1) {
                // Job accepted
                AddJob job = dbHelper.getJobDetails(jobId);
                MealTable meal = dbHelper.getMealDetails(jobId);
                ActivityTable activity = dbHelper.getActivityDetails(jobId);

                // Display the job, meal, and activity details
                if (job != null) {
                    displayJobDetails(job);
                }
                if (meal != null) {
                    displayMealDetails(meal);
                }
                if (activity != null) {
                    displayActivityDetails(activity);
                }

                // Show the job accepted message
                TextView jobStatusTextView = findViewById(R.id.jobStatusTextView);
                jobStatusTextView.setVisibility(View.VISIBLE);
                jobStatusTextView.setText("Job Accepted");
            } else {
                // Job declined
                AddJob job = dbHelper.getJobDetails(jobId);

                // Display the job details
                displayDeclinedJobDetails(job);

                // Show the job accepted message
                TextView jobStatusTextView = findViewById(R.id.jobStatusTextView);
                jobStatusTextView.setVisibility(View.VISIBLE);
                jobStatusTextView.setText("Job Declined");
            }
        } else {
            // No job found for the user
            Toast.makeText(this, "No job found for the user", Toast.LENGTH_SHORT).show();
        }
    }



    private void displayJobDetails(AddJob job) {
        // TODO: Display job details (e.g., title, date, time, address, etc.)
        if (job != null) {
            // Sample code to display job details
            String jobDetails = "Title: " + job.getTitle()
                    + "\nDate: " + getFormattedDate(job.getJobDate())
                    + "\nTime: " + job.getJobTime()
                    + "\nAddress: " + job.getAddress()
                    + "\nTotal Hours: " + job.getTotalHours()
                    + "\nNumber of Kids: " + job.getNoOfKids()
                    + "\nPay per Hour: " + job.getPayPerHour()
                    + "\nSpecial Kids: " + job.getSpecialKids();


            TextView jobDetailsTextView = findViewById(R.id.jobdetails);
            jobDetailsTextView.setText(jobDetails);
        }
    }

    private void displayDeclinedJobDetails(AddJob job) {
        // TODO: Display job details (e.g., title, date, time, address, etc.)
        if (job != null) {
            // Sample code to display job details
            String jobDetails = "Title: " + job.getTitle()
                    + "\nDate: " + getFormattedDate(job.getJobDate())
                    + "\nTime: " + job.getJobTime()
                    + "\nAddress: " + job.getAddress();


            TextView jobDetailsTextView = findViewById(R.id.jobdetails);
            jobDetailsTextView.setText(jobDetails);
        }
    }
    // Helper method to convert Calendar to formatted date string
    private String getFormattedDate(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private void displayMealDetails(MealTable meal) {
        // TODO: Display meal details (e.g., meal required, allergy, instruction, etc.)
        if (meal != null) {
            String mealDetails = "Meal Required: " + meal.getMealRequired()
                    + "\nAllergy: " + meal.getAllergy()
                    + "\nInstruction: " + meal.getInstruction();


            TextView mealDetailsTextView = findViewById(R.id.mealdetails);
            mealDetailsTextView.setText(mealDetails);
        }
    }

    private void displayActivityDetails(ActivityTable activity) {
        if (activity != null) {
            // Activity details are available, display them
            String activityDetails = "Activity Required: " + activity.getActivityrequired()
                    + "\nActivity Details: " + (activity.getActivity() != null ? activity.getActivity() : "N/A")
                    + "\nInstruction: " + activity.getInstruction();
            Log.d("UserNotification", "Activity Required (From displayActivityDetails): " + activity.getActivityrequired());
            Log.d("UserNotification", "Activity Details (From displayActivityDetails): " + activity.getActivity());
            Log.d("UserNotification", "Activity Instructions (From displayActivityDetails): " + activity.getInstruction());

            TextView activityDetailsTextView = findViewById(R.id.activitydetails);
            activityDetailsTextView.setText(activityDetails);
        } else {
            // If the activity object is null, log an error or show a message to indicate the issue.
            Log.e("UserNotification", "Activity object is null. Unable to display activity details.");
            // You can also show a Toast message to inform the user about the issue.
            Toast.makeText(this, "Activity details not available.", Toast.LENGTH_SHORT).show();
        }
    }

}