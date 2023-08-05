package com.example.careconnect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserEdit extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImageView;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText descriptionEditText;

    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        // Find views by their IDs
        profileImageView = findViewById(R.id.profilepic);
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        descriptionEditText = findViewById(R.id.experience);

        // Initialize your SQLiteOpenHelper and get a writable database
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        //get parent id
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }

        if (userId > 0) {
            // Retrieve the parent profile from the database
            String[] projection = {DBHelper.USER_NAME, DBHelper.USER_EMAIL, DBHelper.USER_IMAGE, DBHelper.USER_EDUCATION};
            String selection = DBHelper.USER_ID + " = ?";
            String[] selectionArgs = {String.valueOf(userId)};
            Cursor cursor = database.query(DBHelper.TABLE_NAME2, projection, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                // Retrieve the initial values from the cursor
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBHelper.USER_NAME));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(DBHelper.USER_EMAIL));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex(DBHelper.USER_IMAGE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DBHelper.USER_EDUCATION));

                // Set the initial values in the views
                nameEditText.setText(name);
                emailEditText.setText(email);
                descriptionEditText.setText(description);

                // Convert the byte array to a Bitmap and set it in the ImageView
                if (image != null && image.length > 0) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    profileImageView.setImageBitmap(bitmap);
                }
            } else {
                Toast.makeText(this, "Parent profile not found", Toast.LENGTH_SHORT).show();
            }

            // Close the cursor
            cursor.close();
        } else {
            Toast.makeText(this, "Invalid parent ID", Toast.LENGTH_SHORT).show();
        }


        // Set up any required listeners or actions
        Button updateButton = findViewById(R.id.update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                profileImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateProfile() {
        // Retrieve the values from the views
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        // Convert the profile image to a byte array
        Bitmap bitmap = ((BitmapDrawable) profileImageView.getDrawable()).getBitmap();
        byte[] image = getByteArrayFromBitmap(bitmap);

        // Perform the database update using SQL UPDATE statement
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("image", image);
        values.put("education", description);
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }
        String whereClause = "user_id = ?";
        String[] whereArgs = new String[] { String.valueOf(userId) };
        int numRowsUpdated = database.update("user", values, whereClause, whereArgs);

        if (numRowsUpdated > 0) {
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection when the activity is destroyed
        database.close();
        dbHelper.close();
    }
}
