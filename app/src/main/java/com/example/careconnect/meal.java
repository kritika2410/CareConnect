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

public class meal extends AppCompatActivity {

    EditText allerges, instruction;
    RadioGroup meal;
    RadioButton yes, no;
    String value;
    DBHelper dbhelper;
    Button btnSubmit;
    boolean insertStatus;
    TextView job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        dbhelper = new DBHelper(this);
        allerges = findViewById(R.id.edtAllergy);
        instruction = findViewById(R.id.edtInstructions);
        meal = (RadioGroup) findViewById(R.id.meal);
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);
        btnSubmit = findViewById(R.id.btnSubmit);
        job = findViewById(R.id.jobId);

        int jobId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("jobId")) {
            jobId = extras.getInt("jobId", -1);
        }
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null && extras.containsKey("userId")) {
            userId = extras.getInt("userId", -1);
        }



        yes.setChecked(false);
        no.setChecked(false);

        meal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
                MealTable mealObj = addMealDetails();
                insertStatus = dbhelper.InsertMeal(mealObj);
                if(insertStatus)
                {
                   MealTable meal = new MealTable();
                    Toast.makeText(meal.this, "Meal added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(meal.this, activityDetails.class);
                    intent1.putExtra("jobId",mealObj.getJobId());
                    Bundle extras1 = getIntent().getExtras();
                    int userId = -1;
                    if (extras1 != null && extras1.containsKey("userId")) {
                        userId = extras1.getInt("userId", -1);
                    }
                    intent1.putExtra("userId",userId);
                    startActivity(intent1);
                } else {
                    Toast.makeText(meal.this, "Failed to add meal details", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public MealTable addMealDetails() {
        MealTable mealObj = new MealTable();
        int job = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("jobId")) {
            job = extras.getInt("jobId", -1);
            Log.d("DEBUG", "Job id "+ job);
        }

        int mealId = 1;
        mealObj.setMealId(mealId);
        Log.d("DEBUG", "Meal ID: " + mealId);
        mealObj.setJobId(job);
        Log.d("DEBUG", "Job ID: " + job);
        mealObj.setMealRequired(value);
        Log.d("DEBUG", "Special kid: " + value);
        mealObj.setAllergy(allerges.getText().toString().trim());
        Log.d("DEBUG", "Allerges: " + allerges.getText().toString().trim());
        mealObj.setInstruction(instruction.getText().toString().trim());
        Log.d("DEBUG", "Instruction: " + instruction.getText().toString().trim());

        return mealObj;
    }
}