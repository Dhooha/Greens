/*
 * Created by Melanie Trammell on 2019.04.16  * 
 * Copyright © 2019 Melanie Trammell. All rights reserved. * 
 */
package edu.vt.managers;

import edu.vt.EntityBeans.Orders;
import edu.vt.controllers.CartController;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Melanie
 */

@Named(value = "orderManager")
@SessionScoped
public class OrderManager implements Serializable {
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    
    
    //TODO: enum
    String orderType;
    
    //nject CartController to help the order manager save the menu items 
    //that are being bought (which are in the cart), to the order
     @Inject CartController cartController;
    
    //TODO: need POJO Class
    // private List<Menu> orderItems = null;
    
    /*
    ==================
    Constructor Method
    ==================
     */
    public OrderManager(){
    }

    /*
    ================
    Instance Methods
    ================
*
     */
    
    //TODO: wouldn't it be better to call OrderController to create..? won't
    //OrderController have the parameters?
    //Create an Order Object and then call the OrderFacade create and pass all
    //necessary parameters
    //empty the cart by calling removeAllItemsFromCart() - inject the cart controller
    public void placeOrder() {
    }
    


    
}
