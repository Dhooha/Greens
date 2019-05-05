/*
 * Created by Melanie Trammell on 2019.04.16  * 
 * Copyright © 2019 Melanie Trammell. All rights reserved. * 
 */
package edu.vt.managers;

import edu.vt.EntityBeans.Orders;
import edu.vt.EntityBeans.User;
import edu.vt.FacadeBeans.OrdersFacade;
import edu.vt.FacadeBeans.UserFacade;
import edu.vt.controllers.OrdersController;
import edu.vt.controllers.CartController;
import edu.vt.controllers.UserController;
import edu.vt.controllers.TextMessageController;
import edu.vt.globals.Methods;
import edu.vt.pojo.CartItem;
import edu.vt.pojo.MenuItem;
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
import javax.faces.event.ValueChangeEvent;
import java.util.Timer;
import java.util.TimerTask;


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
    
    String displayDelivery = "none";

    //used to update Dababase
    @EJB
    OrdersFacade ejbFacade;
    
    //Inject CartController to help the order manager save the menu items 
    //that are being bought (which are in the cart), to the order
     @Inject CartController cartController;
     
     
    //Inject CartController to help the order manager save the menu items 
    //that are being bought (which are in the cart), to the order
    @Inject OrdersController ordersController;
     
   //Inject UserController to help update user fields
    @Inject
    private UserController userController;
     
    //Inject TextMessageController to help order manager send text message when
    //order is ready
    @Inject
    private TextMessageController textMessageController;
    
    @EJB
    private UserFacade userFacade;
    
    @EJB
    private OrdersFacade ordersFacade;
    
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
    
    public String getDisplayDelivery() {
        return displayDelivery;
    }

    //used to hide delivery form if not needed
    public void setDisplayDelivery(String displayDelivery) {
        this.displayDelivery = displayDelivery;
    }
    
    public UserFacade getUserFacade() {
        return userFacade;
    }
    
    //TODO: what about phone number? I guess that will be in User... but how 
    //does the system remember to send the text?
    
    //TODO: how does the system remember how it was paid for?
    
    //TODO: wouldn't it be better to call OrderController to create..? won't
    //OrderController have the parameters?
    public String placeOrder() {
        
        ordersController.prepareCreate();
        
        Integer primaryKey = (Integer) Methods.sessionMap().get("user_id");
        User userPlacingOrder = getUserFacade().findById(primaryKey);

        //Integer id, String orderItems, String orderType, Date orderTimestamp, String orderStatus, float orderTotal, String specialInstructions
        Orders o = new Orders();
        
        o.setUserId(userPlacingOrder);
        String orderItems = cartController.getSelected().getCartItems();
        o.setOrderItems(orderItems);
        o.setOrderType(orderType);
        o.setOrderStatus("PLACED");
        
        Date d = new Date(System.currentTimeMillis());
        o.setOrderTimestamp(d);
        
        //pull pieces of orderItems and get total and place here
        float f = Float.parseFloat(cartController.getTotalAfterTaxes());
        o.setOrderTotal(f);
        o.setSpecialInstructions(specialInstructions);
        o.setTextNotification(notification);
        
        //Create an Order Object and then call the OrderFacade create and pass all
        ordersController.setSelected(o);
        ordersController.createOrder();
        
        //empty the cart by calling removeAllItemsFromCart() - inject the cart controller
        cartController.removeAllItemsFromCart();
        
        //Update User with fields edite
        userController.updateAccount();
        
        //clean up
        orderType = "";
        specialInstructions = "";
        notification = false;
    
        //Get the order we just made
        Orders changeStatus = getFacade().findOrdersbyUserIdANDTimeStamp(userPlacingOrder.getId(), d);
        changeOrderStatus("READY", changeStatus);
            
        //have to do this here, but normally would be below
        //changeStatus.setOrderStatus("READY");
        //ordersController.setSelected(changeStatus);
        //ordersController.update();
        
        //MyTimerTask timerTask = new MyTimerTask(changeStatus);
 
        //int second = 1000;
        //Timer timer = new Timer();
        //timer.schedule(timerTask, 2 * second);
        
        return "/orders/OrderHistory?faces-redirect=true";
    }
    
    //used to get Order id and call changeOrder status properly
    /*
    class MyTimerTask extends TimerTask  { 
       Orders param;

        public MyTimerTask(Orders param) {
            this.param = param;
        }

        @Override
        public void run() {
            // You can do anything you want with param
            changeOrderStatus("READY", param);
        }  
    }*/
    
    //manage changing order status
    public void changeOrderStatus(String newStatus, Orders o){
        
        System.out.println("in change order status");
        
        if(newStatus == "READY"){
            //check flag
            //sent text or ask controller to do it
             System.out.println("it is ready");
            if(o.getTextNotification()){
                System.out.println("apparently is true");
                //ask textmessate controller to send text
                textMessageController.setCellPhoneNumber(o.getUserId().getPhoneNumber());
                textMessageController.setCellPhoneCarrierDomain(o.getUserId().getPhoneCarrier());
                textMessageController.setMmsTextMessage("Your order is ready.");
                try{
                     System.out.println("trying to send message");
                    textMessageController.sendTextMessage();
                }
                catch(Exception AddressException){
                    System.out.println("Phone Number was invalid");  
                }
            }
        }
        
        //cannot call it here because odersController is no longer actively scoped
        o.setOrderStatus("READY");
        ordersController.setSelected(o);
        ordersController.update();
    }
    
    public void changeDeliveryDisplay(ValueChangeEvent e){
        System.out.println("We did it!" + e.toString());
        if(e.toString().equals("DELIVERY")){
            displayDelivery = "block";
        }
    }
    
    //Takes in a JSON string and creates from it an Array of CartItems that have
    //corresponding data, and returns it
    //NOTE - selected.cartItems is never null when calling this method
    public List<CartItem> convertJsonToCartItems(String JSONCartItemData){

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
