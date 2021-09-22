package gr.hellasdirect.houseinsurance;

public class ServiceResponse<T> {
    private ServiceCode code;
    private String message;
    private T data;

    public ServiceCode getCode() {
        return code;
    }

    public ServiceResponse<T> setCode(ServiceCode code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ServiceResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ServiceResponse<T> setData(T data) {
        this.data = data;
        return this;
    }
}
