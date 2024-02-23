package lucafavaretto.FinalProjectJava.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Element with id " + id + " not found");
    }

    public NotFoundException(String email) {
        super("Element with mail " + email + " not found");
    }
}
