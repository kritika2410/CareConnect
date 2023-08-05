package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class ParentDashboard extends AppCompatActivity {

    LinearLayout review, notification, home, profile;
    Button inbox, addJob, editProfile,contactUs, history, logout;
    TextView parent;
    private void init() {
        greetUser();
        inbox = (Button) findViewById(R.id.inbox);
        addJob = (Button) findViewById(R.id.addJob);
        editProfile = (Button) findViewById(R.id.editProfile);
        history = (Button) findViewById(R.id.history);
        logout = (Button) findViewById(R.id.logout);
        review = findViewById(R.id.review);
        notification=findViewById(R.id.notification);
        home =findViewById(R.id.home);
        profile = findViewById(R.id.profileLayout);
        contactUs =findViewById(R.id.contactus);

        parent = findViewById(R.id.parent);
        //parent.setText(getIntent().getIntExtra("USER_ID", -1));
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }

        ImageView profileImageView = findViewById(R.id.imageView12);
        DBHelper dbHelper = new DBHelper(this);

        // Get the parent image as a byte array from the database
        Bitmap parentImage = dbHelper.getParentImage(userId);

        if (parentImage != null) {
            // If the parent image is available, set it to the imageView12
            profileImageView.setImageBitmap(parentImage);
        } else {
            // If the parent image is not available, set the default profile image
            profileImageView.setImageResource(R.drawable.profile);
        }

        // Inside the onCreate() method of MainActivity
        //  int parentId = Integer.parseInt(getIntent().getStringExtra("parentId")); // -1 is the default value if the parentId is not found
        //Parent parentObj =(Parent)getIntent().getSerializableExtra("PARENT_DETAILS");

        // Use the parentId as needed in the MainActivity
        // parent = findViewById(R.id.parent);
        //parent.setText("Parent ID: " + parentObj.getParentId());

        //Navigates to notification screen
        inbox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                launchNotification();
            }
        });
        //Navigates to notification screen
        notification.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                launchNotification();
            }
        });

        //Navigates to Add job page
        addJob.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                launchAddJob();
            }
        });
       //Navigates to edit profile screen
        editProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                launchEditProfile();
            }
        });
        //Navigates to edit profile screen
        profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                launchEditProfile();
            }
        });
        //Navigates to history page shows all job posted by parent
        history.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                launchHistory();
            }
        });
        //exit
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                logoutScreen();
            }
        });

        //Navigates to review screen
        review.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                launchReview();
            }
        });

        //Navigates to home screen
        home.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                launchHome();
            }
        });

        //Navigates to review screen
        contactUs.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                launchContact();
            }
        });



    }

    private void launchHome() {
        Intent intent9 = new Intent(ParentDashboard.this, ParentDashboard.class);
        startActivity(intent9);
    }


    private void launchContact() {
        Intent intent8 = new Intent(ParentDashboard.this, ParentContactUs.class);
        startActivity(intent8);
    }


    private void logoutScreen() {
        Intent intent1 = new Intent(ParentDashboard.this, ParentLogin.class);
        startActivity(intent1);
    }

    private void launchHistory() {
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }
        Intent intent2 = new Intent(ParentDashboard.this, ParentHistory.class);
        intent2.putExtra("USER_ID",userId);
        startActivity(intent2);
    }

    private void launchEditProfile() {
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }
        Intent intent3 = new Intent(ParentDashboard.this, ParentEditProfile.class);
        intent3.putExtra("USER_ID",userId);

        startActivity(intent3);
    }

    private void launchAddJob() {
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }
        Intent intent4 = new Intent(ParentDashboard.this, ParentPosting.class);
        intent4.putExtra("USER_ID",userId);

        startActivity(intent4);
    }

    private void launchNotification() {
        Intent intent5 = new Intent(ParentDashboard.this, ParentInbox.class);
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }

        intent5.putExtra("USER_ID",userId);
        startActivity(intent5);
    }

    private void launchReview() {
        Intent intent6 = new Intent(ParentDashboard.this, ParentReview.class);
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }

        intent6.putExtra("USER_ID",userId);
        startActivity(intent6);
    }

    private void greetUser() {
        TextView greetingTextView = findViewById(R.id.greeting);

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (hourOfDay >= 0 && hourOfDay < 12) {
            // Good Morning
            greetingTextView.setText("Good Morning!");
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            // Good Afternoon
            greetingTextView.setText("Good Afternoon!");
        } else {
            // Good Evening
            greetingTextView.setText("Good Evening!");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        init();


    }
}