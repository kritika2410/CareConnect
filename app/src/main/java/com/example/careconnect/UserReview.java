package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UserReview extends AppCompatActivity {

    private LinearLayout jobContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);

        DBHelper dbHelper = new DBHelper(this);

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
                if (job != null) {
                    jobContainer = findViewById(R.id.jobContainer);
                    addJobCardView(job);
                }
            }
        }

    }

    private void addJobCardView(AddJob job) {
        // Inflate the userreviewcard.xml layout

        int parentId = job.getParentId();
        Log.d("UserReview", "Parent name: " + parentId);
        DBHelper dbHelper = new DBHelper(this);
        Parent parent = dbHelper.getParentDetails(parentId);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        CardView jobCardView = (CardView) getLayoutInflater().inflate(R.layout.userreviewcard, null);
        jobCardView.setLayoutParams(layoutParams);

        // Set job details to the CardView's TextViews
        TextView jobTitleTextView = jobCardView.findViewById(R.id.jobTitle);
        TextView jobDateTimeTextView = jobCardView.findViewById(R.id.jobDate);
        TextView jobAddressTextView = jobCardView.findViewById(R.id.address);
        TextView parentName = jobCardView.findViewById(R.id.parentname);

        jobTitleTextView.setText(job.getTitle());
        jobDateTimeTextView.setText("Date " + getFormattedDate(job.getJobDate()));
        jobAddressTextView.setText("Address: " + job.getAddress());
        if (parent != null) {
            Log.d("UserReview", "Parent name: " + parent.getName());
            parentName.setText("Name: " + parent.getName());
        } else {
            parentName.setText("Name: Not Available");
        }


        // Add the CardView to the jobContainer LinearLayout
        jobContainer.addView(jobCardView);

        jobCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display an alert dialog to take review details and stars
                AlertDialog.Builder builder = new AlertDialog.Builder(UserReview.this);
                builder.setTitle("Write a Review");

                View view = LayoutInflater.from(UserReview.this).inflate(R.layout.reviewdialog, null);
                builder.setView(view);

                RatingBar ratingBar = view.findViewById(R.id.ratingBar);
                EditText reviewEditText = view.findViewById(R.id.reviewEditText);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int stars = (int) ratingBar.getRating();
                        String reviewDetail = reviewEditText.getText().toString().trim();
                        int userId = -1; // Default value if the extra is not found or not an integer

                        Bundle extras = getIntent().getExtras();
                        if (extras != null && extras.containsKey("USER_ID")) {
                            userId = extras.getInt("USER_ID", -1);
                        }
                        // Create a new Review object and save it to the "review" table
                        Review review = new Review();
                        review.setParentId(parent.getParentId());
                        review.setUserId(userId);
                        review.setJobId(job.getJobId());
                        review.setStars(stars);
                        review.setReviewDetail(reviewDetail);

                        long reviewId = dbHelper.InsertReview(review);

                        if (reviewId != -1) {
                            // Review saved successfully, show a toast or perform any other action
                            Toast.makeText(UserReview.this, "Review submitted successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Failed to save the review, show an error toast or perform any other action
                            Toast.makeText(UserReview.this, "Failed to submit review. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    // Helper method to convert Calendar to formatted date string
    private String getFormattedDate(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
    // Method to save the review to the "review" table
    private void saveReview(int parentId, int userId, AddJob job, int stars, String reviewDetail) {
        DBHelper dbHelper = new DBHelper(this);

        // Create a new Review object and set its properties
        Review review = new Review();
        review.setParentId(parentId);
        review.setUserId(userId);
        review.setJobId(job.getJobId());
        review.setStars(stars);
        review.setReviewDetail(reviewDetail);

        // Insert the review into the "review" table
        long reviewId = dbHelper.InsertReview(review);

        if (reviewId != -1) {
            // Review saved successfully, show a toast or perform any other action
            Toast.makeText(UserReview.this, "Review submitted successfully!", Toast.LENGTH_SHORT).show();
        } else {
            // Failed to save the review, show an error toast or perform any other action
            Toast.makeText(UserReview.this, "Failed to submit review. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }


}