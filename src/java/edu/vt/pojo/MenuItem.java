/*
 * Created by Dhoha Abid on 2019.04.20  * 
 * Copyright © 2019 Dhoha Abid. All rights reserved. * 
 */
package edu.vt.pojo;

import java.util.List;
import java.lang.StringBuilder;


public class MenuItem {
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
    */
    private String name;
    private String description;
    private double price;
    private List<String> specialInstructionItems;

    /*
    ===================
    Constructor Method
    ===================
    */
    public MenuItem(String name, String description, Double price, List<String> specialInstructionItems){
        this.name = name;
        this.description = description;
        this.price = price;
        this.specialInstructionItems = specialInstructionItems;
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
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public List<String> getSpecialInstructionItems() {
        return specialInstructionItems;
    }

    public void setSpecialInstructionItems(List<String> specialInstructionItems) {
        this.specialInstructionItems = specialInstructionItems;
    }

    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        for(int i = 0; i < this.specialInstructionItems.size(); i++){
            b.append("\"");
            b.append(this.specialInstructionItems.get(i));
            b.append("\"");
            if(i != (this.specialInstructionItems.size() - 1)){
                b.append(",");
            }
        }
        String si = b.toString();
        return  "\"MenuItem\":" +
                    "{" +
                        "\"name\":"         + "\"" + this.name  + "\"," +
                        "\"description\":"  + "\"" + this.description +  "\"," +
                        "\"price\":"        + "\"" + this.price + "\"," +
                        "\"specialInstructionItems\":" +
                            "[" + 
                                si + 
                            "]" +
                    "}";
    }
}
