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
    
    String greensSpecialityRollsName ;
    String greensSpecialityRollsDescription;
    List <MenuItem> greensSpecialittyRollsItems = null;

    String grillAppetizerName;
    String grillAppetizerDescription;
    List<MenuItem> grillAppetizerItems = null;

    String grillGourmetBurgersSandwichesName;
    String grillGourmetBurgersSandwichesDescription;
    List<MenuItem> grillGourmetBurgersSandwichesItems = null;

    String grillGourmetTacosName;
    String grillGourmetTacosDescription;
    List<MenuItem> grillGourmetTacosItems = null;
                      
    String grillMiddleEasternCuisineName;
    String grillMiddleEasternCuisineDescription;
    List<MenuItem> grillMiddleEasternCuisineItems = null;

   
    
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
     
    public String getGreensSpecialityRollsName() {
        return greensSpecialityRollsName;
    }
    
    public void setGreensSpecialityRollsName(String greensSpecialityRollsName) {
        this.greensSpecialityRollsName = greensSpecialityRollsName;
    }
    
     public String getSushiNigiriName() {
        return sushiNigiriName;
    }

    public void setSushiNigiriName(String sushiNigiriName) {
        this.sushiNigiriName = sushiNigiriName;
    }

    public String getSushiNigiriDescription() {
        return sushiNigiriDescription;
    }

    public void setSushiNigiriDescription(String sushiNigiriDescription) {
        this.sushiNigiriDescription = sushiNigiriDescription;
    }

    public List<MenuItem> getSushiNigiriItems() {
        return sushiNigiriItems;
    }

    public void setSushiNigiriItems(List<MenuItem> sushiNigiriItems) {
        this.sushiNigiriItems = sushiNigiriItems;
    }

    public String getGreensSpecialityRollsDescription() {
        return greensSpecialityRollsDescription;
    }

    public void setGreensSpecialityRollsDescription(String greensSpecialityRollsDescription) {
        this.greensSpecialityRollsDescription = greensSpecialityRollsDescription;
    }

    public List<MenuItem> getGreensSpecialittyRollsItems() {
        return greensSpecialittyRollsItems;
    }

    public void setGreensSpecialittyRollsItems(List<MenuItem> greensSpecialittyRollsItems) {
        this.greensSpecialittyRollsItems = greensSpecialittyRollsItems;
    }

    public String getGrillAppetizerName() {
        return grillAppetizerName;
    }

    public void setGrillAppetizerName(String grillAppetizerName) {
        this.grillAppetizerName = grillAppetizerName;
    }

    public String getGrillAppetizerDescription() {
        return grillAppetizerDescription;
    }

    public void setGrillAppetizerDescription(String grillAppetizerDescription) {
        this.grillAppetizerDescription = grillAppetizerDescription;
    }

    public List<MenuItem> getGrillAppetizerItems() {
        return grillAppetizerItems;
    }

    public void setGrillAppetizerItems(List<MenuItem> grillAppetizerItems) {
        this.grillAppetizerItems = grillAppetizerItems;
    }

    public String getGrillGourmetBurgersSandwichesName() {
        return grillGourmetBurgersSandwichesName;
    }

    public void setGrillGourmetBurgersSandwichesName(String grillGourmetBurgersSandwichesName) {
        this.grillGourmetBurgersSandwichesName = grillGourmetBurgersSandwichesName;
    }

    public String getGrillGourmetBurgersSandwichesDescription() {
        return grillGourmetBurgersSandwichesDescription;
    }

    public void setGrillGourmetBurgersSandwichesDescription(String grillGourmetBurgersSandwichesDescription) {
        this.grillGourmetBurgersSandwichesDescription = grillGourmetBurgersSandwichesDescription;
    }

    public List<MenuItem> getGrillGourmetBurgersSandwichesItems() {
        return grillGourmetBurgersSandwichesItems;
    }

    public void setGrillGourmetBurgersSandwichesItems(List<MenuItem> grillGourmetBurgersSandwichesItems) {
        this.grillGourmetBurgersSandwichesItems = grillGourmetBurgersSandwichesItems;
    }

    public String getGrillGourmetTacosName() {
        return grillGourmetTacosName;
    }

    public void setGrillGourmetTacosName(String grillGourmetTacosName) {
        this.grillGourmetTacosName = grillGourmetTacosName;
    }

    public String getGrillGourmetTacosDescription() {
        return grillGourmetTacosDescription;
    }

    public void setGrillGourmetTacosDescription(String grillGourmetTacosDescription) {
        this.grillGourmetTacosDescription = grillGourmetTacosDescription;
    }

    public List<MenuItem> getGrillGourmetTacosItems() {
        return grillGourmetTacosItems;
    }

    public void setGrillGourmetTacosItems(List<MenuItem> grillGourmetTacosItems) {
        this.grillGourmetTacosItems = grillGourmetTacosItems;
    }

    public String getGrillMiddleEasternCuisineName() {
        return grillMiddleEasternCuisineName;
    }

    public void setGrillMiddleEasternCuisineName(String grillMiddleEasternCuisineName) {
        this.grillMiddleEasternCuisineName = grillMiddleEasternCuisineName;
    }

    public String getGrillMiddleEasternCuisineDescription() {
        return grillMiddleEasternCuisineDescription;
    }

    public void setGrillMiddleEasternCuisineDescription(String grillMiddleEasternCuisineDescription) {
        this.grillMiddleEasternCuisineDescription = grillMiddleEasternCuisineDescription;
    }

    public List<MenuItem> getGrillMiddleEasternCuisineItems() {
        return grillMiddleEasternCuisineItems;
    }

    public void setGrillMiddleEasternCuisineItems(List<MenuItem> grillMiddleEasternCuisineItems) {
        this.grillMiddleEasternCuisineItems = grillMiddleEasternCuisineItems;
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
                    case "Sushi - Nigiri":
                        sushiNigiriName = nameCategory;
                        sushiNigiriDescription = descriptionCategory;
                        sushiNigiriItems = menuItems;
                        break;
                    case "Sushi - Green's Specialty Rolls":
                        greensSpecialityRollsName = nameCategory;
                        greensSpecialityRollsDescription = descriptionCategory;
                        greensSpecialittyRollsItems = menuItems;
                        break;
                    case " Grill - Appetizers":
                        grillAppetizerName = nameCategory;
                        grillAppetizerDescription = descriptionCategory;
                        grillAppetizerItems = menuItems;
                        break;
                    case "Grill - Gourmet Burgers & Sandwiches":
                        grillGourmetBurgersSandwichesName = nameCategory;
                        grillGourmetBurgersSandwichesDescription = descriptionCategory;
                        grillGourmetBurgersSandwichesItems = menuItems;
                        break;
                    case "Grill - Gourmet Tacos":
                        grillGourmetTacosName = nameCategory;
                        grillGourmetTacosDescription = descriptionCategory;
                        grillGourmetTacosItems = menuItems;
                        break;
                    case "Grill - Middle Eastern Cuisine":
                        grillMiddleEasternCuisineName = nameCategory;
                        grillMiddleEasternCuisineDescription = descriptionCategory;
                        grillMiddleEasternCuisineItems = menuItems;
                        break;
                }
                        
            }
        } catch (Exception ex) {
            System.out.print(ex.toString());
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
      
   }
    
    
    
}
