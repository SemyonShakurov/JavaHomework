package Validator;

import Annotations.AnyOf;
import Annotations.InRange;
import Annotations.Size;
import ValidationError.FormError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnnotationValidator {

    /**
     * Проверка на null
     *
     * @param obj - объект для проверки
     * @return ошибку, если объект не прошел проверку, иначе null
     */
    public static FormError checkNotNull(Object obj) {
        if (obj == null)
            return new FormError("must not be null", null);
        if (obj.getClass().isPrimitive()) {
            return new FormError("only for reference types", obj);
        }
        return null;
    }

    /**
     * Проверка на положительное значение
     *
     * @param obj - объект для проверки
     * @return ошибку, если объект не прошел проверку, иначе null
     */
    public static FormError checkPositive(Object obj) {
        if (obj == null) return null;
        String className = obj.getClass().getSimpleName()
                .replaceAll("\\d+$", "");

        boolean isPositive;
        switch (className) {
            case "Byte":
                isPositive = ((byte) obj) > 0;
                break;
            case "Short":
                isPositive = ((short) obj) > 0;
                break;
            case "Integer":
                isPositive = ((int) obj) > 0;
                break;
            case "Long":
                isPositive = ((Long) obj) > 0;
                break;
            default:
                return new FormError(
                        "must be primitive types or their wrappers",
                        obj);
        }

        if (!isPositive)
            return new FormError("must be positive", obj);

        return null;
    }

    /**
     * Проверка на отрицательное значение
     *
     * @param obj - объект для проверки
     * @return ошибку, если объект не прошел проверку, иначе null
     */
    public static FormError checkNegative(Object obj) {
        if (obj == null) return null;
        String className = obj.getClass().getSimpleName()
                .replaceAll("\\d+$", "");

        boolean isNegative;
        switch (className) {
            case "Byte":
                isNegative = ((byte) obj) < 0;
                break;
            case "Short":
                isNegative = ((short) obj) < 0;
                break;
            case "Integer":
                isNegative = ((int) obj) < 0;
                break;
            case "Long":
                isNegative = ((Long) obj) < 0;
                break;
            default:
                return new FormError(
                        "must be primitive types or their wrappers",
                        obj);
        }

        if (!isNegative)
            return new FormError("must be negative", obj);

        return null;
    }

    /**
     * Проверка на notBlank
     *
     * @param obj - объект для проверки
     * @return ошибку, если объект не прошел проверку, иначе null
     */
    public static FormError checkNotBlank(Object obj) {
        if (obj == null) return null;
        String type = obj.getClass().getSimpleName()
                .replaceAll("\\d+$", "");

        if (!type.equals("String"))
            return new FormError("must be a String", obj);
        String value = obj.toString();
        if (value.isBlank())
            return new FormError("must be not blank", obj);

        return null;
    }

    /**
     * Проверка на notEmpty
     *
     * @param obj - объект для проверки
     * @return ошибку, если объект не прошел проверку, иначе null
     */
    public static FormError checkNotEmpty(Object obj) {
        if (obj == null) return null;
        String type = obj.getClass().getSimpleName()
                .replaceAll("\\d+$", "");

        boolean isEmpty;
        switch (type) {
            case "List":
                isEmpty = ((List<?>) obj).isEmpty();
                break;
            case "Set":
                isEmpty = ((Set<?>) obj).isEmpty();
                break;
            case "Map":
                isEmpty = ((Map<?, ?>) obj).isEmpty();
                break;
            case "String":
                isEmpty = obj.toString().isEmpty();
                break;
            default:
                return new FormError(
                        "must be List, Map, Set or String", obj);
        }

        if (isEmpty)
            return new FormError("must be not empty", obj);

        return null;
    }

    /**
     * Проверка на корректный размер объекта
     *
     * @param obj - объект для проверки
     * @param sizeAnnotation - аннотация Size
     * @return ошибку, если объект не прошел проверку, иначе null
     */
    public static FormError checkSize(Object obj, Size sizeAnnotation) {
        if (obj == null) return null;
        String type = obj.getClass().getSimpleName()
                .replaceAll("\\d+$", "");
        int min = sizeAnnotation.min();
        int max = sizeAnnotation.max();

        int size;
        switch (type) {
            case "List":
                size = ((List<?>) obj).size();
                break;
            case "Set":
                size = ((Set<?>) obj).size();
                break;
            case "Map":
                size = ((Map<?, ?>) obj).size();
                break;
            case "String":
                size = obj.toString().length();
                break;
            default:
                return new FormError(
                        "must be List, Map, Set or String", obj);
        }

        if (size < min || size > max)
            return new FormError(
                    "size must be between " + min + " and " + max, obj);

        return null;
    }

    /**
     * Проверка на корректный диапазон
     *
     * @param obj - объект для проверки
     * @param inRange - Аннотация inRange
     * @return ошибку, если объект не прошел проверку, иначе null
     */
    public static FormError checkInRange(Object obj, InRange inRange) {
        if (obj == null) return null;
        String type = obj.getClass().getSimpleName()
                .replaceAll("\\d+$", "");
        long min = inRange.min();
        long max = inRange.max();
        String errorMsg = "must be in range from " + min + " to " + max;

        switch (type) {
            case "Byte":
                byte byteValue = (byte) obj;
                if (byteValue < min || byteValue > max)
                    return new FormError(errorMsg, obj);
                break;
            case "Short":
                short shortValue = (short) obj;
                if (shortValue < min || shortValue > max)
                    return new FormError(errorMsg, obj);
                break;
            case "Integer":
                int intValue = (int) obj;
                if (intValue < min || intValue > max)
                    return new FormError(errorMsg, obj);
                break;
            case "Long":
                long longValue = (long) obj;
                if (longValue < min || longValue > max)
                    return new FormError(errorMsg, obj);
                break;
            default:
                return new FormError(
                        "must be primitive types or their wrappers", obj);
        }
        return null;
    }

    /**
     * Проверка на соответствие элементам массива
     *
     * @param obj - объект для проверки
     * @param anyOf - аннотация anyOf
     * @return ошибку, если объект не прошел проверку, иначе null
     */
    public static FormError checkAnyOf(Object obj, AnyOf anyOf) {
        if (obj == null) return null;
        String type = obj.getClass().getSimpleName()
                .replaceAll("\\d+$", "");
        String[] values = anyOf.value();
        if (!type.equals("String"))
            return new FormError("must be a string", obj);
        String value = obj.toString();

        if (Arrays.asList(values).contains(value))
            return null;

        StringBuilder stringBuilder = new StringBuilder("must be one of ");
        for (int i = 0; i < values.length - 1; i++)
            stringBuilder.append("'").append(values[i]).append("', ");
        stringBuilder.append("'").append(values[values.length - 1]).append("'");

        return new FormError(stringBuilder.toString(), value);
    }
}
