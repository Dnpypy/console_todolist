package exceptions;

public class EmptyException extends Exception{

    private static final long serialVersionUID = 1L;

    public EmptyException() {

    }

    public EmptyException(String s, String str) {
        super(s);
    }

    public EmptyException(String message) {
        super(message);
    }




//    public void EmptyStringException(String message, String string){
//        if(string.isEmpty()) {
//            System.err.println("Пустая строка!!! Введите число!!!");
//        }
//    }
}
