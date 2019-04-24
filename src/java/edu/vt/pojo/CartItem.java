/*
 * Created by Dhoha Abid on 2019.04.23  * 
 * Copyright © 2019 Dhoha Abid. All rights reserved. * 
 */
package edu.vt.pojo;


public class CartItem {
    /*
    ================================
    Instance Variables (Properties)
    ================================
    */
    private MenuItem menuItem;
    private int qty;

 
    /*
    ====================
    Constructor Method
    ====================
    */
    public CartItem(MenuItem menuItem, int qty){
        this.menuItem = menuItem;
        this.qty = qty;
    }
    
    /*
    ==========================
    Getter and Setter Methods
    ==========================
    */
       public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    
    
}
