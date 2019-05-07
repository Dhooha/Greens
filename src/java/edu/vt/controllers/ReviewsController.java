/*
 * Created by Joshua Detwiler on 2019.05.07  * 
 * Copyright 2019 Joshua Detwiler. All rights reserved. * 
 */
package edu.vt.controllers;

import edu.vt.globals.Methods;
import edu.vt.pojo.Review;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

@Named("reviewsController")
@SessionScoped
public class ReviewsController implements Serializable {
    private List<Review> reviewItems;
    private Review selected;

    public ReviewsController() {
    }

    public List<Review> getReviewItems() {
        getReviews();
        return reviewItems;
    }

    public void setReviewItems(List<Review> reviewItems) {
        this.reviewItems = reviewItems;
    }

    public Review getSelected() {
        return selected;
    }

    public void setSelected(Review selected) {
        this.selected = selected;
    }
    
    
    
    public void getReviews() {
        reviewItems = new ArrayList<>();
        String curlResult = "";
        
        try {
            curlResult = Methods.readCurlContent("curl -H \"Authorization: Bearer bYawKdGh55d5rP2rw-f686PVL0Ga4kKCkSkqdE1eHAr9ZHG_ramRiFI_Qg0t6jx8hJWypABm0xlOoOuAJXc-9TKmT6hW9jlsJr_FGsDKlYT3-1lUQlXT_8aiTvaoXHYx\" https://api.yelp.com/v3/businesses/YKWQbP6B13LT60j9tEEtIg/reviews");
        }
        catch (Exception e) {
            System.out.println("Curl exception!");
            return;
        }
        System.out.println(curlResult);
        if (curlResult.isEmpty()) {
            System.out.println("The curl return value was empty!");
            return;
        }
        
        
        
        JSONObject jsonReviews = new JSONObject(curlResult);
        JSONArray reviewsList = jsonReviews.getJSONArray("reviews");
        for (int i = 0; i < reviewsList.length(); i++) {
            JSONObject reviewObject = reviewsList.getJSONObject(i);
            String rating = Integer.toString(reviewObject.getInt("rating"));
            String reviewText = reviewObject.getString("text");
            String timeCreated = reviewObject.getString("time_created");
            String ratingUrl = reviewObject.getString("url");
            
            JSONObject userObject = reviewObject.getJSONObject("user");
            String name = userObject.getString("name");
            String userPhotoUrl = userObject.getString("image_url");
            
            Review review = new Review(i, name, rating, reviewText, timeCreated, userPhotoUrl, ratingUrl);
            reviewItems.add(review);
        }
        
    }
    
}
