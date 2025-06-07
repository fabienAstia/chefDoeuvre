package co.simplon.personalities.dtos.reversegeocoding;

import org.springframework.http.HttpStatus;

import java.util.List;

public class GeoapifyResponseWrapper {
    private List<GeoapifyAddress> addresses;
    private HttpStatus statusCode;

    public GeoapifyResponseWrapper(List<GeoapifyAddress> addresses, HttpStatus statusCode) {
        this.addresses = addresses;
        this.statusCode = statusCode;
    }

    public GeoapifyResponseWrapper() {
    }

    public List<GeoapifyAddress> getAddresses() {
        return addresses;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setAddresses(List<GeoapifyAddress> addresses) {
        this.addresses = addresses;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "GeoapifyResponseWrapper{" +
                "addresses=" + addresses +
                ", statusCode=" + statusCode +
                '}';
    }
}
