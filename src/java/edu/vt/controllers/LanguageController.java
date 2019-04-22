/*
 * Created by Dhoha Abid on 2019.04.21  * 
 * Copyright © 2019 Dhoha Abid. All rights reserved. * 
 */
package edu.vt.controllers;

import edu.vt.globals.Methods;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;


@Named("languageController")
@SessionScoped
public class LanguageController implements Serializable{
    
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
    */
    String language = "EN";
    
    
    /*
    ==========================
    Getter and Setters Methods
    ==========================
    */

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language; 
        if (language.equals("FRENCH")){
            Locale frenchLocal = new Locale("fr", "FR");
            FacesContext.getCurrentInstance().getViewRoot().setLocale(frenchLocal);
        }
        else{
            Locale englishLocal = new Locale("en", "US");;
            FacesContext.getCurrentInstance().getViewRoot().setLocale(englishLocal);
        }
    }
    
    /*
    ====================
      ** Constructor **
    ====================
    */
    public LanguageController(){
        
    }
    
    /*
    ===================
       ** Methods **
    ===================
    */
    public void changeLanguage(){
        if (language.equals("FRENCH")){
            Locale frenchLocal = new Locale("fr", "FR");
            ResourceBundle bundle = ResourceBundle.getBundle("Bundle", frenchLocal);
            System.out.print(bundle.getString("ViewUserLabel_firstName"));
        }
        
    }
    
}
