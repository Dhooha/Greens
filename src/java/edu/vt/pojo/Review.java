/*
 * Created by Joshua Detwiler on 2019.05.07  * 
 * Copyright 2019 Joshua Detwiler. All rights reserved. * 
 */
package edu.vt.pojo;

public class Review {
    private Integer id;
    private String name;
    private String rating;
    private String reviewText;
    private String timeCreated;
    private String userPhotoUrl;
    private String ratingUrl;

    public Review() {
    }

    public Review(Integer id, String name, String rating, String reviewText, String timeCreated, 
            String userPhotoUrl, String ratingUrl) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.reviewText = reviewText;
        this.timeCreated = timeCreated;
        this.userPhotoUrl = userPhotoUrl;
        this.ratingUrl = ratingUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getRatingUrl() {
        return ratingUrl;
    }

    public void setRatingUrl(String ratingUrl) {
        this.ratingUrl = ratingUrl;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }
    
        
    
}
