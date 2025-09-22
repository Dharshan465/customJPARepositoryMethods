package ford.relationalMapping.assignment19.LibraryManagementSystem.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ExpiredMembershipException extends RuntimeException {
    public ExpiredMembershipException(String message) {
        super(message);
    }
}