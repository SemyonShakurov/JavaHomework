package Validator;

import Forms.EmptyForm;
import Forms.RegistrationForm;
import ValidationError.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class FormValidatorTest {

    @Test
    void validate() {
        Validator validator = new FormValidator();

        checkNull(validator);

        checkNotConstrainedForm(validator);

        checkCorrectForm(validator);
    }

    void checkCorrectForm(Validator validator) {
        HashSet<String> contacts = new HashSet<>();
        contacts.add("1234567");
        RegistrationForm form = new RegistrationForm("alex", 20,
                -1, "qwerty", contacts, "java@oracle.com",
                170, "Dog");
        printResult(validator.validate(form));
    }

    void checkNull(Validator validator) {
        try {
            validator.validate(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    void checkNotConstrainedForm(Validator validator) {
        Set<ValidationError> errors = validator.validate(new EmptyForm());
        printResult(errors);
    }

    void printResult(Set<ValidationError> errors) {
        for (ValidationError error : errors) {
            System.out.println(error.getMessage());
            System.out.println(error.getPath());
            System.out.println(error.getFailedValue());
        }
    }
}