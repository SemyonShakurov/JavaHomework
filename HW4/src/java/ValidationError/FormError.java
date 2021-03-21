package ValidationError;

public class FormError implements ValidationError {

    String message; // Сообщение об ошибке
    String path; // Путь к ошибке
    Object failedValue; // Значение

    public FormError(String message, String path, Object failedValue) {
        this.message = message;
        this.failedValue = failedValue;
        this.path = path;
    }

    public FormError(String message, Object failedValue) {
        this.message = message;
        this.failedValue = failedValue;
        path = "not determined";
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public Object getFailedValue() {
        return failedValue;
    }
}
