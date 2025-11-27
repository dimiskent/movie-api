package be.skenteridis.movieapi.dto;

public class SuccessLoginDTO {
    private final boolean success = true;
    private final String message = "Logged in successfully!";
    private String name;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
