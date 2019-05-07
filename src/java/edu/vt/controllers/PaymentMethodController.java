/*
 * Created by Joshua Detwiler on 2019.05.04
 * Copyright © 2019 Joshua Detwiler. All rights reserved. 
 */

package edu.vt.controllers;

import edu.vt.EntityBeans.PaymentMethod;
import edu.vt.EntityBeans.User;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.FacadeBeans.PaymentMethodFacade;
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

@Named("paymentMethodController")
@SessionScoped
public class PaymentMethodController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private List<PaymentMethod> items = null;
    private PaymentMethod selected;
    
    // Required to have a required field. We don't actually save it though. It gets cleared at creation.
    private String securityCode;
    
    private String answerToSecurityQuestion;
    
    /*
    The instance variable 'paymentMethodFacade' is annotated with the @EJB 
    annotation. The @EJB annotation directs the EJB Container (of the GlassFish
    AS) to inject (store) the object reference of the PaymentMethodFacade 
    object, after it is instantiated at runtime, into the instance variable 
    'paymentMethodFacade'.
     */
    @EJB
    private edu.vt.FacadeBeans.PaymentMethodFacade paymentMethodFacade;

    /*
    ==================
    Constructor Method
    ==================
     */
    public PaymentMethodController() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public PaymentMethod getSelected() {
        return selected;
    }

    public void setSelected(PaymentMethod selected) {
        this.selected = selected;
    }

    private PaymentMethodFacade getPaymentMethodFacade() {
        return paymentMethodFacade;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getAnswerToSecurityQuestion() {
        return answerToSecurityQuestion;
    }

    public void setAnswerToSecurityQuestion(String answerToSecurityQuestion) {
        this.answerToSecurityQuestion = answerToSecurityQuestion;
    }
    
    
    
    public String getCardTypeFormatted(String cardType) {
        switch(cardType) {
            case "VISA": return "Visa";
            case "MASTERCARD": return "MasterCard";
            case "DISCOVER": return "Discover";
            case "AMERICANEXPRESS": return "American Express";
            case "DINERSCLUB": return "Diners Club";
            case "JCB": return "JCB";
            default: return cardType;
        }
    }
    
    
    
    /*
    ================
    Instance Methods
    ================
    */

    /*
    *************************************
    Prepare to Create a New Payment Method
    *************************************
     */
    public PaymentMethod prepareCreate() {
        System.out.println("muauau");
        selected = new PaymentMethod();
        
        User signedInUser = (User) Methods.sessionMap().get("user");
        selected.setUserId(signedInUser);
        
        return selected;
    }
    
    /**
     * Sets the selected element since PrimeFaces can't do it on its own.
     * @param activeTab the open tab when the user clicks to edit a payment method
     * @return the selected payment method
     */
    public PaymentMethod prepareEdit(PaymentMethod activeTab) {
        selected = activeTab;
        
        return selected;
    }
    
    /**
     * Sets the selected element since PrimeFaces can't do it on its own.
     * @param activeTab the open tab when the user clicks to delete a payment method
     * @return the selected payment method
     */
    public PaymentMethod prepareDelete(PaymentMethod activeTab) {
        selected = activeTab;
        
        return selected;
    }

    /*
    ******************************************
    Create a New Payment Method in the Database
    ******************************************
     */
    public void create() {
        /*
        We need to preserve the messages since we will redirect to show a
        different JSF page after successful creation of the survey.
         */
        Methods.preserveMessages();
        
        securityCode = "";
        int position = selected.getCreditCardNumber().length() - 4;
        selected.setCreditCardNumber(selected.getCreditCardNumber().substring(position, position + 4));
        
        /*
        Show the message "Thank You! Your Payment Method was Successfully Saved in the Database!"
        given in the Bundle.properties file under the UserSurveyCreated keyword.

        Prevent displaying the same msg twice, one for Summary and one for Detail, by setting the 
        message Detail to "" in the addSuccessMessage(String msg) method in the jsfUtil.java file.
         */
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PaymentMethodCreated"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    // We do not allow update of a payment method.
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PaymentMethodUpdated"));
    }
    
    public void securityAnswerSubmit() {
        /*
        'user', the object reference of the signed-in user, was put into the SessionMap
        in the initializeSessionMap() method in LoginManager upon user's sign in.
         */
        User signedInUser = (User) Methods.sessionMap().get("user");

        String actualSecurityAnswer = signedInUser.getSecurityAnswer();
        String enteredSecurityAnswer = getAnswerToSecurityQuestion();

        if (actualSecurityAnswer.equals(enteredSecurityAnswer)) {
            // Answer to the security question is correct. Delete the selected survey.
            destroy();

        } else {
            Methods.showMessage("Error", "Answer to the Security Question is Incorrect!", "");
        }
    }

    /*
    ***************************************************
    Delete the Selected Payment Method from the Database
    ***************************************************
     */
    public void destroy() {
        /*
        We need to preserve the messages since we will redirect to show a
        different JSF page after successful deletion of the survey.
         */
        Methods.preserveMessages();
        /*
        Show the message "The Payment Method was Successfully Deleted from the Database!"
        given in the Bundle.properties file under the UserSurveyDeleted keyword.
        
        Prevent displaying the same msg twice, one for Summary and one for Detail, by setting the 
        message Detail to "" in the addSuccessMessage(String msg) method in the jsfUtil.java file.
         */
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PaymentMethodDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PaymentMethod> getItems() {
        if (items == null) {
            /*
            user_id (database primary key) was put into the SessionMap in the
            initializeSessionMap() method in LoginManager upon user's sign in.
             */
            int userPrimaryKey = (int) Methods.sessionMap().get("user_id");
            
            // Note: We can assume the user is logged in if they're accessing payment methods.
            // Thus, the query should never fail or need to check for a null user_id value.
            items = getPaymentMethodFacade().findPaymentMethodsByUserPrimaryKey(userPrimaryKey);
        }
        return items;
    }

    /*
     ****************************************************************************
     *   Perform CREATE, EDIT (UPDATE), and DELETE Operations in the Database   *
     ****************************************************************************
     */
    /**
     * @param persistAction refers to CREATE, UPDATE (Edit) or DELETE action
     * @param successMessage displayed to inform the user about the result
     */
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    /*
                     -------------------------------------------------
                     Perform CREATE or EDIT operation in the database.
                     -------------------------------------------------
                     The edit(selected) method performs the SAVE (STORE) operation of the "selected"
                     object in the database regardless of whether the object is a newly
                     created object (CREATE) or an edited (updated) object (EDIT or UPDATE).
                    
                     PaymentMethodFacade inherits the edit(selected) method from the AbstractFacade class.
                     */
                    getPaymentMethodFacade().edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove method performs the DELETE operation in the database.
                    
                     PaymentMethodFacade inherits the remove(selected) method from the AbstractFacade class.
                     */
                    getPaymentMethodFacade().remove(selected);
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

    /*
    ************************************************
    |   Other Auto Generated Methods by NetBeans   |
    ************************************************
     */
    public PaymentMethod getPaymentMethod(java.lang.Integer id) {
        return getPaymentMethodFacade().find(id);
    }

    public List<PaymentMethod> getItemsAvailableSelectMany() {
        return getPaymentMethodFacade().findAll();
    }

    public List<PaymentMethod> getItemsAvailableSelectOne() {
        return getPaymentMethodFacade().findAll();
    }

    @FacesConverter(forClass = PaymentMethod.class)
    public static class PaymentMethodControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PaymentMethodController controller = (PaymentMethodController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "paymentMethodController");
            return controller.getPaymentMethod(getKey(value));
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
            if (object instanceof PaymentMethod) {
                PaymentMethod o = (PaymentMethod) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PaymentMethod.class.getName()});
                return null;
            }
        }

    }
}