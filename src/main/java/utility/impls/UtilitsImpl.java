package utility.impls;

import utility.Utilits;

public class UtilitsImpl implements Utilits {

    /**
     * Предупреждает о вводе статуса состояния
     * @param status устанавливает статус состояния
     * @return текущий статус
     */
    public  boolean statusBooleanInfo(boolean status){
        var bool = status;
        System.out.println("Введите новый статус задачи (true или false) выполнена или нет:(y выход)");
        return bool;
    }


    /**
     * Предупреждает о вводе текста задачи
     * @param status устанавливает статус состояния
     * @param nameText запрашивает текущий текст задачи
     * @return текущий статус
     */
    public boolean statusBooleanInfo(boolean status, String nameText){
        var bool = status;
        System.out.println("Введите новый текст задачи: ");
        System.out.println("Текущий текст задачи: " + nameText);
        return bool;
    }

}
