/*
 * Created by Dhoha Abid on 2019.04.20  * 
 * Copyright © 2019 Dhoha Abid. All rights reserved. * 
 */
package edu.vt.pojo;

import java.util.List;


public class MenuItem {
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
    */
    String name;
    String description;
    Double price;
    List<SpecialInstruction> specialInstructionItems;

    /*
    ===================
    Constructor Method
    ===================
    */
    public MenuItem(String name, String description, Double price, List<SpecialInstruction> specialInstructionItems){
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
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public List<SpecialInstruction> getSpecialInstructionItems() {
        return specialInstructionItems;
    }

    public void setSpecialInstructionItems(List<SpecialInstruction> specialInstructionItems) {
        this.specialInstructionItems = specialInstructionItems;
    }

    
}
