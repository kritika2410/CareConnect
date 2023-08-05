package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegister extends AppCompatActivity {

    private EditText etFullName, etEmail, etPassword;


    DBHelper userDBHelper;
    boolean insertStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        etFullName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        userDBHelper = new DBHelper(this);


        Button btnRegister = findViewById(R.id.signUp);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User userObj =createUser();

                AlertDialog.Builder builder = new AlertDialog.Builder(UserRegister.this);
                builder.setTitle("Terms and Conditions");
                builder.setMessage("Please read and accept the terms and conditions.User Eligibility:\n" +
                        "You must be at least 18 years old to use this app. By using the app, you represent and warrant that you are 18 years of age or older.\n" +
                        "\n" +
                        "Intellectual Property:\n" +
                        "All content and materials available in the app, including but not limited to text, graphics, logos, images, and software, are the property of the app developer or its licensors and are protected by intellectual property laws.\n" +
                        "\n" +
                        "Prohibited Conduct:\n" +
                        "You agree not to use the app for any unlawful or unauthorized purpose. This includes, but is not limited to, posting or transmitting any content that is harmful, offensive, or violates the rights of others.By using this app, you agree that you are solely responsible for any actions or content you post or transmit through the app. Any misuse, violation of our terms, or unlawful activities may result in termination of your account and legal action as per applicable laws\n" +
                        "\n" +
                        "Privacy Policy:\n" +
                        "Our Privacy Policy explains how we collect, use, and disclose information about our users. By using the app, you consent to our collection and use of your information as described in the Privacy Policy.\n" +
                        "\n" +
                        "Disclaimer of Warranties:\n" +
                        "The app and its content are provided \"as is\" without any warranties of any kind. We do not guarantee the accuracy, completeness, or reliability of the app or its content.\n" +
                        "\n" +
                        "Limitation of Liability:\n" +
                        "In no event shall the app developer or its affiliates be liable for any direct, indirect, incidental, special, or consequential damages arising out of or in any way connected with the use of the app.\n" +
                        "\n" +
                        "Changes to Terms:\n" +
                        "We reserve the right to modify or update these terms and conditions at any time without prior notice. Continued use of the app after any changes constitutes acceptance of the modified terms.\n" +
                        "\n" +
                        "Governing Law:\n" +
                        "These terms and conditions shall be governed by and construed in accordance with the laws of Ontario, without regard to its conflict of law principles.");

                builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        insertStatus= userDBHelper.InsertUser(userObj);
                        if(insertStatus)
                        {
                            Intent intent1 = new Intent();
                            intent1 = new Intent(UserRegister.this, UserLogin.class);
                            startActivity(intent1);
                            Toast.makeText(UserRegister.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserRegister.this, "Failed to register parent", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        builder.show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }

    public User createUser() {
        User user = new User();
        int userId=1;
        user.setUserId(userId);
        user.setName(etFullName.getText().toString().trim());
        user.setEmail(etEmail.getText().toString().trim());
        user.setPassword(etPassword.getText().toString().trim());
        return user;
    }
    }
