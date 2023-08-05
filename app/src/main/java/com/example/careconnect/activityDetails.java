package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class activityDetails extends AppCompatActivity {

    EditText activityList, instruction;
    RadioGroup chooseActivity;
    RadioButton yes, no;
    String value;
    DBHelper dbhelper;
    Button btnSubmit;
    boolean insertStatus;
    TextView job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle extras1 = getIntent().getExtras();

        dbhelper = new DBHelper(this);
        activityList = findViewById(R.id.edtActivity);
        instruction = findViewById(R.id.edtInstruction);
        chooseActivity = (RadioGroup) findViewById(R.id.activity);
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);
        btnSubmit = findViewById(R.id.btnSubmit);
        job = findViewById(R.id.jobId);

        int jobId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("jobId")) {
            jobId = extras.getInt("jobId", -1);
        }



        yes.setChecked(false);
        no.setChecked(false);

        chooseActivity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = radioGroup.findViewById(i);
                if (rb.getId() == R.id.yes) {
                    value = yes.isChecked() ? "yes" : "no";
                    Log.d("DEBUG", "Parent ID: " + value);
                } else if (rb.getId() == R.id.no) {
                    value = value = no.isChecked() ? "no" : "yes";
                    Log.d("DEBUG", "Parent ID: " + value);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityTable actObj = addActivityDetails();
                insertStatus = dbhelper.InsertActivity(actObj);
                if (insertStatus) {
                    ActivityTable activity = new ActivityTable();
                    Toast.makeText(activityDetails.this, "Activity added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(activityDetails.this, ParentDashboard.class);
                    int userId = -1;
                    if (extras1 != null && extras1.containsKey("userId")) {
                        userId = extras1.getInt("userId", -1);
                    }
                    intent1.putExtra("userId",userId);
                    startActivity(intent1);
                } else {
                    Toast.makeText(activityDetails.this, "Failed to add meal details", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public ActivityTable addActivityDetails() {
        ActivityTable activityObj = new ActivityTable();
        int job = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("jobId")) {
            job = extras.getInt("jobId", -1);
            Log.d("DEBUG", "Job id " + job);
        }

        int activityId = 1;
        activityObj.setActivityId(activityId);
        Log.d("DEBUG", "Activity ID: " + activityId);
        activityObj.setJobId(job);
        Log.d("DEBUG", "Job ID: " + job);
        activityObj.setActivityrequired(value);
        Log.d("DEBUG", "Special kid: " + value);
        activityObj.setActivity(activityList.getText().toString().trim());
        Log.d("DEBUG", "Allerges: " + activityList.getText().toString().trim());
        activityObj.setInstruction(instruction.getText().toString().trim());
        Log.d("DEBUG", "Instruction: " + instruction.getText().toString().trim());

        return activityObj;
    }
}