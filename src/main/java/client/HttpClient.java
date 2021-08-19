package client;

import config.ServiceConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class HttpClient {

    public static Response get(String endpoint) {
        return HttpClient.sendRequest(Method.GET, endpoint);
    }

    public static Response post(String endpoint, String body, ContentType contentType) {
        return HttpClient.sendRequest(Method.POST, endpoint, body, getContentType(contentType));
    }

    private static ContentType getContentType(ContentType contentType) {
        return contentType = contentType == null ? ContentType.JSON : contentType;
    }

    public static Response put(String endpoint, String body, ContentType contentType) {
        return HttpClient.sendRequest(Method.PUT, endpoint, body, getContentType(contentType));
    }

    public static Response delete(String endpoint) {
        return HttpClient.sendRequest(Method.DELETE, endpoint);
    }

    private static Response sendRequest(Method method, String endpoint) {
        return HttpClient.sendRequest(method, endpoint, null, null);
    }

    private static Response sendRequest(Method method, String endpoint, String body, ContentType contentType) {
        String url = ServiceConfig.HOST + endpoint;
        RequestSpecification spec = given();
        if (body != null) spec.body(body);
        if (contentType != null) spec.contentType(contentType);
        if (contentType != null) spec.accept(contentType);
        Response response = spec.request(method, url);
        return response;
    }
}
