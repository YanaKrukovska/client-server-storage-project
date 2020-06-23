package ua.edu.ukma.distedu.storage.persistence.model;

public class Response<T> {

    private String errorMessage;
    private T object;

    public Response(T object, String errorMessage) {
        this.object = object;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public boolean isOkay(){
        return errorMessage.isEmpty();
    }
}
