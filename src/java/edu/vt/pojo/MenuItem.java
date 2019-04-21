/*
 * Created by Dhoha Abid on 2019.04.20  * 
 * Copyright © 2019 Dhoha Abid. All rights reserved. * 
 */
package edu.vt.pojo;

/**
 *
 * @author se
 */

public class MenuItem {
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
    */
    String name;
    String description;
    Double price;

    /*
    ===================
    Constructor Method
    ===================
    */
    public MenuItem(String name, String description, Double price){
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    /*
    ==========================
    Getter and Setter Methods
    ==========================
    */
    
     public String getName() {
        return name;
    }
     
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
}
