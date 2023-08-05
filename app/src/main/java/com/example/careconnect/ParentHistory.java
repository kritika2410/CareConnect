package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;

public class ParentHistory extends AppCompatActivity {

        private RecyclerView recyclerView;
        private JobAdapter jobAdapter;
        private DBHelper dbHelper;
        private int parentId;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_parent_history);

            // Get the parent ID passed from the previous activity
            int userId = -1; // Default value if the extra is not found or not an integer

            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey("USER_ID")) {
                userId = extras.getInt("USER_ID", -1);
            }
           parentId =userId;

            // Initialize the DBHelper
            dbHelper = new DBHelper(this);

            // Retrieve the list of job details for the parent ID from the database
            List<AddJob> jobList = getJobList(parentId);

            // Initialize the RecyclerView
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Create an instance of the JobAdapter and set it as the adapter for the RecyclerView
            jobAdapter = new JobAdapter(jobList);
            recyclerView.setAdapter(jobAdapter);
        }

        private List<AddJob> getJobList(int parentId) {
            List<AddJob> jobList = new ArrayList<>();

            // Open the database for reading
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Define the columns to be retrieved from the table
            String[] projection = {
                    DBHelper.COLUMN_TITLE,
                    DBHelper.COLUMN_JOB_DATE,
                    DBHelper.COLUMN_TOTAL_HOURS,
                    DBHelper.COLUMN_PAY_PER_HOUR
            };

            // Define the WHERE clause to filter by parent ID
            String selection = DBHelper.COLUMN_PARENT_ID + " = ?";
            String[] selectionArgs = {String.valueOf(parentId)};

            // Define the sorting order
            String sortOrder = DBHelper.COLUMN_JOB_DATE + " ASC";

            // Perform the query
            Cursor cursor = db.query(
                    DBHelper.TABLE_JOB,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );

            // Process the cursor and populate the jobList
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TITLE));
                String jobDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_JOB_DATE));
                int totalHours = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TOTAL_HOURS));
                int payPerHour = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PAY_PER_HOUR));

                AddJob job = new AddJob();
                job.setTitle(title);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String selectedDate = jobDate.toString();
                try {
                    Date date = dateFormat.parse(selectedDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    job.setJobDate(calendar);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

               // job.setJobDate(jobDate);
                job.setTotalHours(totalHours);
                job.setPayPerHour(payPerHour);

                jobList.add(job);
            }

            // Close the cursor and database
            cursor.close();
            db.close();

            return jobList;
        }
    }

