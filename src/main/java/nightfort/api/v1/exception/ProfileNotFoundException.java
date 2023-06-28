package nightfort.api.v1.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String identifier) {
        super("Profile '" + identifier + "' not found");
    }
}
