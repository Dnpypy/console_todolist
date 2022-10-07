package user_interface.impls;


import exceptions.EmptyException;
import exceptions.OutOfRangeException;
import user_interface.UserInput;

import java.util.Scanner;

import static interfaces.impls.MenuActionsImpl.messageMainManager;

public class UserInputImpl implements UserInput {

    private static final Scanner SCANNER = new Scanner(System.in);


    /**
     * статичес переменная позволяет узнать размер главного меню из DisplayTaskImpl
     */
    public static int sizeMainMenu;

    /**
     * ввод пользователя вначале, проверка на введенное число
     *
     * @return ошибку или цифру
     */
    public int readIntMenu() {
        var num = 0;
        var status = true;
        while (status) {
            var taskEnterMenu = "task.enter.number";
            System.out.println(messageMainManager.getString(taskEnterMenu));


            var errorNum = "task.error.number";
            var errorEmpty = "task.empty.string";
            var errorMenuNumber = "task.menu.number";
            System.out.println("check " + sizeMainMenu);
            try {
                String line = SCANNER.nextLine();
                if (line.isEmpty()) throw new EmptyException();
                if(line.length() > sizeMainMenu) throw  new OutOfRangeException();

                num = Integer.parseInt(line);
                if (num < 0 || num > sizeMainMenu) throw new OutOfRangeException();
                status = false;
            } catch (NumberFormatException e) { // на строки
                System.err.println(messageMainManager.getString(errorNum));
            } catch (EmptyException e) { // на пустую строку
                System.err.println(messageMainManager.getString(errorEmpty));
            } catch (OutOfRangeException e) { // на цифры меньше 0 и больше 8
                System.err.println(messageMainManager.getString(errorMenuNumber));
            }
        }
        return num;
    }


}
