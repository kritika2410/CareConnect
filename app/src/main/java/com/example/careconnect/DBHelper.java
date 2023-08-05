package com.example.careconnect;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import kotlinx.coroutines.Job;

public class DBHelper extends SQLiteOpenHelper {

    static final String DBNAME = "CareConnect.db";
    static final int version =1;
    //Parent table details
     static final String TABLE_NAME = "parent";
     static final String COLUMN_ID = "parent_id";
     static final String COLUMN_NAME = "name";
     static final String COLUMN_EMAIL = "email";
     static final String COLUMN_PASSWORD = "password";
     static final String COLUMN_IMAGE = "image";
     static final String COLUMN_DESCRIPTION = "description";

    String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT," +
            COLUMN_EMAIL + " TEXT," +
            COLUMN_PASSWORD + " TEXT," +
            COLUMN_IMAGE + " BLOB," +
            COLUMN_DESCRIPTION + " TEXT" +
            ")";
    // User Table details
    static final String TABLE_NAME2 = "user";
    static final String USER_ID = "user_id";
    static final String USER_NAME = "name";
    static final String USER_EMAIL = "email";
    static final String USER_PASSWORD = "password";
    static final String USER_IMAGE = "image";
    static final String USER_EDUCATION = "education";


    String createUserTable = "CREATE TABLE " + TABLE_NAME2 + " (" +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USER_NAME + " TEXT," +
            USER_EMAIL + " TEXT," +
            USER_PASSWORD + " TEXT," +
            USER_IMAGE + " BLOB," +
            USER_EDUCATION + " TEXT" +
            ")";

    //Add job table details

    // Table Names
     static final String TABLE_JOB = "job";

    // Job Table Columns
     static final String COLUMN_JOB_ID = "job_id";
     static final String COLUMN_PARENT_ID = "parent_id";
     static final String COLUMN_USER_ID = "user_id";
     static final String COLUMN_TITLE = "title";
     static final String COLUMN_JOB_DATE = "job_date";
     static final String COLUMN_TOTAL_HOURS = "total_hours";
     static final String COLUMN_PAY_PER_HOUR = "pay_per_hour";
     static final String COLUMN_ADDRESS = "address";
     static final String COLUMN_NO_OF_KIDS = "no_of_kids";
     static final String COLUMN_SPECIAL_KIDS = "special_kids";
     static final String COLUMN_JOB_TIME = "time";
     static final String  COLUMN_JOB_FLAG = "flag";

    String CREATE_JOB_TABLE = "CREATE TABLE " + TABLE_JOB + "("
            + COLUMN_JOB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PARENT_ID + " INTEGER,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_JOB_DATE + " TEXT,"
            + COLUMN_JOB_TIME + " TEXT,"
            + COLUMN_ADDRESS + " TEXT,"
            + COLUMN_TOTAL_HOURS + " INTEGER,"
            + COLUMN_NO_OF_KIDS + " INTEGER,"
            + COLUMN_PAY_PER_HOUR + " INTEGER,"
            + COLUMN_SPECIAL_KIDS + " TEXT,"
            + COLUMN_JOB_FLAG + " INTEGER DEFAULT -1,"
            + "FOREIGN KEY (" + COLUMN_PARENT_ID + ") REFERENCES parent(parent_id),"
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES user(user_id)"
            + ")";


    private static final String TABLE_MEAL = "meal";
    private static final String COLUMN_MEAL_ID = "meal_id";
    private static final String COLUMN_JOB_ID_FK = "job_id_fk";
    private static final String COLUMN_MEAL_REQUIRED = "meal_required";
    private static final String COLUMN_ALLERGY = "allergy";
    private static final String COLUMN_INSTRUCTION = "instruction";

    // Create the meal table
    String createMealTableQuery = "CREATE TABLE " + TABLE_MEAL + "(" +
            COLUMN_MEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_JOB_ID_FK + " INTEGER," +
            COLUMN_MEAL_REQUIRED + " TEXT," +
            COLUMN_ALLERGY + " TEXT," +
            COLUMN_INSTRUCTION + " TEXT," +
            "FOREIGN KEY(" + COLUMN_JOB_ID_FK + ") REFERENCES " + TABLE_JOB + "(" + COLUMN_JOB_ID + ")" +
            ")";


    private static final String TABLE_ACTIVITY = "activity";
    private static final String COLUMN_ACTIVITY_ID = "activity_id";
    private static final String COLUMN_JOB_ID_FK2 = "job_id_fk";
    private static final String COLUMN_ACTIVITY_REQUIRED = "activity_required";
    private static final String COLUMN_ACTIVITY = "activity_details";
    private static final String COLUMN_INSTRUCTION2 = "instruction";

    // Create the meal table
    String createActivityTableQuery = "CREATE TABLE " + TABLE_ACTIVITY + "(" +
            COLUMN_ACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_JOB_ID_FK2 + " INTEGER," +
            COLUMN_ACTIVITY_REQUIRED + " TEXT," +
            COLUMN_ACTIVITY + " TEXT," +
            COLUMN_INSTRUCTION2 + " TEXT," +
            "FOREIGN KEY(" + COLUMN_JOB_ID_FK2 + ") REFERENCES " + TABLE_JOB + "(" + COLUMN_JOB_ID + ")" +
            ")";

    // Review table name and columns
    private static final String TABLE_REVIEW = "review";
    private static final String COLUMN_REVIEW_ID = "review_id";
    static final String COLUMN_REVIEW_STARS = "review_stars";
     static final String COLUMN_REVIEW_DETAIL = "review_detail";
    private static final String COLUMN_PARENTS_ID = "parent_id";
    private static final String COLUMN_USERS_ID = "user_id";
    private static final String COLUMN_JOBS_ID = "job_id";


    // Create review table query
    private static final String createReviewTable = "CREATE TABLE " + TABLE_REVIEW + "("
            + COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + COLUMN_REVIEW_STARS + " INTEGER,"
            + COLUMN_REVIEW_DETAIL + " TEXT,"
            + COLUMN_PARENT_ID + " INTEGER,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_JOB_ID + " INTEGER,"
            + "FOREIGN KEY (" + COLUMN_PARENTS_ID + ") REFERENCES " + TABLE_NAME + "(" + COLUMN_ID + "),"
            + "FOREIGN KEY (" + COLUMN_USERS_ID + ") REFERENCES " + TABLE_NAME2 + "(" + COLUMN_USER_ID + "),"
            + "FOREIGN KEY (" + COLUMN_JOBS_ID + ") REFERENCES " + TABLE_JOB + "(" + COLUMN_JOB_ID + ")"
            + ")";

    private static final int DATABASE_VERSION = 1;
    private static final String CONTACT_US = "contact_us";
    private static final String CONTACT_ID = "contactus_id";
    private static final String CONTACT_NAME = "cname";
    private static final String CONTACT_EMAIL = "cemail";
    private static final String CONTACT_PHONE = "cphone";
    private static final String CONTACT_MESSAGE = "cmessage";

    String contactusQuery = "CREATE TABLE " + CONTACT_US + " ("
            + CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CONTACT_NAME + " TEXT, "
            + CONTACT_EMAIL + " TEXT, "
            + CONTACT_PHONE + " TEXT, "
            + CONTACT_MESSAGE + " TEXT)";

    public DBHelper(Context context) {
        super(context, DBNAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableQuery);
        db.execSQL(createUserTable);
        db.execSQL(CREATE_JOB_TABLE);
        db.execSQL(createMealTableQuery);
        db.execSQL(createActivityTableQuery);
        db.execSQL(createReviewTable);
        db.execSQL(contactusQuery);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i<2)
        {
            ParentRegister parent = new ParentRegister();
            InsertParent(parent.createParent());
            UserRegister user = new UserRegister();
            InsertUser(user.createUser());
            ParentPosting posting = new ParentPosting();
            InsertJob(posting.addJob());
            meal mealObj = new meal();
            InsertMeal(mealObj.addMealDetails());
            activityDetails activityDetails= new activityDetails();
            InsertActivity(activityDetails.addActivityDetails());
            ParentContactUs contact = new ParentContactUs();
            InsertContact(contact.createcontact());



        }
    }



    public boolean InsertParent(Parent parent){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,parent.getName());
        cv.put(COLUMN_EMAIL,parent.getEmail());
        cv.put(COLUMN_PASSWORD,parent.getPassword());
        long result = db.insert(TABLE_NAME,null,cv);
        return((result == -1) ? false : true);
    }

    public boolean InsertUser(User user){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME,user.getName());
        cv.put(USER_EMAIL,user.getEmail());
        cv.put(USER_PASSWORD,user.getPassword());
        long result = db.insert(TABLE_NAME2,null,cv);
        return((result == -1) ? false : true);
    }

    public boolean InsertJob(AddJob addjob){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PARENT_ID, addjob.getParentId());
        Log.d("DEBUG", "Parent ID: " + addjob.getParentId());
        cv.put(COLUMN_USER_ID, addjob.getUserId());
        Log.d("DEBUG", "User ID: " + addjob.getUserId());
        cv.put(COLUMN_TITLE,addjob.getTitle());
        Log.d("DEBUG", "title ID: " + addjob.getTitle());
       // cv.put(COLUMN_JOB_DATE, addjob.getJobDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = dateFormat.format(addjob.getJobDate().getTime());
        cv.put(COLUMN_JOB_DATE, formattedDate);
        Log.d("DEBUG", "Date: " + formattedDate);
        cv.put(COLUMN_JOB_TIME,addjob.getJobTime());

        Log.d("DEBUG", "Time: " + addjob.getJobDate());
        cv.put(COLUMN_ADDRESS, addjob.getAddress());
        Log.d("DEBUG", "get address: " + addjob.getAddress());
        cv.put(COLUMN_TOTAL_HOURS, addjob.getTotalHours());
        Log.d("DEBUG", "Total hours: " + addjob.getTotalHours());
        cv.put(COLUMN_NO_OF_KIDS, addjob.getNoOfKids());
        Log.d("DEBUG", "No of kids: " + addjob.getNoOfKids());
        cv.put(COLUMN_PAY_PER_HOUR, addjob.getPayPerHour());
        Log.d("DEBUG", "Pay per hour: " + addjob.getPayPerHour());
        cv.put(COLUMN_SPECIAL_KIDS, addjob.getSpecialKids());
        Log.d("DEBUG", "Special kids: " + addjob.getSpecialKids());
        long result = db.insert(TABLE_JOB,null,cv);
        return((result == -1) ? false : true);
    }

    boolean InsertMeal(MealTable meal) {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_JOB_ID_FK, meal.getJobId());
        cv.put(COLUMN_MEAL_REQUIRED, meal.getMealRequired());
        cv.put(COLUMN_ALLERGY, meal.getAllergy());
        cv.put(COLUMN_INSTRUCTION, meal.getInstruction());
        long mealId = db.insert(TABLE_MEAL, null, cv);
        return mealId != -1;
    }


    boolean InsertActivity(ActivityTable activity) {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_JOB_ID_FK2, activity.getJobId());
        cv.put(COLUMN_ACTIVITY_REQUIRED, activity.getActivityrequired());
        cv.put(COLUMN_ACTIVITY, activity.getActivity());
        cv.put(COLUMN_INSTRUCTION2, activity.getInstruction());
        long activities = db.insert(TABLE_ACTIVITY, null, cv);
        return activities != -1;
    }

    public long InsertReview(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();
        int reviewId =500;
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_ID, reviewId);
        values.put(COLUMN_PARENT_ID, review.getParentId());
        values.put(COLUMN_USER_ID, review.getUserId());
        values.put(COLUMN_JOB_ID, review.getJobId());
        values.put(COLUMN_REVIEW_STARS, review.getStars());
        values.put(COLUMN_REVIEW_DETAIL, review.getReviewDetail());

        return db.insert(TABLE_REVIEW, null, values);
    }

    public long InsertContact(UserContactUs contactUs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTACT_NAME, contactUs.getName());
        values.put(CONTACT_EMAIL, contactUs.getEmail());
        values.put(CONTACT_PHONE, contactUs.getPhone());
        values.put(CONTACT_MESSAGE, contactUs.getMessage());
        return db.insert(CONTACT_US, null, values);
    }

    @SuppressLint("Range")
    public List<AddJob> getAllJobs() {
        List<AddJob> jobList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_JOB;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                AddJob job = new AddJob();
                job.setJobId(cursor.getInt(cursor.getColumnIndex(COLUMN_JOB_ID)));
                job.setParentId(cursor.getInt(cursor.getColumnIndex(COLUMN_PARENT_ID)));
                job.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                job.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
              //  job.setJobDate(cursor.getString(cursor.getColumnIndex(COLUMN_JOB_DATE)));
                // Parse date string and convert it to Calendar
                String dateString = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_DATE));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();
                try {
                    Date date = format.parse(dateString);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                job.setJobDate(calendar);

                job.setJobTime(cursor.getString(cursor.getColumnIndex(COLUMN_JOB_TIME)));
                job.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                job.setTotalHours(cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_HOURS)));
                job.setNoOfKids(cursor.getInt(cursor.getColumnIndex(COLUMN_NO_OF_KIDS)));
                job.setPayPerHour(cursor.getInt(cursor.getColumnIndex(COLUMN_PAY_PER_HOUR)));

                // Add job to the list
                jobList.add(job);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        // Return the list of jobs
        return jobList;
    }

    public void updateJobWithUserId(int jobId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);

        String whereClause = COLUMN_JOB_ID + " = ?";
        String[] whereArgs = { String.valueOf(jobId) };

        int rowsAffected = db.update(TABLE_JOB, values, whereClause, whereArgs);

        db.close();

        if (rowsAffected > 0) {
            Log.d("DBHelper", "Job updated successfully with user ID");
        } else {
            Log.d("DBHelper", "Failed to update job with user ID");
        }
    }




    // Method to get job details and associated user details by parent ID
    @SuppressLint("Range")
    public List<AddJob> getJobDetailsByParentId(int parentId, Context context) {
        List<AddJob> jobDetailsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT j.*, u.* " +
                "FROM " + TABLE_JOB + " j " +
                "INNER JOIN " + TABLE_NAME2 + " u ON j." + COLUMN_USER_ID + " = u." + USER_ID +
                " WHERE j." + COLUMN_PARENT_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(parentId)});

        if (cursor.moveToFirst()) {
            do {
                // Retrieve job details
                int jobId = cursor.getInt(cursor.getColumnIndex(COLUMN_JOB_ID));
                int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
               String jobDateString = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_DATE));
                String jobTime = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_TIME));
                String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                int totalHours = cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_HOURS));
                int noOfKids = cursor.getInt(cursor.getColumnIndex(COLUMN_NO_OF_KIDS));
                int payPerHour = cursor.getInt(cursor.getColumnIndex(COLUMN_PAY_PER_HOUR));
                String specialKids = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIAL_KIDS));
                int flag = -1;
                // Parse job date from the string representation
                Calendar jobDate = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                try {
                    Date parsedDate = dateFormat.parse(jobDateString);
                    jobDate.setTime(parsedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // Retrieve user details
                String name = cursor.getString(cursor.getColumnIndex(USER_NAME));
                String email = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
                String experience = cursor.getString(cursor.getColumnIndex(USER_EDUCATION));
                byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(USER_IMAGE));

                // Create AddJob object and add to the list
                AddJob jobDetails = new AddJob(jobId, parentId, userId, title, jobDate, jobTime, totalHours, payPerHour, address, noOfKids, specialKids,flag);
                if (imageBytes != null) {
                    jobDetails.getUser().setImage(imageBytes);
                } else {
                    // Set default image
                    // Assuming you have a default image byte array
                    byte[] defaultImageBytes = getDefaultImageBytes(context);
                    jobDetails.getUser().setImage(defaultImageBytes);
                }

                User user = new User(userId, name, email, null, imageBytes,null, experience);
                jobDetails.setUser(user);
                jobDetailsList.add(jobDetails);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return jobDetailsList;
    }
    private byte[] getDefaultImageBytes(Context context) {
        Drawable defaultImageDrawable = ContextCompat.getDrawable(context, R.drawable.girl);
        Bitmap defaultImageBitmap = ((BitmapDrawable) defaultImageDrawable).getBitmap();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        defaultImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public Cursor getUserDetails(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Retrieve user details for the given user ID
        String userQuery = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + USER_ID + " = " + userId;
        Cursor userCursor = db.rawQuery(userQuery, null);

        return userCursor;
    }


    // Method to retrieve job details and complete user details
    public Cursor getJobAndUserDetailsForParent(int parentId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Retrieve job details for the given parent ID
        String jobQuery = "SELECT * FROM " + TABLE_JOB + " WHERE " + COLUMN_PARENT_ID + " = " + parentId;
        Cursor jobCursor = db.rawQuery(jobQuery, null);

        return jobCursor;
    }

    // Method to retrieve job details and complete user details
    @SuppressLint("Range")
    public int getJobIdForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int jobId = -1;

        // Define the columns to be retrieved from the database
        String[] columns = {COLUMN_JOB_ID};

        // Define the selection criteria
        String selection = COLUMN_USER_ID + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {String.valueOf(userId)};

        // Query the database to retrieve the job ID for the given user ID
        Cursor cursor = db.query(TABLE_JOB, columns, selection, selectionArgs, null, null, null);

        // Check if the cursor has a valid result
        if (cursor != null && cursor.moveToFirst()) {
            jobId = cursor.getInt(cursor.getColumnIndex(COLUMN_JOB_ID));
            cursor.close();
        }

        // Close the database connection
        db.close();

        return jobId;
    }

    // Method to retrieve flag detail from job table
    @SuppressLint("Range")
    public int getJobFlag(int jobId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_JOB_FLAG};
        String selection = COLUMN_JOB_ID + " = ?";
        String[] selectionArgs = {String.valueOf(jobId)};
        Cursor cursor = db.query(TABLE_JOB, projection, selection, selectionArgs, null, null, null);
        int jobFlag = -1;
        if (cursor != null && cursor.moveToFirst()) {
            jobFlag = cursor.getInt(cursor.getColumnIndex(COLUMN_JOB_FLAG));
            cursor.close();
        }
        return jobFlag;
    }

    public void updateJobFlag(int jobId, int flag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_JOB_FLAG, flag);

        String whereClause = COLUMN_JOB_ID + " = ?";
        String[] whereArgs = {String.valueOf(jobId)};

        int rowsAffected = db.update(TABLE_JOB, values, whereClause, whereArgs);

        db.close();

        if (rowsAffected > 0) {
            Log.d("DBHelper", "Job flag updated successfully");
        } else {
            Log.d("DBHelper", "Failed to update job flag");
        }
    }
// Method to retrieve job details from the job Id
@SuppressLint("Range")
public AddJob getJobDetails(int jobId) {
    SQLiteDatabase db = this.getReadableDatabase();
    String[] projection = {
            COLUMN_TITLE,
            COLUMN_JOB_DATE,
            COLUMN_JOB_TIME,
            COLUMN_ADDRESS,
            COLUMN_TOTAL_HOURS,
            COLUMN_NO_OF_KIDS,
            COLUMN_PAY_PER_HOUR,
            COLUMN_SPECIAL_KIDS,
            COLUMN_PARENT_ID,
            COLUMN_USER_ID
    };
    String selection = COLUMN_JOB_ID + " = ?";
    String[] selectionArgs = {String.valueOf(jobId)};
    Cursor cursor = db.query(TABLE_JOB, projection, selection, selectionArgs, null, null, null);
    AddJob job = null;
    Parent parent = null;
    if (cursor != null && cursor.moveToFirst()) {
        String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
        String jobDate = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_DATE));
        String jobTime = cursor.getString(cursor.getColumnIndex(COLUMN_JOB_TIME));
        String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
        int totalHours = cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_HOURS));
        int noOfKids = cursor.getInt(cursor.getColumnIndex(COLUMN_NO_OF_KIDS));
        int payPerHour = cursor.getInt(cursor.getColumnIndex(COLUMN_PAY_PER_HOUR));
        String specialKids = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIAL_KIDS));

        // Retrieve the user ID associated with the job
        int parentId = cursor.getInt(cursor.getColumnIndex(COLUMN_PARENT_ID));
        int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
        // Retrieve user details from the user table based on the user ID
        String[] parentProjection = {
                COLUMN_NAME
        };
        String parentSelection = COLUMN_ID + " = ?";
        String[] parentSelectionArgs = {String.valueOf(parentId)};
        Cursor userCursor = db.query(TABLE_NAME, parentProjection, parentSelection, parentSelectionArgs, null, null, null);

        // Check if the user cursor is valid and move to the first row
        if (userCursor != null && userCursor.moveToFirst()) {
            String parentName = userCursor.getString(userCursor.getColumnIndex(COLUMN_NAME));


            // Set the retrieved details to the existing Job object
            job = new AddJob();
            job.setParentId(parentId);
            job.setUserId(userId);
            job.setJobId(jobId);
            job.setTitle(title);

            // Parse date string and convert it to Calendar
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = dateFormat.parse(jobDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                job.setJobDate(calendar);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            job.setJobTime(jobTime);
            job.setAddress(address);
            job.setTotalHours(totalHours);
            job.setNoOfKids(noOfKids);
            job.setPayPerHour(payPerHour);
            job.setSpecialKids(specialKids);

            //set Parent Details
            parent = new Parent();
            parent.setName(parentName);
            userCursor.close();
        }
            cursor.close();
        } else {
            return null;
        }
        return job;
    }

// Method to retrieve meal details based on job id
@SuppressLint("Range")
    public MealTable getMealDetails(int jobId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_MEAL_REQUIRED,
                COLUMN_ALLERGY,
                COLUMN_INSTRUCTION
        };
        String selection = COLUMN_JOB_ID_FK + " = ?";
        String[] selectionArgs = {String.valueOf(jobId)};
        Cursor cursor = db.query(TABLE_MEAL, projection, selection, selectionArgs, null, null, null);
        MealTable meal = null;
        if (cursor != null && cursor.moveToFirst()) {

            String mealRequired = cursor.getString(cursor.getColumnIndex(COLUMN_MEAL_REQUIRED));
            String allergy = cursor.getString(cursor.getColumnIndex(COLUMN_ALLERGY));
            String instruction = cursor.getString(cursor.getColumnIndex(COLUMN_INSTRUCTION));

            // Create a new Meal object with the retrieved details
            meal = new MealTable(jobId, mealRequired, allergy, instruction);
            Log.e("DBHelper", "No meal details found for Job ID: " + jobId);
            cursor.close();
        }
        else{
                // Log an error if the cursor is null
                Log.e("DBHelper", "Cursor is null when fetching meal details for Job ID: " + jobId);
            return null;
        }
        return meal;
    }

    //Method to retrieve activity details from given job id
    @SuppressLint("Range")
    public ActivityTable getActivityDetails(int jobId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_ACTIVITY_REQUIRED,
                COLUMN_ACTIVITY,
                COLUMN_INSTRUCTION2
        };
        String selection = COLUMN_JOB_ID_FK2 + " = ?";
        String[] selectionArgs = {String.valueOf(jobId)};

        // Log the values for debugging
        Log.d("UserNotification", "Job ID: " + jobId);
        Log.d("UserNotification", "Projection: " + Arrays.toString(projection));
        Log.d("UserNotification", "Selection: " + selection);
        Log.d("UserNotification", "Selection Args: " + Arrays.toString(selectionArgs));

        Cursor cursor = db.query(TABLE_ACTIVITY, projection, selection, selectionArgs, null, null, null);
        ActivityTable activity = null;
        if (cursor != null && cursor.moveToFirst()) {
            int rowCount = cursor.getCount();
            Log.d("DBHelper", "Rows returned for Job ID " + jobId + ": " + rowCount);

            String activityRequired = cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVITY_REQUIRED));
            String activityDetails = cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVITY));
            String instruction = cursor.getString(cursor.getColumnIndex(COLUMN_INSTRUCTION2));
            Log.d("UserNotification", "Activity Required (From Cursor): " + activityRequired);
            Log.d("UserNotification", "Activity Details (From Cursor): " + activityDetails);
            // Create a new Activity object with the retrieved details
            activity = new ActivityTable(jobId, activityRequired, activityDetails, instruction);
            Log.e("DBHelper", " Activity details found for Job ID: " + activityDetails);
            cursor.close();
        }
        else{
            Log.e("DBHelper", "Cursor is null when fetching activity details for Job ID: " + jobId);
            return null;
        }

        return activity;

    }

    // Fetch job details for the respective user with the provided user ID and flag = 1

    @SuppressLint("Range")
    List<AddJob> fetchJobsForUser(int userId) {
        List<AddJob> jobs = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve from the job table
        String[] projection = {
                DBHelper.COLUMN_JOB_ID,
                DBHelper.COLUMN_TITLE,
                DBHelper.COLUMN_JOB_DATE,
                DBHelper.COLUMN_JOB_TIME,
                DBHelper.COLUMN_ADDRESS,
                DBHelper.COLUMN_PAY_PER_HOUR,
                DBHelper.COLUMN_JOB_FLAG // Add the COLUMN_JOB_FLAG to the projection
                // Add other columns you need
        };

        // Define the selection criteria for the user's jobs with flag = 1
        String selection = DBHelper.COLUMN_USER_ID + " = ? AND " + DBHelper.COLUMN_JOB_FLAG + " = ?";
        String[] selectionArgs = {String.valueOf(userId), "1"};

        // Query the job table with the specified projection, selection, and selectionArgs
        Cursor cursor = db.query(
                DBHelper.TABLE_JOB,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        AddJob job;
        while (cursor.moveToNext()) {
            int jobId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_JOB_ID));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE));
            String jobDate = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOB_DATE));
            String jobTime = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOB_TIME));
            String address = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ADDRESS));
            int totalHours = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_TOTAL_HOURS));
            int noOfKids = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_NO_OF_KIDS));
            int payPerHour = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PAY_PER_HOUR));
            String specialKids = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SPECIAL_KIDS));
            int flag = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_JOB_FLAG);

            // Set the retrieved details to the existing Job object
            job = new AddJob();
            job.setJobId(jobId);
            job.setTitle(title);

            // Parse date string and convert it to Calendar
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = dateFormat.parse(jobDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                job.setJobDate(calendar);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            job.setJobTime(jobTime);
            job.setAddress(address);
            job.setTotalHours(totalHours);
            job.setNoOfKids(noOfKids);
            job.setPayPerHour(payPerHour);
            job.setSpecialKids(specialKids);
            job.setFlag(flag); // Set the flag

            jobs.add(job); // Add the job to the list
        }

        cursor.close();
        return jobs;
    }
    @SuppressLint("Range")
    public Parent getParentDetails(int parentId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_DESCRIPTION
        };

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(parentId)};

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Parent parent = null;

        if (cursor != null && cursor.moveToFirst()) {
             String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            Log.d("DBHelper", "Parent name: " + name);
            String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            // Add other columns you need

            // Create a new Parent object and set its details
            parent = new Parent();
            parent.setParentId(parentId);
            parent.setName(name);
            parent.setEmail(email);
            parent.setDescription(description);
            // Set other details if needed
            cursor.close();
        }

        return parent;
    }

    @SuppressLint("Range")
    public User getUsersDetails(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                USER_NAME,
                USER_EMAIL,
                USER_IMAGE,
                USER_EDUCATION

        };

        String selection = USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_NAME2, projection, selection, selectionArgs, null, null, null);

        User user = null;

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(USER_NAME));
            Log.d("DBHelper", "Parent name: " + name);
            String email = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
            String description = cursor.getString(cursor.getColumnIndex(USER_EDUCATION));
            // Retrieve the image data as a byte array
            byte[] imageData = cursor.getBlob(cursor.getColumnIndex(USER_IMAGE));

            // Create a new Parent object and set its details
            user = new User();
            user.setUserId(userId);
            user.setName(name);
            user.setEmail(email);
            user.setEducation(description);
            // Set the user image if it exists
            if (imageData != null) {
                user.setImage(imageData);
            }
            // Set other details if needed
            cursor.close();
        }

        return user;
    }

    public  Cursor getReviewDetails(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_REVIEW_STARS,
                COLUMN_REVIEW_DETAIL
        };

        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        return db.query(TABLE_REVIEW, projection, selection, selectionArgs, null, null, null);
    }
    public  Bitmap getParentImage(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Bitmap parentImage = null;

        String[] projection = {COLUMN_IMAGE};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
            if (imageBytes != null) {
                parentImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
            cursor.close();
        }

        return parentImage;
    }


}

