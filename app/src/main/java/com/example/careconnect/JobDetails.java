package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JobDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        // Retrieve job details from the intent
        int jobId = getIntent().getIntExtra("jobId", 0);
        String jobTitle = getIntent().getStringExtra("jobTitle");
        long jobDateInMillis = getIntent().getLongExtra("jobDate", 0);
        String jobTime = getIntent().getStringExtra("jobTime");
        int noOfKids = getIntent().getIntExtra("noOfKids", 0);
        int payPerHour = getIntent().getIntExtra("payPerHour", 0);
        String jobAddress = getIntent().getStringExtra("jobAddress");
        int totalHours = getIntent().getIntExtra("totalHours",0);
        // Retrieve the user ID from the intent
        int userId = getIntent().getIntExtra("userId", -1);
        // Find TextViews in the layout
        TextView textViewJobTitle = findViewById(R.id.edtTitle);
        TextView textViewJobDate = findViewById(R.id.edtDate);
        TextView textViewJobTime = findViewById(R.id.edtTime);
        TextView textViewNoOfKids = findViewById(R.id.noOfKids);
        TextView textViewPayPerHour = findViewById(R.id.payPerHour);
        TextView textViewJobAddress = findViewById(R.id.edtaddress);
        TextView textViewtotalHours = findViewById(R.id.hours);
        TextView textUserId = findViewById(R.id.parent);
        // Set the job details in the TextViews
        textViewJobTitle.setText(jobTitle);
        textViewJobDate.setText("Date: " + formatDate(jobDateInMillis));
        textViewJobTime.setText("Time: " + jobTime);
        textViewNoOfKids.setText("Number of Kids: " + noOfKids);
        textViewPayPerHour.setText("Pay: $" + payPerHour + " per hour");
        textViewJobAddress.setText("Address: " +jobAddress);
        textViewtotalHours.setText("Total hours:  " +totalHours);
        // Calculate the total pay
        int totalPay = payPerHour * totalHours;

// Find the new TextView for total pay
        TextView textViewTotalPay = findViewById(R.id.totalPay);

// Set the calculated total pay in the new TextView
        textViewTotalPay.setText("Total Pay: $" + totalPay);
        textUserId.setText(String.valueOf(userId));

        // Create an instance of DBHelper using the JobDetails activity's context
        DBHelper dbHelper = new DBHelper(JobDetails.this);

        // Retrieve Review Details for the given user ID using the instance of DBHelper
        Cursor reviewCursor = dbHelper.getReviewDetails(userId);
        if (reviewCursor != null && reviewCursor.moveToFirst()) {
            // Assuming each user can have only one review
            int reviewStars = reviewCursor.getInt(reviewCursor.getColumnIndexOrThrow(DBHelper.COLUMN_REVIEW_STARS));
            float rating = (float) reviewStars/5.0f;
            String reviewDescription = reviewCursor.getString(reviewCursor.getColumnIndexOrThrow(DBHelper.COLUMN_REVIEW_DETAIL));

            // Set Review Details
            RatingBar reviewStarsRatingBar = findViewById(R.id.reviewStars);
            TextView reviewDescriptionTextView = findViewById(R.id.reviewDescription);

            reviewStarsRatingBar.setRating(rating);
            reviewDescriptionTextView.setText(reviewDescription);
        } else {
            // If there is no review for this user, you can hide the review section or display a default message.
            RatingBar reviewStarsRatingBar = findViewById(R.id.reviewStars);
            TextView reviewDescriptionTextView = findViewById(R.id.reviewDescription);

            reviewStarsRatingBar.setVisibility(View.GONE);
            reviewDescriptionTextView.setText("No review available");
        }
        Button applyButton = findViewById(R.id.btnSubmit);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int jobId = getIntent().getIntExtra("jobId", 0);
                int userId = getIntent().getIntExtra("userId", 0);

                DBHelper dbHelper = new DBHelper(JobDetails.this);
                dbHelper.updateJobWithUserId(jobId, userId);

                // Show a success message
                Toast.makeText(JobDetails.this, "Job successfully updated with user ID", Toast.LENGTH_SHORT).show();

                // Perform other required actions, such as navigating back to the user dashboard
                Intent intent = new Intent(JobDetails.this, UserDashboard.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity if necessary
            }
        });

    }

    private String formatDate(long dateInMillis) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        return format.format(new Date(dateInMillis));
    }

    }
