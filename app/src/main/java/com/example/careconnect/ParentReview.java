package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ParentReview extends AppCompatActivity {

    private LinearLayout jobContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_review);


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
        // Inflate the job_item_card.xml layout

        int userId = job.getUserId();
        Log.d("UserReview", "User Id: " + job.getUserId());
        DBHelper dbHelper = new DBHelper(this);
        User user = dbHelper.getUsersDetails(userId);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        CardView jobCardView = (CardView) getLayoutInflater().inflate(R.layout.parentreviewcard, null);
        jobCardView.setLayoutParams(layoutParams);

        // Set job details to the CardView's TextViews
        TextView jobTitleTextView = jobCardView.findViewById(R.id.jobTitle);
        TextView jobDateTimeTextView = jobCardView.findViewById(R.id.jobDate);
        TextView jobAddressTextView = jobCardView.findViewById(R.id.address);
        TextView userName = jobCardView.findViewById(R.id.username);
        ImageView userImage =jobCardView.findViewById(R.id.userImage);


        jobTitleTextView.setText(job.getTitle());
        jobDateTimeTextView.setText("Date " + getFormattedDate(job.getJobDate()));
        jobAddressTextView.setText("Address: " + job.getAddress());
        if (user != null) {
            Log.d("UserReview", "Parent name: " + user.getName());
            userName.setText("Name: " + user.getName());
            // Check if the user has an image
            if (user.getImage() != null) {
                // Convert the byte[] data to a Bitmap
                Bitmap userBitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);

                // Set the Bitmap to the ImageView
                userImage.setImageBitmap(userBitmap);
            } else {
                // If the user has no image, you can set a default image or leave it blank.
                // For example:
                userImage.setImageResource(R.drawable.girl);
            }

        } else {
            userName.setText("Name: Not Available");
        }


        // Add the CardView to the jobContainer LinearLayout
        jobContainer.addView(jobCardView);

        jobCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display an alert dialog to take review details and stars
                AlertDialog.Builder builder = new AlertDialog.Builder(ParentReview.this);
                builder.setTitle("Write a Review");

                View view = LayoutInflater.from(ParentReview.this).inflate(R.layout.reviewdialog, null);
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
                        review.setParentId(userId);
                        review.setUserId(job.getUserId());
                        review.setJobId(job.getJobId());
                        review.setStars(stars);
                        review.setReviewDetail(reviewDetail);

                        long reviewId = dbHelper.InsertReview(review);

                        if (reviewId != -1) {
                            // Review saved successfully, show a toast or perform any other action
                            Toast.makeText(ParentReview.this, "Review submitted successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Failed to save the review, show an error toast or perform any other action
                            Toast.makeText(ParentReview.this, "Failed to submit review. Please try again.", Toast.LENGTH_SHORT).show();
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

}