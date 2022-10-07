package exceptions;

public class DoubleException extends Exception{

    public DoubleException() {
    }

    /**
     * пробую для пробы объединить две ошибки, для уменьшения кода
     * @param text текст, который принимаю в методе
     * @param size размер листа
     * @throws EmptyException проверка на пустоту
     * @throws OutOfRangeException проверка на диапазон
     */
    public static void exDoubleException(String text, int size) throws EmptyException, OutOfRangeException {
        if (text.isEmpty()) throw new EmptyException();
        if (text.length() > size) throw new OutOfRangeException();
    }

}
