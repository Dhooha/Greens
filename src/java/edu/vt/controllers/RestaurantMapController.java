/*
 * Created by Joshua Detwiler on 2019.05.06  * 
 * Copyright � 2019 Joshua Detwiler. All rights reserved. * 
 */
package edu.vt.controllers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Named("restaurantMapController")
@SessionScoped
public class RestaurantMapController implements Serializable {
    
    private MapModel model;
    private Marker restaurantMarker;
    private Marker squiresParkingMarker;
    private Marker annexParkingMarker;
    private Marker roadsideParkingMarker;
    
    public RestaurantMapController() {
        model = new DefaultMapModel();
        restaurantMarker = new Marker(new LatLng(37.2303339,-80.4155607), "Green's Grill & Sushi Bar");
        restaurantMarker.setTitle("Green's Grill & Sushi Bar");
        
        squiresParkingMarker = new Marker(new LatLng(37.2291353,-80.4168983), "Parking at Squires");
        squiresParkingMarker.setTitle("Parking at Squires");
        
        annexParkingMarker = new Marker(new LatLng(37.229020, -80.415628), "Parking at the Architecture Annex");
        annexParkingMarker.setTitle("Parking at the Architecture Annex");
        
        roadsideParkingMarker = new Marker(new LatLng(37.229541, -80.415644), "Parking on Draper Road");
        roadsideParkingMarker.setTitle("Parking on Draper Road");
        
        model.addOverlay(restaurantMarker);
        model.addOverlay(squiresParkingMarker);
        model.addOverlay(annexParkingMarker);
        model.addOverlay(roadsideParkingMarker);
    }
    
    public MapModel getModel() { 
        return this.model;
    }

    public Marker getRestaurantMarker() {
        return restaurantMarker;
    }
    
    public void onMarkerSelect(OverlaySelectEvent event) {
        this.restaurantMarker = (Marker) event.getOverlay();
    }
}
