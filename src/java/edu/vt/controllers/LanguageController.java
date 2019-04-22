/*
 * Created by Dhoha Abid on 2019.04.21  * 
 * Copyright © 2019 Dhoha Abid. All rights reserved. * 
 */
package edu.vt.controllers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

/**
 *
 * @author se
 */

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
    }
    
    /*
    ===================
       ** Methods **
    ===================
    */
    public void changeLanguage(){
        
    }
    
}
