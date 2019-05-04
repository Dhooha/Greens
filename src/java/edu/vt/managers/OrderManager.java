/*
 * Created by Melanie Trammell on 2019.04.16  * 
 * Copyright © 2019 Melanie Trammell. All rights reserved. * 
 */
package edu.vt.managers;

import edu.vt.EntityBeans.Orders;
import edu.vt.FacadeBeans.OrdersFacade;
import edu.vt.controllers.OrdersController;
import edu.vt.controllers.CartController;
import edu.vt.controllers.UserController;
import edu.vt.globals.Methods;
import edu.vt.pojo.CartItem;
import edu.vt.pojo.MenuItem;
import edu.vt.EntityBeans.User;
import edu.vt.FacadeBeans.UserFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

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
    
    //some static variables that will be editable from the place an Order page
    String orderType;
    String specialInstructions;
    boolean notification = false;
    
    //used to update Dababase
    OrdersFacade ejbFacade;
    
    //used to get user who is signed in
    @EJB
    private UserFacade userFacade;
    
    //Inject CartController to help the order manager save the menu items 
    //that are being bought (which are in the cart), to the order
     @Inject CartController cartController;
     
     
    //Inject CartController to help the order manager save the menu items 
    //that are being bought (which are in the cart), to the order
     @Inject OrdersController ordersController;
     
     //Inject CartController to help the order manager save the menu items 
    //that are being bought (which are in the cart), to the order
     @Inject UserController userController;
    
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
    
    private OrdersFacade getFacade() {
        return ejbFacade;
    }
    
    private UserFacade getUserFacade() {
        return userFacade;
    }
    
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }
    
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
    
    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }
    
    
    //TODO: what about phone number? I guess that will be in User... but how 
    //does the system remember to send the text?
    
    //TODO: how does the system remember how it was paid for?
    
    //TODO: wouldn't it be better to call OrderController to create..? won't
    //OrderController have the parameters?
    public String placeOrder() {
        ordersController.prepareCreate();
        Integer primaryKey = (Integer) Methods.sessionMap().get("user_id");
        
        System.out.println("the prim key is" + primaryKey);
        if(getUserFacade() == null){
            System.out.println("MUAUAUA");
        }
        
        User userPlacingOrder = getUserFacade().findById(primaryKey);
        //Integer id, String orderItems, String orderType, Date orderTimestamp, String orderStatus, float orderTotal, String specialInstructions
        Orders o = new Orders();
        
        
        o.setUserId(userPlacingOrder);

        String orderItems = cartController.getSelected().getCartItems();
        o.setOrderItems(orderItems);
        o.setOrderType(orderType);
        
        Date d = new Date(System.currentTimeMillis());
        o.setOrderTimestamp(d);
        
        o.setOrderStatus("PLACED");
        
        //pull pieces of orderItems and get total and place here
        float total = Float.parseFloat(cartController.getTotalAfterTaxes());
        o.setOrderTotal(total);
        
        specialInstructions = "ARE WE STILL USING THIS?";
        o.setSpecialInstructions(specialInstructions);
        
        if(notification){
            o.setTextNotification(true);
            //TODO: must check to make sure user has input phone number
        }
        else{
            o.setTextNotification(false);
        }
        
        //Create an Order Object and then call the OrderFacade create and pass all
        //necessary parameters - it felt weird to recreate what already exists in 
        //ordercontroller... but then selected is already set...
        
        ordersController.setSelected(o);
        
        //TODO - call ordersFacade directly because create is evil!
        ordersController.create();
        
        
        //empty the cart by calling removeAllItemsFromCart() - inject the cart controller
        cartController.removeAllItemsFromCart();
        
        //also update user content
        userController.updateAccount();
        
        return "/orders/OrderHistory?faces-redirect=true";
    }
    
    //manage changing order status
    //TODO: Should pass userid or Order id?
    //probably order id
    public void changeOrderStatus(String newStatus, Integer id){
        Orders o = getFacade().findOrdersbyId(id);
        
        if(newStatus == "READY"){
            //check flag
            //sent text or ask controller to do it
            //TODO
        
        }
        o.setOrderStatus(newStatus);
    }
    
    //Takes in a JSON string and creates from it an Array of CartItems that have
    //corresponding data, and returns it
    //NOTE - selected.cartItems is never null when calling this method
    public static List<CartItem> convertJsonToCartItems(String JSONCartItemData){

        //construct the appropriate array and return it
        List<CartItem> ret = new ArrayList<CartItem>();
        
        //Json Data should look like
        /*
        {
            "cartItems":[
                {"CartItem":
                    {
                        "qty":"#",
                          
                        "MenuItem":{
                            "name":"x",
                            "description":"x",
                            "price":"x",
                            "specialInstructionItems":[
                                "x",
                                "x",
                                "x"...
                            ]
                        }                       
                        
                    }
                },...
            ]
        }
        */
        
        JSONObject dict = new JSONObject(JSONCartItemData);
        
        JSONArray cartItemsJSON = dict.getJSONArray("cartItems");

        if (cartItemsJSON.toString().equals("[]")) {
                System.out.println("it's empty");
                return ret;
        }
        else{
            
            JSONObject cartItemJSONObject = new JSONObject();
            for(int i = 0; i < cartItemsJSON.length(); i++){

                JSONObject unwrap = cartItemsJSON.getJSONObject(i);

                String cartItemData = unwrap.optString("CartItem", "");
                JSONObject cartItemObject = new JSONObject(cartItemData);
                
                //get qty
                int qty = Integer.parseInt(cartItemObject.optString("qty", ""));
                
                //get Menu Item
                String menuItem = cartItemObject.optString("MenuItem", "");
                JSONObject menuItemObject = new JSONObject(menuItem);
                
                //get name
                String name = menuItemObject.optString("name", "");
                
                //get description
                String description = menuItemObject.optString("description", "");
                
                //get price
                double price = menuItemObject.optDouble("price", 0.0);
                
                //get special instructions
                String specialItemInstructionsList = menuItemObject.optString("specialInstructionItems", "");
                List<String> specialInstructionItems = new ArrayList<String>();
                
                String[] specialInstructionString = specialItemInstructionsList.toString().split("\"");
                for(int j = 0; j < specialInstructionString.length; j++){
                    if(j % 2 != 0){
                        specialInstructionItems.add(specialInstructionString[j]);
                    }
                }
                
                //make objects and store in arraylist to return
                MenuItem m = new MenuItem(name, description, price, specialInstructionItems);
                CartItem c = new CartItem(m, qty);
                ret.add(c);
            }
        }
        
        return ret;
    }
    
}
