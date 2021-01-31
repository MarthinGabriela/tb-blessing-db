package systemapp.tbblessing.object;

public class BaseResponse {

    public BaseResponse() {

    }

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.result = null;
    }

    public BaseResponse(int status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    private int status;
    private String message;
    private Object result;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
