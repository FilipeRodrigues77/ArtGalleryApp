package util;

public class MessageResponse {
    private String message;

    public MessageResponse(String message, String... args) {
        this.message = String.format(message, args);
    }

    public MessageResponse(Exception e) {
        this.message = String.format("%s : %s", e.getClass().getSimpleName(), e.getMessage());
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return String.format("{\"message\" : \"%s\"}", this.message);
    }
}
