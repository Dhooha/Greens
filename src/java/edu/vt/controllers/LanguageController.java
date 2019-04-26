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
    private String language;
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    
    /*
    ==========================
    Getter and Setters Methods
    ==========================
    */
    public Locale getLocale() {
        return locale;
    }
    
    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        if (language != null){
            if (language.equals("FR")){
            locale = new Locale("fr", "FR");
            FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
            }
            else{
                locale = new Locale("en", "US");
                FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
            }
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
//    public void changeLanguage(){
//         if (language.equals("FR")){
//            Locale frenchLocal = new Locale("fr", "FR");
//            FacesContext.getCurrentInstance().getViewRoot().setLocale(frenchLocal);
//        }
//        else{
//            Locale englishLocal = new Locale("en", "US");;
//            FacesContext.getCurrentInstance().getViewRoot().setLocale(englishLocal);
//        }
////        if (language.equals("FRENCH")){
////            Locale frenchLocal = new Locale("fr", "FR");
////            ResourceBundle bundle = ResourceBundle.getBundle("Bundle", frenchLocal);
////            System.out.print(bundle.getString("ViewUserLabel_firstName"));
////        }
//        
//    }
    
}
