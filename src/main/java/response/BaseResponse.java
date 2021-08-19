package response;

import io.restassured.response.Response;

import java.util.List;

public class BaseResponse<T> {
    protected Response response;
    private final Class<T> responseClass;

    public BaseResponse(Response response, Class<T> responseClass) {
        this.response = response;
        this.responseClass = responseClass;
    }

    public int getStatusCode() {
        return this.response.getStatusCode();
    }

    public String getHeader(String header) {
        return this.response.getHeader(header);
    }

    public T getBody() {
        return this.response.body().as(this.responseClass);
    }

    public List<T> getListObjects() {
        return this.response.then().extract().jsonPath().getList("", this.responseClass);
    }
}
