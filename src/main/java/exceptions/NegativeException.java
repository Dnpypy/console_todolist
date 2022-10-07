package exceptions;

public class NegativeException extends Exception{

    private static final long serialVersionUID = 1L;

    public NegativeException() {

    }

    public NegativeException(String s, String str) {
        super(s);
    }

    public NegativeException(String message) {
        super(message);
    }
}
