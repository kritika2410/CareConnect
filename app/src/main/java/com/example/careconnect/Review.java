package com.example.careconnect;

public class Review {
    private int reviewId;
    private int parentId;
    private int userId;
    private int jobId;
    private int stars;
    private String reviewDetail;

    public Review(){
        this.reviewId = reviewId;
        this.parentId = parentId;
        this.userId = userId;
        this.jobId = jobId;
        this.stars = stars;
        this.reviewDetail = reviewDetail;
    }

    public Review(int reviewId, int parentId, int userId, int jobId, int stars, String reviewDetail) {
        this.reviewId = reviewId;
        this.parentId = parentId;
        this.userId = userId;
        this.jobId = jobId;
        this.stars = stars;
        this.reviewDetail = reviewDetail;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
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

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getReviewDetail() {
        return reviewDetail;
    }

    public void setReviewDetail(String reviewDetail) {
        this.reviewDetail = reviewDetail;
    }
}
