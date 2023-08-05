package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ParentContactUs extends AppCompatActivity {

    EditText edtName, edtEmail, edtPhone, edtMessage;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_contact_us);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtDate);
        edtPhone = findViewById(R.id.edtphoneNo);
        edtMessage = findViewById(R.id.address);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Set an OnClickListener to the Submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input from the EditText fields
                String name = edtName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String message = edtMessage.getText().toString().trim();

                // Validate the input (you can add more validation if required)

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || message.isEmpty()) {
                    Toast.makeText(ParentContactUs.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // If input is valid, create a new ContactUsDetails object
                    UserContactUs contactUsDetails = new UserContactUs();
                    contactUsDetails.setName(name);
                    contactUsDetails.setEmail(email);
                    contactUsDetails.setPhone(phone);
                    contactUsDetails.setMessage(message);

                    // Insert the contact us details into the database
                    DBHelper dbHelper = new DBHelper(ParentContactUs.this);
                    long newRowId = dbHelper.InsertContact(contactUsDetails);

                    // Check if the insertion was successful
                    if (newRowId != -1) {
                        Toast.makeText(ParentContactUs.this, "Contact us details added successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent = new Intent(ParentContactUs.this, ParentDashboard.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ParentContactUs.this, "Failed to add contact us details.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public UserContactUs createcontact() {
        UserContactUs contactUs = new UserContactUs();
        int contactId=500;
        contactUs.setName(edtName.getText().toString().trim());
        contactUs.setEmail(edtEmail.getText().toString().trim());
        contactUs.setPhone(edtPhone.getText().toString().trim());
        contactUs.setMessage(edtMessage.getText().toString().trim());
        return contactUs;
    }

}
