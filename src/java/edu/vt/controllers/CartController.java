package edu.vt.controllers;

import edu.vt.EntityBeans.Cart;
import edu.vt.EntityBeans.User;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.FacadeBeans.CartFacade;
import edu.vt.FacadeBeans.UserFacade;

import edu.vt.globals.Methods;
import edu.vt.pojo.MenuItem;
import edu.vt.pojo.CartItem;
import edu.vt.managers.OrderManager;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.inject.Inject;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.lang.StringBuilder;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import java.lang.Integer;

@Named("cartController")
@SessionScoped
public class CartController implements Serializable{

    @EJB
    private UserFacade userFacade;
    
    @EJB
    private edu.vt.FacadeBeans.CartFacade ejbFacade;
    
    @Inject
    private OrderManager orderManager;
    
    @Inject
    private MenuController menuController;
    
    /*
    ================================
    Attribute Variables (Properties)
    ================================
    */
    
    //Cart is represented with this list to help display its contents and
    //interact with them on the UI
    private List<CartItem> cartItems = null;
    
    private String totalBeforeTaxes = "0.00";
    private String tax = "0.00";
    private String totalAfterTaxes = "0.00";

    
    //Used to delete a Cart Item
    private CartItem selectedCartItem;

    //Used to add a Cart Item to the cart through combining these three
    //fields into a CartItem
    private MenuItem cartSelectedMenuItem;
    private int cartSelectedMenuItemQty;
    private List<String> cartSelectedSpecialInstructionItems;

    //Cart starts as null, but is instantiated when user logs in or
    //guest starts to use cart, then stays saved here for duration of the
    //sesstion
    private Cart selected = null;
    
    //for DB
    private List<Cart> items;

    /*
    ===============================
    Getter and Settter Methods
    ===============================
    */
     public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<String> getCartSelectedSpecialInstructionItems() {
        return cartSelectedSpecialInstructionItems;
    }

    public void setCartSelectedSpecialInstructionItems(List<String> cartSelectedSpecialInstructionItems) {
        this.cartSelectedSpecialInstructionItems = cartSelectedSpecialInstructionItems;
    }

    public CartFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(CartFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public MenuItem getCartSelectedMenuItem() {
        return cartSelectedMenuItem;
    }

    public void setCartSelectedMenuItem(MenuItem cartSelectedMenuItem) {
        this.cartSelectedMenuItem = cartSelectedMenuItem;
    }
    
    public int getCartSelectedMenuItemQty() {
        return cartSelectedMenuItemQty;
    }

    public void setCartSelectedMenuItemQty(int cartSelectedMenuItemQty) {
        this.cartSelectedMenuItemQty = cartSelectedMenuItemQty;
    }
    
     public MenuController getMenuController() {
        return menuController;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public CartItem getSelectedCartItem() {
        return selectedCartItem;
    }

    public void setSelectedCartItem(CartItem selectedCartItem) {
        this.selectedCartItem = selectedCartItem;
    } 
    
    public String getTotalBeforeTaxes() {
        return totalBeforeTaxes;
    }

    public void setTotalBeforeTaxes(String totalBeforeTaxes) {
        this.totalBeforeTaxes = totalBeforeTaxes;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTotalAfterTaxes() {
        return totalAfterTaxes;
    }

    //Used to keep track of price to display on cart
    public void setTotalAfterTaxes(String totalAfterTaxes) {
        this.totalAfterTaxes = totalAfterTaxes;
    }

    /*
    =====================
    Constructor Method
    =====================
    */
    
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
    
    /*
    ================================
    Methods generated by the system
    ================================
    */

    protected void initializeEmbeddableKey() {
    }

    private CartFacade getFacade() {
        return ejbFacade;
    }
    
    //used to get user object to save for selected
    private UserFacade getUserFacade(){
        return userFacade;
    
    }
    
    //Using this to create a cart when user makes an account should be okay
    //because selected is only used upon logining in, at which point it will
    //be overridden
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
        //TODO: may need to set selected back to null here
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
                    System.out.println("going to edit selected aka put in DB");
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
    
    /*
    ==================================
    Methods Added by the Team Members
    ==================================
    */
    
    
    public void updateTotalPrice(){
        calculateTotalBeforeTaxes();
        calculateTax();
        calculateTotalAfterTax();
    }
    /*
    -----------------------------------
    Calculate Total Price before Taxes
    This method consider the prices of 
    the added special instructions
    -----------------------------------
    */
    public void calculateTotalBeforeTaxes(){
        // Format to show only 2 decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        
        if ((cartItems == null) || (cartItems.isEmpty())){
            totalBeforeTaxes = "0.00";
        }else{
            try{
                double total = 0.0;
                for (CartItem cartItem: cartItems){
                    // get the qty and price of the cartItem
                    int qty = cartItem.getQty();

                    double price = cartItem.getMenuItem().getPrice();
                    
                    // get the special instruction if there are any
                    List<String> specialInstructionItems = cartItem.getMenuItem().getSpecialInstructionItems();
                    double totalSpecialInsructionItems = 0.0;
                    if ((specialInstructionItems != null) && (specialInstructionItems.isEmpty() == false)){                    
                        for (String specialInstructionItem: specialInstructionItems){
                            double specialInstructionItemPrice = Double.valueOf(specialInstructionItem.split("\\$")[1]);
                            totalSpecialInsructionItems += specialInstructionItemPrice;
                        }
                    }
                // add to the total for every cartItem
                total += qty * (price + totalSpecialInsructionItems);
                }
                totalBeforeTaxes = df.format(total);
            }
            catch(Exception ex){

                totalBeforeTaxes = "0.00";
            }
           
        }
    }
    
    /*
    -------------------------------------
    Calculate Taxes: 11% according to VA
    -------------------------------------
    */
    public void calculateTax(){
        // Format to show only 2 decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        tax = df.format(Double.valueOf(totalBeforeTaxes) * 0.11);
    }
    
    /*
    ----------------------
    Calculate Total after
    ----------------------
    */
    public void calculateTotalAfterTax(){
        // Format to show only 2 decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        totalAfterTaxes = df.format(Double.valueOf(totalBeforeTaxes) + Double.valueOf(tax));
    }
    
    /*
    -----------------------------------
    Check if the cart is empty or null
    -----------------------------------
    */
    public boolean isCartEmpty(){
        if ((cartItems == null) || (cartItems.isEmpty() == true)){
            return true;
        } 
        return false;
    }

    /*
    -------------------------------------------------------------
    Clear the selected MenuItem quantity and special instruction, 
    so they don't show up for the next selections of MenuItems
    -------------------------------------------------------------
    */
    public void clearFields(){ // By Dhoha
        cartSelectedSpecialInstructionItems = null;
        cartSelectedMenuItemQty = 1;
        cartSelectedMenuItem = null;
        selectedCartItem = null;
        
    }
    
    //Retreives/initializes selectd Cart and carItems, and returns a positive integer if the 
    //user is signed in and negative 1 if they are not
    public Integer check(){
    
        Integer primaryKey = (Integer) Methods.sessionMap().get("user_id");
        if(primaryKey != null){         
            if(cartItems == null){
                //user is signed in AND cart is null
               selected = getUserCart(primaryKey);
               //populate CartItems
               cartItems = orderManager.convertJsonToCartItems(selected.getCartItems());
            }
            return primaryKey;
        }
        else{
            if(cartItems == null){
                //user is not signed in AND cart is null
                selected = new Cart();
                cartItems = new ArrayList<>();
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

        /*
    -------------------------------------------------
    This method adds a selected MenuItem to CartItems
    -------------------------------------------------
    */
    public void addMenuItemToCart(){ // By Dhoha
        MenuItem menuSelectedMenuItem = menuController.getSelectedMenuItem();
        if (menuSelectedMenuItem != null){
            
            //if this menuItem already exists in the cart, then update it
            //TODO: how update special instructions?
            for(CartItem c: cartItems){
                if(c.getMenuItem().getName().equals(menuSelectedMenuItem.getName())){
                    //merge quantity
                    int totalQuantity = c.getQty() + cartSelectedMenuItemQty;
                    c.setQty(totalQuantity);
                    
                    //TODO: is there a good arraylist function for this?
                    //special instructions
                    for(String i: c.getMenuItem().getSpecialInstructionItems()){
                        //do with cartSelectedSpecialInstructionItems
                    
                    }
                    return;
                }
            }
            cartSelectedMenuItem = new MenuItem(
                menuSelectedMenuItem.getName()
                , menuSelectedMenuItem.getDescription()
                , menuSelectedMenuItem.getPrice()
                , cartSelectedSpecialInstructionItems);
        }
        cartItems.add(new CartItem(cartSelectedMenuItem, cartSelectedMenuItemQty));
        clearFields();
    }  
    
/*
    -------------------------------------------------
    This method edits a MenuItem in CartItems
    -------------------------------------------------
    */
    
    public void editMenuItemInCart(){ // By Melanie
        //maybe I am being silly and I can just edit selectedCartItem?
        for(CartItem c: cartItems){
           if(c.getMenuItem().getName().equals(selectedCartItem.getMenuItem().getName())){
                    c.setQty(cartSelectedMenuItemQty);
                    clearFields();
                    return;
                }
        }
        clearFields();        
    }  
   
    
    /*
    -------------------------------------------------
    This method edits a MenuItem in CartItems
    -------------------------------------------------
    */
    public void deleteCartItemFromCart(){ // By Melanie
        //either the user is logged in or not
        cartItems.remove(selectedCartItem);
    }  
    
    //Checks if the user is logged in or not to see if we need to update the database,
    //Update the user's cart by adding the menu item and quantity to Cart JSON String
    public void addItemToCart(){ 
        Methods.preserveMessages();
        int c = check();
        if(c >= 0){
            addMenuItemToCart();
            updateTotalPrice();
            System.out.println(convertCartItemsToJson());
            
            //update selected with the new Menu item and quantity by updating the 
            //json string - note, pushing to DB will create notification
            selected.setCartItems(convertCartItemsToJson());
            //push to database aka call update()
            update();
        }
        else{
            //update cart items, not DB
            addMenuItemToCart();
            updateTotalPrice();
            Methods.showMessage("Information", "Successful Add", "The meal item has been added to your cart");
        }
    }
    
    
    //Checks if the user is logged in or not to see if we need to update the database,
    //changes Json string inside Cart to edit Item
    //also can’t set it to zero, it is about editing the quantity
    public void editItemInCart(){
        Methods.preserveMessages();
        int c = check();
        if(c >= 0){
            editMenuItemInCart();
            updateTotalPrice();
            //update selected with the new Menu item and quantity by updating the 
            //json string
            selected.setCartItems(convertCartItemsToJson());
            //push to database aka call update()
            update();
        }
        else{
           //update cart items, not DB
           editMenuItemInCart();
           updateTotalPrice();
           Methods.showMessage("Information", "Successful Edit", "The meal item in your cart has been updated.");
        }    
    }
    
    //Checks if the user is logged in or not to see if we need to update the database
    //Removes specified item from CartItems
    public void removeItemFromCart(){
        Methods.preserveMessages();
        int c = check();
        if(c >= 0){
            deleteCartItemFromCart();
            updateTotalPrice();
            //update selected with the new Menu item and quantity by updating the 
            //json string            deleteCartItemFromCart(item);

            selected.setCartItems(convertCartItemsToJson());
            //push to database aka call update()
            update();
        }
        else{
           //update car Items, not DB
           deleteCartItemFromCart();
           updateTotalPrice();
           Methods.showMessage("Information", "Successful Delete", "The meal item has been deleted from your cart");
        }  
    }
    
    //when the user signs in, check if they have a cart and merge it with their
    //current cart
    public void mergeCart(){
        
        //get Cart from DB
        Integer primaryKey = (Integer) Methods.sessionMap().get("user_id");
        Cart dbCart = getUserCart(primaryKey);
     
        List<CartItem> temp = new ArrayList<CartItem>();
        
        //possible that user logged in with no using of cart and cartItems is null
        //and selected is still null
        if(cartItems == null){
            cartItems = new ArrayList<CartItem>();
        }  
        if(selected == null){
            selected = new Cart();
        }
        
        //set selected UserId and selected id so does not get messed up
        User justSignedIn = getUserFacade().findById(primaryKey);
        selected.setUserId(justSignedIn);
        selected.setId(dbCart.getId());
        
        //possible that user added something to cart and then deleted it so now
        //cartItems is empty (would flow from earlier statement too)
        if(cartItems.isEmpty()){
            //then the cart should just be whatever was saved in DB
            System.out.println("cart was empty and so we just use what we already have in DB");
            System.out.println("what we have in DB is " + dbCart.getCartItems());
            selected.setCartItems(dbCart.getCartItems());
            cartItems = orderManager.convertJsonToCartItems(selected.getCartItems());
        }
        //possible that DB is empty, so cartItems should just be whatever is already there
        else if(dbCart.getCartItems().equals("{cartItems:[]}")){
            System.out.println("DB was empty and so we just use what we already have in cartItems");
            System.out.println("what we already have in cartitems is" + convertCartItemsToJson());
            selected.setCartItems(convertCartItemsToJson());
            update();
        } 
        else{
            //TODO - doesn't work
            System.out.println("we must merge");
            //store current cartItems in temp
            for(CartItem c: cartItems){
                temp.add(c);
            }
            
            //change selected to be what is in DB
            selected.setCartItems(dbCart.getCartItems());
            cartItems = orderManager.convertJsonToCartItems(selected.getCartItems());
            
             //merge temp and cartItems
            //TODO: also merge instructions?
            for(CartItem d: temp){
                for(CartItem c: cartItems){
                    if(c.getMenuItem().getName().equals(d.getMenuItem().getName())){
                        int totalQuantity = c.getQty() + d.getQty();
                        c.setQty(totalQuantity);
                    }
                }
            }
            //push to database aka call update()
            selected.setCartItems(convertCartItemsToJson());
            update();
        }
         updateTotalPrice();
    }
    
    //converts static variable cartItems into json to be saved in the database
    public String convertCartItemsToJson(){
        StringBuilder s = new StringBuilder();
        s.append("{");
        s.append("\"cartItems\":[");
        for(int i=0; i< cartItems.size(); i++){
           s.append(cartItems.get(i).toString());
           if(i < (cartItems.size() - 1)){
                s.append(",");
           }
        }
        s.append("]");
        s.append("}");
        System.out.println(s.toString());
        return s.toString();
    }
    
    //Removes the Json string items from the cart object as well as the cart table  
    //(no need to check because the user will be logged in)
    public void removeAllItemsFromCart(){
        Methods.preserveMessages();
        //change Java object
        cartItems.clear();
        updateTotalPrice();
        
        //update database
        selected.setCartItems(" ");
        update();
    }
    
    //checks if the user is logged in, if yes, it redirect to the order page, 
    //otherwise, it will ask the user to login, and redirect the user to the 
    //login/create user page.
    public String checkOutCart(){
        
        //if the user is logged in
        if(Methods.sessionMap().get("username") != null){
            //redirect to order page
            return "/orders/OrderHistory.xhtml?faces-redirect=true";
        }
        else{
            //redirect to login page
            return "/SignIn.xhtml?faces-redirect=true";
        
        }
    }
}
