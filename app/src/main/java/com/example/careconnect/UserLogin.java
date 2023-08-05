package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;

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

public class UserLogin extends AppCompatActivity {

    Button userRegister,userLogin;
    private EditText email, password;
    DBHelper dbHelper;
    SQLiteDatabase database;

    //Navigates to parent registration page when button clicked
    private void init() {
        userRegister = (Button) findViewById(R.id.Register);
        userRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchParentRegisterScreen();
            }
        });

    }

    // Method to retrieve the user ID from the database based on login credentials


    public int getUserId(String email, String password) {
        // Retrieve the table name and column names from DBHelper
        String tableName = DBHelper.TABLE_NAME2;
        String emailColumnName = DBHelper.USER_EMAIL;
        String passwordColumnName = DBHelper.USER_PASSWORD;
        String parentIdColumnName = DBHelper.USER_ID;

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
        Intent intent1 = new Intent(UserLogin.this, UserRegister.class);
        startActivity(intent1);
    }


    private boolean isValidCredentials(String email, String password) {
        DBHelper dbHelper =  new DBHelper(this);
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String selection = DBHelper.USER_EMAIL + " = ? AND " + DBHelper.USER_PASSWORD + " = ?";
        String[] selectionArgs ={email, password};

        Cursor cursor = db.query(DBHelper.TABLE_NAME2,
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);
        init();

        email =findViewById(R.id.et_email);
        password =findViewById(R.id.et_password);
        userLogin =findViewById(R.id.btn_login);

        dbHelper = new DBHelper(this); // Instantiate the DBHelper class
        database = dbHelper.getReadableDatabase(); // Assign the SQLiteDatabase object

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail =email.getText().toString().trim();
                String getPassword = password.getText().toString().trim();
                // Perform validation on email and password
                if (TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)) {
                    Toast.makeText(UserLogin.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                int userId= getUserId(getEmail,getPassword);
                // Check the credentials against the database or API
                if (isValidCredentials(getEmail, getPassword)) {
                    // Login successful, navigate to the main activity or home screen
                Intent intent1 = new Intent();
                    intent1 = new Intent(UserLogin.this, UserDashboard.class);
                    intent1.putExtra("USER_ID",userId);
                    startActivity(intent1);
                    finish();
                } else {
                    // Login failed, display an error message
                    Toast.makeText(UserLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    }
