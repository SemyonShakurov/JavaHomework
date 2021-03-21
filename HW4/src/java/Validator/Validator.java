package Validator;

import ValidationError.ValidationError;

import java.util.Set;

public interface Validator {
    Set<ValidationError> validate(Object object);
}
