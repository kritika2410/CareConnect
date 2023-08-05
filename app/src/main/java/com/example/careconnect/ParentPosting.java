package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ParentPosting extends AppCompatActivity {

    EditText title, jobDate, jobTime, address, hours, noOfKids, pay;
    RadioGroup specialKid;
    RadioButton yes, no;
    String value;
    DBHelper dbhelper;
    Button btnSubmit;
    boolean insertStatus;
    TextView parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_posting);

        title = findViewById(R.id.edtTitle);
        jobDate = findViewById(R.id.edtDate);
        jobTime = findViewById(R.id.edtTime);
        address = findViewById(R.id.address);
        hours = findViewById(R.id.hours);
        noOfKids = findViewById(R.id.noOfKids);
        pay = findViewById(R.id.payPerHour);
        specialKid = (RadioGroup) findViewById(R.id.specialKid);
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);
        btnSubmit = findViewById(R.id.btnSubmit);
        dbhelper = new DBHelper(this);



        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                jobDate.setText(selectedDate);
            }
        };

        jobTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        jobDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the DatePickerDialog to select the date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ParentPosting.this, dateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

        yes.setChecked(false);
        no.setChecked(false);

        specialKid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
                if (areFieldsNotEmpty()) {
                    int payPerHourValue = Integer.parseInt(pay.getText().toString());
                    int totalHoursValue = Integer.parseInt(hours.getText().toString());
                    int totalPay = payPerHourValue * totalHoursValue;

                    if (payPerHourValue < 16) {
                        showPayPerHourAlert();
                    } else {
                        if (isPreviousDateSelected(jobDate.getText().toString())) {
                            showPreviousDateAlert();
                        } else {
                            showConfirmationDialog(payPerHourValue, totalPay);
                        }
                    }
                } else {
                    Toast.makeText(ParentPosting.this, "Please fill all mandatory fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to check if the selected date is a previous date
    private boolean isPreviousDateSelected(String selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date selectedDateObj = dateFormat.parse(selectedDate);
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.setTime(selectedDateObj);

            Calendar currentCalendar = Calendar.getInstance();

            // Compare the selected date with the current date
            return selectedCalendar.before(currentCalendar);
        } catch (ParseException e) {
            e.printStackTrace();
            return true; // In case of any error, consider it as a previous date
        }
    }

    // Method to show AlertDialog for selecting a previous date
    private void showPreviousDateAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invalid Job Date");
        builder.setMessage("Job date cannot be a previous date. Please select a valid date.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Clear the job date EditText and dismiss the AlertDialog
                jobDate.setText("");
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // Method to show AlertDialog with kids, pay per hour, and total pay information
    private void showConfirmationDialog(int payPerHour, int totalPay) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Number of Kids: " + noOfKids.getText().toString() + "\n" +
                "Pay Per Hour: $" + payPerHour + "\n" +
                "Total Pay: $" + totalPay);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Redirect to meal page
                AddJob jobObj = addJob();
                insertStatus = dbhelper.InsertJob(jobObj);
                if (insertStatus) {
                    Intent intent1 = new Intent(ParentPosting.this, meal.class);
                    intent1.putExtra("jobId", jobObj.getJobId());
                    int userId = -1; // Default value if the extra is not found or not an integer

                    Bundle extras = getIntent().getExtras();
                    if (extras != null && extras.containsKey("USER_ID")) {
                        userId = extras.getInt("USER_ID", -1);
                    }
                    intent1.putExtra("userId", userId);
                    Log.d("DEBUG", "Job id is" + jobObj.getJobId());
                    startActivity(intent1);
                } else {
                    Toast.makeText(ParentPosting.this, "Failed to add job", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the AlertDialog
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // Method to show AlertDialog for pay per hour less than 16
    private void showPayPerHourAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Basic Pay");
        builder.setMessage("The average hourly rate in your area is $16.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the AlertDialog when the user clicks OK
                pay.setText("");
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean areFieldsNotEmpty() {
        String titleText = title.getText().toString().trim();
        String jobDateText = jobDate.getText().toString().trim();
        String jobTimeText = jobTime.getText().toString().trim();
        String addressText = address.getText().toString().trim();
        String hoursText = hours.getText().toString().trim();
        String noOfKidsText = noOfKids.getText().toString().trim();
        String payText = pay.getText().toString().trim();

        return !titleText.isEmpty() && !jobDateText.isEmpty() && !jobTimeText.isEmpty() &&
                !addressText.isEmpty() && !hoursText.isEmpty() && !noOfKidsText.isEmpty() &&
                !payText.isEmpty();
    }

    private void showTimePickerDialog() {
        // Get the current time
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                ParentPosting.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Update the jobTime EditText with the selected time
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        jobTime.setText(selectedTime);
                    }
                },
                hour,
                minute,
                true
        );

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

    public AddJob addJob() {
        AddJob job = new AddJob();
        int userId = -1; // Default value if the extra is not found or not an integer

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("USER_ID")) {
            userId = extras.getInt("USER_ID", -1);
        }

        int jobId = 201;
        int flag = -1;
        job.setJobId(jobId);
        Log.d("DEBUG", "Job ID: " + jobId);
        job.setParentId(userId);
        Log.d("DEBUG", "Parent ID: " + userId);
        job.setTitle(title.getText().toString().trim());
        Log.d("DEBUG", "Title: " + title.getText().toString().trim());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String selectedDate = jobDate.getText().toString();
        try {
            Date date = dateFormat.parse(selectedDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            job.setJobDate(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("DEBUG", "Date: " + jobDate.getText().toString().trim());
        job.setJobTime(jobTime.getText().toString().trim());
        Log.d("DEBUG", "Time: " + jobTime.getText().toString().trim());

        job.setAddress(address.getText().toString().trim());
        Log.d("DEBUG", "Address: " + address.getText().toString().trim());
        job.setTotalHours(Integer.parseInt(hours.getText().toString().trim()));
        Log.d("DEBUG", "Total hours: " + hours.getText().toString().trim());
        job.setNoOfKids(Integer.parseInt(noOfKids.getText().toString().trim()));
        Log.d("DEBUG", "No of kids: " + noOfKids.getText().toString().trim());
        job.setPayPerHour(Integer.parseInt(pay.getText().toString().trim()));
        Log.d("DEBUG", "Pay: " + pay.getText().toString().trim());
        job.setSpecialKids(value);
        Log.d("DEBUG", "Special kid: " + value);
        job.setFlag(flag);
        return job;
    }
}
