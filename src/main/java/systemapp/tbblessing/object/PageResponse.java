package systemapp.tbblessing.object;

public class PageResponse {

    public PageResponse() {

    }

    public PageResponse(int status, boolean message) {
        this.status = status;
        this.message = message;
        this.result = null;
    }

    public PageResponse(int status, boolean message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    private int status;
    private boolean message;
    private Object result;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean getMessage() {
        return this.message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
