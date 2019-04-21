/*
 * Created by Dhoha Abid on 2019.04.20  * 
 * Copyright © 2019 Dhoha Abid. All rights reserved. * 
 */
package edu.vt.controllers;

import edu.vt.globals.Methods;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import edu.vt.pojo.MenuItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;


@Named("menuController")
@SessionScoped
public class MenuController implements Serializable  {
    
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
    */
    String sushiClassicUramakiName;
    String sushiClassicUramakiDescription;
    List<MenuItem> sushiClassicUramakiItems = null;

    String sushiNigiriName;
    String sushiNigiriDescription;
    List<MenuItem> sushiNigiriItems = null;
    
    MenuItem selectedMenuItem;
    
    @PostConstruct
    public void init() {
        obtainMenuDataFromAPI();
    }
    
    /*
    ==========================
    Getter and Setter Methods
    ==========================
    */
    
    public String getSushiClassicUramakiName(){
        return sushiClassicUramakiName;
    }
    
    public void setSushiClassicUramakiName(String sushiClassicUramakiName){
        this.sushiClassicUramakiName = sushiClassicUramakiName;
    }
    
    public String getSushiClassicUramakiDescription() {
        return sushiClassicUramakiDescription;
    }
    
     public void setSushiClassicUramakiDescription(String sushiClassicUramakiDescription) {
        this.sushiClassicUramakiDescription = sushiClassicUramakiDescription;
    }
     
    public List<MenuItem> getSushiClassicUramakiItems() {
        return sushiClassicUramakiItems;
    }

    public void setSushiClassicUramakiItems(List<MenuItem> sushiClassicUramakiItems) {
        this.sushiClassicUramakiItems = sushiClassicUramakiItems;
    }
     
    public MenuItem getSelectedMenuItem() {
        return selectedMenuItem;
    }
    
    public void setSelectedMenuItem(MenuItem selectedMenuItem) {
        this.selectedMenuItem = selectedMenuItem;
    }

    
    private void obtainMenuDataFromAPI(){
        Methods.preserveMessages();

        try {
            String menuJsonData = Methods.readCurlContent("curl -X GET -H X-Access-Token:__API_EXPLORER_AUTH_KEY__ https://eatstreet.com/publicapi/v1/restaurant/90fd4587554469b1884225aec137a02a83c1200448b8c26e/menu");
            
            // if the retrieved data is null, throw an exception.
            if (menuJsonData == null){
                Exception NullPointerException = null;
                throw NullPointerException;
            }
            
            // else gets the hits from the json data 
            JSONArray menuJsonArray = new JSONArray(menuJsonData);
            for (int i=0; i<menuJsonArray.length(); i++){
                JSONObject MenuCategoryObject = menuJsonArray.getJSONObject(i);
                String nameCategory = MenuCategoryObject.optString("name", "");
                String descriptionCategory = MenuCategoryObject.optString("description", "");
                JSONArray menuCategoryItems = MenuCategoryObject.optJSONArray("items");
                
                List<MenuItem> menuItems = new ArrayList<>();
                for (int j=0; j<menuCategoryItems.length(); j++){
                    JSONObject menuItemObject = menuCategoryItems.getJSONObject(j);
                    String name = menuItemObject.optString("name", "");
                    String description = menuItemObject.optString("description", "");
                    Double price = menuItemObject.optDouble("price", 7.99);
                    MenuItem menuItem = new MenuItem(name, description, price);
                    menuItems.add(menuItem);
                }
                
                switch (nameCategory){
                    case "Sushi - Classic Uramaki Sushi":
                        sushiClassicUramakiName = nameCategory;
                        sushiClassicUramakiDescription = descriptionCategory;
                        sushiClassicUramakiItems = menuItems;       
                        break;
                }
                        
            }
            //JSONArray hitJsonArray = (JSONArray) menuJsonObject.optJSONArray("hits");
            
//            for (int i=0; i<hitJsonArray.length(); i++){
//                JSONObject hitMenuObject = hitJsonArray.getJSONObject(i);
//                String name = hitMenuObject.optString("name", "");
//                String description = hitMenuObject.optString("description", "");
//                // JSONObject hitMenuItemsObject = hitMe
//                
//            }
        } catch (Exception ex) {
            System.out.print(ex.toString());
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
      
   }
    
    
    
}
