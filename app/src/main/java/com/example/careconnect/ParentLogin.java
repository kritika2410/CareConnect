package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ParentLogin extends AppCompatActivity {

    Button parentRegister,parentLogin;
    private EditText email, password;
    Intent intent1;
    int parentId;
    DBHelper dbHelper;
    SQLiteDatabase database;


    //Navigates to parent registration page when button clicked
    private void init() {

        parentRegister = (Button) findViewById(R.id.Register);
        parentRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchParentRegisterScreen();
            }
        });

    }

    private boolean isValidCredentials(String email, String password) {
        DBHelper dbHelper =  new DBHelper(this);
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String selection = DBHelper.COLUMN_EMAIL + " = ? AND " + DBHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs ={email, password};

        Cursor cursor = db.query(DBHelper.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        boolean isValid = cursor.getCount()>0;
        cursor.close();
        db.close();
        return isValid;

    }

    // Method to retrieve the user ID from the database based on login credentials


    public int getParentId(String email, String password) {
        // Retrieve the table name and column names from DBHelper
        String tableName = DBHelper.TABLE_NAME;
        String emailColumnName = DBHelper.COLUMN_EMAIL;
        String passwordColumnName = DBHelper.COLUMN_PASSWORD;
        String parentIdColumnName = DBHelper.COLUMN_ID;

        // Define the selection criteria
        String selection = emailColumnName + " = ? AND " + passwordColumnName + " = ?";
        String[] selectionArgs = {email, password};

        // Execute the query
        Cursor cursor = database.query(tableName, new String[]{parentIdColumnName}, selection, selectionArgs, null, null, null);

        int parentId = -1; // Default value if the parent ID is not found

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(parentIdColumnName);
            Log.d("DEBUG", "Column Index: " + columnIndex);
            if (!cursor.isNull(columnIndex)) {
                parentId = cursor.getInt(columnIndex);
                Log.d("DEBUG", "Parent ID: " + parentId);
            } else {
                Log.d("DEBUG", "Parent ID is null");
            }
        } else {
            Log.d("DEBUG", "Cursor is empty");
        }


        cursor.close();

        return parentId;
    }


    private void launchParentRegisterScreen() {
        Intent intent1 = new Intent(ParentLogin.this, ParentRegister.class);
        startActivity(intent1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);
        init();
//Initialize views
        email =findViewById(R.id.et_email);
        password =findViewById(R.id.et_password);
        parentLogin =findViewById(R.id.btn_login);

   //Initialize database
   dbHelper = new DBHelper(this);
   database =  dbHelper.getReadableDatabase();

   //Set click listener for login button
        parentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail =email.getText().toString().trim();
                String getPassword = password.getText().toString().trim();


                // Perform validation on email and password
                if (TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)) {
                    Toast.makeText(ParentLogin.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                parentId= getParentId(getEmail,getPassword);
                // Check the credentials against the database or API
                if (isValidCredentials(getEmail, getPassword)) {
                    // Login successful, navigate to the main activity or home screen
                    intent1 = new Intent(ParentLogin.this, ParentDashboard.class);
                    intent1.putExtra("USER_ID",parentId);
                    startActivity(intent1);
                    finish();
                } else {
                    // Login failed, display an error message
                    Toast.makeText(ParentLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}