package nightfort.api.v1.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(Long id) {
        super("Profile '" + id + "' not found.");
    }
}
