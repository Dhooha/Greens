/*
 * Created by Dhoha Abid on 2019.03.13  * 
 * Copyright © 2019 Dhoha Abid. All rights reserved. * 
 */
package edu.vt.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/*
The @FacesValidator annotation on a class automatically registers the class with the runtime as a Validator. 
The "usernameValidator" attribute is the validator-id used in the JSF facelets page CreateAccount.xhtml within

    <f:validator validatorId="usernameValidator" /> 

to invoke the "validate" method of the annotated class given below.  
*/
@FacesValidator("usernameValidator")

public class UsernameValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        // Type cast the inputted "value" to String type
        String username = (String) value;
        
        if (username == null || username.isEmpty()){
            return;
        }
        
        String regex = "^[_A-Za-z0-9.@-]{6,32}";
        
        if (!username.matches(regex)){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Username Failed!", "The username must be minimum 6 and maximum 32"
                    + "characters long and can contain underscore, uppercase letters, "
                    + "lowercase letters, digits 0 to 9, period, at sing @, and hyphen."));
        }
    }
}
