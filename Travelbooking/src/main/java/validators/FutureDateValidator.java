package validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import java.util.Date;

@FacesValidator("futureDateValidator")
public class FutureDateValidator implements Validator<Date> {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Date value) 
            throws ValidatorException {
        if (value == null) {
            return; // Let required="true" handle null values
        }
        
        if (value.before(new Date())) {
            throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Date must be in the future", 
                    "Please select a future date"));
        }
    }
}