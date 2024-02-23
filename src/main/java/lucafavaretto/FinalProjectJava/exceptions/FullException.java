package lucafavaretto.FinalProjectJava.exceptions;

public class FullException extends RuntimeException {
    public FullException() {
        super("The event is already full!");
    }
}
