package edu.vt.controllers;

import edu.vt.EntityBeans.Cart;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.FacadeBeans.CartFacade;
import edu.vt.globals.Methods;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("cartController")
@SessionScoped
public class CartController implements Serializable {

    @EJB
    private edu.vt.FacadeBeans.CartFacade ejbFacade;
    private List<Cart> items = null;
    
    //Cart starts as null, but is instantiated when user logs in or
    //guest starts to use cart, then stays saved here for duration of the
    //sesstion
    private Cart selected = null;

    public CartController() {
    }

    public Cart getSelected() {
        return selected;
    }

    public void setSelected(Cart selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CartFacade getFacade() {
        return ejbFacade;
    }

    public Cart prepareCreate() {
        selected = new Cart();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CartCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CartUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CartDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    //There should only be one Cart per User
    public List<Cart> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Cart getCart(java.lang.Integer id) {
        return getFacade().find(id);
    }
    
    public Cart getUserCart(Integer id){
        List<Cart> carts= getFacade().findCartsbyUserId(id);
        //should only be one cart
        return carts.get(0);
    }

    //There should only be one Cart per User
    public List<Cart> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    //There should only be one Cart per User
    public List<Cart> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Cart.class)
    public static class CartControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CartController controller = (CartController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cartController");
            return controller.getCart(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cart) {
                Cart o = (Cart) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cart.class.getName()});
                return null;
            }
        }

    }
    
    //Above this is automatically generated Code **********************************************************************************
    
    //Updates selectd Cart, and returns a positive integer if the 
    //user is signed in and negative 1 if they are not
    public Integer check(){
    
        Integer primaryKey = (Integer) Methods.sessionMap().get("user_id");
        if(primaryKey != null){         
            if(selected == null){
             //user is signed in AND cart is null
            selected = getUserCart(primaryKey);
            }
            return primaryKey;
        }
        else{
            if(selected == null){
            //user is not signed in AND cart is null
            selected = new Cart();
            }
            return -1;
        }
        
    }
    
    //NOTE: only call if creating a user!
    //calls the cart facade to create a cart in the DB table for this user id 
    /*
    public void createCart(int user_id){
        //possibly use create()
        selected.setUserId(user_id);
        create();
    }*/

    //Checks if the user is logged in or not to see if we need to update the database,
    //Update the user's cart by adding the menu item and quantity to Cart JSON String
    /* 
    public void addItemToCart(MenuItem item, int quantity){ 
        
        int c = check();
        if(c >= 0){
    
            //update selected with the new Menu item and quantity by updating the 
            //json string
            //TODO
    
            //push to database aka call update()
            //update();
        }
        else{
           //update selected cart object, not DB
           //TODO
        }
    }*/
    
    //Checks if the user is logged in or not to see if we need to update the database,
    //changes Json string inside Cart to edit Item
    //also can’t set it to zero, it is about editing the quantity
    /*
    public void editItemInCart(MenuItem item, int newQuantity){
        int c = check();
        if(c >= 0){
    
            //update selected with the new Menu item and quantity by updating the 
            //json string
            //TODO
    
            //push to database aka call update()
            //update();
        }
        else{
           //update selected cart object, not DB
           //TODO
        }    
    }*/
    
    //Checks if the user is logged in or not to see if we need to update the database
    //Removes specified item from JSON string in Cart
    /*
    public void removeItemFromCart(MenuItem item){
        int c = check();
        if(c >= 0){
    
            //update selected with the new Menu item and quantity by updating the 
            //json string
            //TODO
    
            //push to database aka call update()
            //update();
        }
        else{
           //update selected cart object, not DB
           //TODO
        }  
    }*/
    
    //Removes the Json string items from the cart object as well as the cart table  
    //(no need to check because the user will be logged in)
    public void removeAllItemsFromCart(){

        //Integer primaryKey = (Integer) Methods.sessionMap().get("user_id");
        //selected = getUserCart(primaryKey);
        //remove items from cart
        //TODO: keep consistent with JSON Format
        //selected.setCartItems(" ");
        //update();
    }
    
    //checks if the user is logged in, if yes, it redirect to the order page, 
    //otherwise, it will ask the user to login, and redirect the user to the 
    //login/create user page.
    public void checkOutCart(){
        
        //if the user is logged in
        /*if(Methods.sessionMap().get("username") != null;){
            removeAllItemsFromCart();
        
            //redirect to order page
            return "/orders/OrderHistory.xhtml?faces-redirect=true";
        }
        else{
            //redirect to login page
            return "/SignIn.xhtml?faces-redirect=true";
        
        }*/
        
    }
}
