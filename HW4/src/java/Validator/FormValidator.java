package Validator;

import Annotations.*;
import ValidationError.FormError;
import ValidationError.ValidationError;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormValidator implements Validator {

    // Путь к объекту с ошибкой
    private final StringBuilder path = new StringBuilder();

    @Override
    public Set<ValidationError> validate(Object object) {
        if (object == null)
            throw new IllegalArgumentException("Object is null");

        if (!object.getClass().isAnnotationPresent(Constrained.class)) {
            Set<ValidationError> errors = new HashSet<>();
            errors.add(new FormError(
                    "Отсутствует аннотация 'Constrained'",
                    path.toString(),
                    this
            ));
            return errors;
        }

        return validateClass(object);
    }

    /**
     * Проверка класса (формы) на соответствие аннотациям
     *
     * @param form - класс (форма)
     * @return Множество ошибок в классе
     */
    private Set<ValidationError> validateClass(Object form) {
        Set<ValidationError> errors = new HashSet<>();
        Field[] fields = form.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            errors.addAll(validateField(form, field));
        }

        return errors;
    }

    /**
     * Проверка поля на соответствие аннотациям
     *
     * @param form  - Класс, в котором находится поле
     * @param field - поле
     * @return ошибки в поле
     */
    private Set<ValidationError> validateField(Object form, Field field) {
        path.append(field.getName());
        Set<ValidationError> errors = new HashSet<>();
        Annotation[] annotations = field.getAnnotatedType()
                .getDeclaredAnnotations();
        Object obj;
        try {
            obj = field.get(form);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Can't get access to field");
        }
        for (Annotation annotation : annotations) {
            FormError error = validateAnnotation(obj, annotation);
            if (error != null) {
                error.setPath(path.toString());
                errors.add(error);
            }
        }
        if (obj == null) return errors;
        if (field.getType().getSimpleName()
                .replaceAll("\\d+$", "").equals("List"))
            errors.addAll(validateList((List<?>) obj, field.getAnnotatedType()));

        if (obj.getClass().isAnnotationPresent(Constrained.class)) {
            path.append('.');
            errors.addAll(validate(obj));
            path.delete(path.length() - 1, path.length());
        }

        int startInd = path.length() - field.getName().length();
        path.delete(startInd, path.length());
        return errors;
    }

    /**
     * Проверка элементов листа на соответствие аннотациям
     *
     * @param list - лист
     * @param type - тип
     * @return - ошибки в элементах листа
     */
    private Set<ValidationError> validateList(List<?> list, AnnotatedType type) {
        Set<ValidationError> errors = new HashSet<>();
        if (list == null || list.isEmpty()) return errors;
        AnnotatedType newType = ((AnnotatedParameterizedType) type)
                .getAnnotatedActualTypeArguments()[0];
        Annotation[] annotations = newType.getAnnotations();
        for (int i = 0; i < list.size(); i++) {
            int startInd = path.length();
            path.append("[").append(i).append("]");
            Object item = list.get(i);
            if (item == null) {
                path.delete(startInd, path.length());
                continue;
            }
            if (item.getClass().isAnnotationPresent(Constrained.class)) {
                path.append('.');
                errors.addAll(validate(item));
                path.delete(path.length() - 1, path.length());
            }
            for (Annotation annotation : annotations) {
                if (item.getClass().getSimpleName()
                        .replaceAll("\\d+$", "").equals("List"))
                    errors.addAll(validateList((List<?>) item, newType));
                FormError error = validateAnnotation(item, annotation);
                if (error != null) {
                    error.setPath(path.toString());
                    errors.add(error);
                }
            }
            path.delete(startInd, path.length());
        }
        return errors;
    }

    /**
     * Проверка объекта на соответствие аннотации
     * @param obj - объект
     * @param annotation - аннотация
     * @return - ошибку, если объект не соответствует аннотации, иначе null
     */
    private FormError validateAnnotation(Object obj,
                                         Annotation annotation) {
        if (annotation instanceof AnyOf)
            return AnnotationValidator.checkAnyOf(obj,
                    (AnyOf) annotation);
        else if (annotation instanceof InRange)
            return AnnotationValidator.checkInRange(obj,
                    (InRange) annotation);
        else if (annotation instanceof Negative)
            return AnnotationValidator.checkNegative(obj);
        else if (annotation instanceof NotBlank)
            return AnnotationValidator.checkNotBlank(obj);
        else if (annotation instanceof NotEmpty)
            return AnnotationValidator.checkNotEmpty(obj);
        else if (annotation instanceof NotNull)
            return AnnotationValidator.checkNotNull(obj);
        else if (annotation instanceof Positive)
            return AnnotationValidator.checkPositive(obj);
        else if (annotation instanceof Size)
            return AnnotationValidator.checkSize(obj, (Size) annotation);
        else throw new IllegalArgumentException("the form has " +
                    "an annotation that is not supported");
    }
}
