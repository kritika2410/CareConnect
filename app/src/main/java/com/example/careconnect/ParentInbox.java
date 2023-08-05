package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ParentInbox extends AppCompatActivity {

    Button btnAccept, btnDecline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_inbox);
        //Creating object of DBHelper class
        DBHelper DBHelper = new DBHelper(this);
        //Getting parent id
        int parentId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            parentId = extras.getInt("USER_ID", -1);
        }

        Cursor cursor = DBHelper.getJobAndUserDetailsForParent(parentId);

        // Iterate through the cursor to retrieve the job and user details

        while (cursor.moveToNext()) {
            LinearLayout parentLayout = findViewById(R.id.linearLayout);
            // Inflate the card view layout
            View cardView = LayoutInflater.from(this).inflate(R.layout.jobuserdetails, parentLayout, false);

            // Retrieve the flag value from the cursor
            int flag = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_JOB_FLAG));


            // Retrieve the accept and decline buttons from the card view
            Button btnAccept = cardView.findViewById(R.id.btn_accept);
            Button btnDecline = cardView.findViewById(R.id.btn_decline);


            int jobId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_JOB_ID));
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TITLE));
            String jobDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_JOB_DATE));
            String jobTime = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_JOB_TIME));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ADDRESS));
            int totalHours = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TOTAL_HOURS));
            int noOfKids = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NO_OF_KIDS));
            int payPerHour = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PAY_PER_HOUR));
            String specialKids = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SPECIAL_KIDS));


            // Retrieve user details for the given user ID
            Cursor userCursor = DBHelper.getUserDetails(userId);
            if (userCursor.moveToNext()) {
                int userIdFromUserTable = userCursor.getInt(userCursor.getColumnIndexOrThrow(DBHelper.USER_ID));
                String name = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_NAME));
                String email = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_EMAIL));
                String password = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_PASSWORD));
                byte[] image = userCursor.getBlob(userCursor.getColumnIndexOrThrow(DBHelper.USER_IMAGE));
                String education = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.USER_EDUCATION));


                // Use the retrieved job and user details

                // Set Job Details
                TextView textJobTitle = cardView.findViewById(R.id.text_job_title);
                TextView textJobDate = cardView.findViewById(R.id.text_job_date);
                TextView textJobTime = cardView.findViewById(R.id.text_job_time);
                TextView textPay = cardView.findViewById(R.id.text_pay);
                TextView textTotalHours = cardView.findViewById(R.id.text_total_hours);

                textJobTitle.setText("Title: "+title);
                textJobDate.setText("Date: "+jobDate);
                textJobTime.setText("Time: "+jobTime);
                textPay.setText("Pay per hour: "+String.valueOf(payPerHour));
                textTotalHours.setText("Total hours: "+String.valueOf(totalHours));

                // Set User Details
                ImageView imageUser = cardView.findViewById(R.id.image_user);
                TextView textUserName = cardView.findViewById(R.id.text_user_name);
                TextView textUserEmail = cardView.findViewById(R.id.text_user_email);
                TextView textUserEducation = cardView.findViewById(R.id.text_user_education);

                Bitmap bitmap = null;
                if (image != null) {
                    bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    imageUser.setImageBitmap(bitmap);
                } else {
                    // Set a default image
                    imageUser.setImageResource(R.drawable.girl);
                }

                imageUser.setImageBitmap(bitmap);
                textUserName.setText("Name: "+name);
                textUserEmail.setText("Email: "+email);
                if(education!=null) {
                    textUserEducation.setText("Education details: " + education);
                }else
                {
                    String educationDetails="Fresher";
                    textUserEducation.setText("Education details: " + educationDetails);}
                // Retrieve Review Details for the given user ID
                Cursor reviewCursor = DBHelper.getReviewDetails(userId);
                if (reviewCursor != null && reviewCursor.moveToFirst()) {
                    // Assuming each user can have only one review
                    int reviewStars = reviewCursor.getInt(reviewCursor.getColumnIndexOrThrow(DBHelper.COLUMN_REVIEW_STARS));
                    float rating = (float) reviewStars/5.0f;
                    String reviewDescription = reviewCursor.getString(reviewCursor.getColumnIndexOrThrow(DBHelper.COLUMN_REVIEW_DETAIL));

                    // Set Review Details
                    RatingBar reviewStarsRatingBar = cardView.findViewById(R.id.reviewStars);
                    TextView reviewDescriptionTextView = cardView.findViewById(R.id.reviewDescription);

                    reviewStarsRatingBar.setRating(rating);
                    reviewDescriptionTextView.setText(reviewDescription);
                } else {
                    // If there is no review for this user, you can hide the review section or display a default message.
                    RatingBar reviewStarsRatingBar = cardView.findViewById(R.id.reviewStars);
                    TextView reviewDescriptionTextView = cardView.findViewById(R.id.reviewDescription);

                    reviewStarsRatingBar.setVisibility(View.GONE);
                    reviewDescriptionTextView.setText("No review available");
                }
            }

            // Set click listeners for the accept and decline buttons
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call a method to update the flag to 1 in the database
                    DBHelper.updateJobFlag(jobId, 1);
                    // Optionally, you can update the UI to reflect the accepted status (e.g., change button colors)
                }
            });

            btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call a method to update the flag to 0 in the database
                    DBHelper.updateJobFlag(jobId, 0);
                    // Optionally, you can update the UI to reflect the declined status (e.g., change button colors)
                }
            });


            // Add the card view to the parent layout
            parentLayout.addView(cardView);



            // Close the user cursor after use
            userCursor.close();



        }
    }
}