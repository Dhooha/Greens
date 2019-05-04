/*
 * Created by Joshua Detwiler on 2019.05.04  * 
 * Copyright © 2019 Joshua Detwiler. All rights reserved. * 
 */
package edu.vt.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("paymentMethodValidator")

public class PaymentMethodValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        // Type cast the inputted "value" to String type
        String creditCardNumberString = (String) value;
        
        if (creditCardNumberString == null || creditCardNumberString.isEmpty()){
            // Do not take any action.
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }
        
        // Descriptions of credit card regular expressions obtained here: https://www.regular-expressions.info/creditcard.html
        // The regular expression was provided by Alexander Pavlov here: https://stackoverflow.com/a/9315696/7851115
        
        /*
            ^                          # Start of the regex line.
            (                          # Start of the capture group (capturing just one card number).
           
                                       # All Visa card numbers start with a 4. New cards have 16 digits. Old cards have 13.
                                       # This will match Visa OR MasterCard, but doesn't represent a separate card type.
                                       # This is the "Visa MasterCard" regex.
            ?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}  # Match a Visa card, or a MasterCard.
            |                          # or
            
                                       # Discover card numbers begin with 6011 or 65. All have 16 digits.
            6(?:011|5[0-9][0-9])[0-9]{12}  # Match a Discover card.
            |                          # or
        
                                       # American Express card numbers start with 34 or 37 and have 15 digits.
            3[47][0-9]{13}             # Match an American Express card.
            |                          # or
        
                                       # Diners Club card numbers begin with 300 through 305, 36 or 38. All have 14 digits. 
                                       # There are Diners Club cards that begin with 5 and have 16 digits.
            3(?:0[0-5]|[68][0-9])[0-9]{11}  # Match a Diners Club card, which is effectively a MasterCard.
            |                          # or
        
                                       # JCB cards beginning with 2131 or 1800 have 15 digits. 
                                       # JCB cards beginning with 35 have 16 digits.
            (?:2131|1800|35\\d{3})\\d{11}  # Match a JCB card.
            )                          # End of the capture group.
            $                   # End of the regex line.
        */
        String regex = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}"
                + "|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
        
        if (!creditCardNumberString.matches(regex)){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Unrecognized Credit Card Number.", "Please check that the credit card number you have entered is correct."));
        }
    }
    
}
