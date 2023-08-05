package com.example.careconnect;

import android.util.Log;

public class ActivityTable {

    private int jobId;
    private int activityId;
    private String activityrequired;
    private String activity;
    private String instruction;

    public ActivityTable(){
        this.jobId = jobId;
        this.activityId = activityId;
        this.activityrequired = activityrequired;
        this.activity = activity;
        this.instruction = instruction;
    }
    public ActivityTable(int jobId, int activityId, String activityrequired, String activity, String instruction) {
        this.jobId = jobId;
        this.activityId = activityId;
        this.activityrequired = activityrequired;
        this.activity = activity;
        this.instruction = instruction;
    }

    public ActivityTable(int jobId, String activityrequired, String activity, String instruction) {
        Log.d("ActivityTable", "ActivityTable Constructor: Job ID: " + jobId);
        Log.d("ActivityTable", "ActivityTable Constructor: Activity Required: " + activityrequired);
        Log.d("ActivityTable", "ActivityTable Constructor: Activity Details: " + activity);
        Log.d("ActivityTable", "ActivityTable Constructor: Instruction: " + instruction);


        this.jobId = jobId;
        this.activityrequired = activityrequired;
        this.activity = activity;
        this.instruction = instruction;
    }


    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityrequired() {
        return activityrequired;
    }

    public void setActivityrequired(String activityrequired) {
        this.activityrequired = activityrequired;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
