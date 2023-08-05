package com.example.careconnect;

import android.widget.DatePicker;

import java.time.DateTimeException;
import java.util.Calendar;

public class AddJob {
    private int jobId;
    private int parentId;
    private int userId;
    private String title;
    private Calendar jobDate;
    private String jobTime;
    private int totalHours;
    private int payPerHour;
    private String address;
    private int noOfKids;
    private String specialKids;
    private int flag;
    private User user;

    public AddJob()
    {
        this.jobId = jobId;
        this.parentId = parentId;
        this.userId = userId;
        this.title = title;
        this.jobDate = null;
        this.jobTime = "";
        this.totalHours = totalHours;
        this.payPerHour = payPerHour;
        this.address = address;
        this.noOfKids = noOfKids;
        this.specialKids = specialKids;
        this.flag = flag;
    }

    public AddJob(int jobId, int parentId, int userId, String title, Calendar jobDate, String jobTime, int totalHours, int payPerHour, String address, int noOfKids, String specialKids, int flag) {
        this.jobId = jobId;
        this.parentId = parentId;
        this.userId = userId;
        this.title = title;
        this.jobDate = jobDate;
        this.jobTime = jobTime;
        this.totalHours = totalHours;
        this.payPerHour = payPerHour;
        this.address = address;
        this.noOfKids = noOfKids;
        this.specialKids = specialKids;
        this.flag = flag;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getJobDate() {
        return jobDate;
    }

    public void setJobDate(Calendar jobDate) {
        this.jobDate = jobDate;
    }

    public String getJobTime() {
        return jobTime;
    }

    public void setJobTime(String jobTime) {
        this.jobTime = jobTime;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getPayPerHour() {
        return payPerHour;
    }

    public void setPayPerHour(int payPerHour) {
        this.payPerHour = payPerHour;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNoOfKids() {
        return noOfKids;
    }

    public void setNoOfKids(int noOfKids) {
        this.noOfKids = noOfKids;
    }

    public String getSpecialKids() {
        return specialKids;
    }

    public void setSpecialKids(String specialKids) {
        this.specialKids = specialKids;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

}
