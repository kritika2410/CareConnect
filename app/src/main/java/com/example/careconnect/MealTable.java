package com.example.careconnect;

public class MealTable {
    private int jobId;
    private int mealId;
    private String mealRequired;
    private String allergy;
    private String instruction;

    public MealTable(){
        this.mealId=mealId;
        this.jobId = jobId;
        this.mealRequired = mealRequired;
        this.allergy = allergy;
        this.instruction = instruction;
    }

    // Add a constructor with arguments
    public MealTable(int jobId, String mealRequired, String allergy, String instruction) {
        this.jobId = jobId;
        this.mealRequired = mealRequired;
        this.allergy = allergy;
        this.instruction = instruction;
    }



    public MealTable(int mealId, int jobId, String mealRequired, String allergy, String instruction) {
        this.mealId=mealId;
        this.jobId = jobId;
        this.mealRequired = mealRequired;
        this.allergy = allergy;
        this.instruction = instruction;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getMealRequired() {
        return mealRequired;
    }

    public void setMealRequired(String mealRequired) {
        this.mealRequired = mealRequired;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
