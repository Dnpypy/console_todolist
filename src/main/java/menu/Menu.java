package menu;

import interfaces.MenuActions;
import interfaces.impls.MenuActionsImpl;
import user_interface.DisplayTask;
import user_interface.UserInput;
import user_interface.impls.DisplayTaskImpl;
import user_interface.impls.UserInputImpl;

import static interfaces.impls.MenuActionsImpl.messageMainManager;


public class Menu {

    private static final DisplayTask displayTask = new DisplayTaskImpl();
    private static final UserInput userInput = new UserInputImpl();
    private static final MenuActions MENU_ACTIONS = new MenuActionsImpl();


    public static void start(){


        // ============= Запуск главного цикла программы ========================

        while (true) {

            try {
                // =========== Главное меню ==========================
                displayTask.displayTextMenu(MENU_ACTIONS.todoGetSize(), messageMainManager);

                // =========== Ввод юзера цифры ===========================
                int userNum = userInput.readIntMenu();

                switch (userNum) {
                    case 1:  // [==== change Locale =====]
                        MENU_ACTIONS.choiceLocale();
                        break;
                    case 2:  // [==== output tasks =====]
                        MENU_ACTIONS.readerTasks();
                        break;
                    case 3:  //  [====== add ======]
                        MENU_ACTIONS.addTask(userNum);
                        break;
                    case 4:  // [====== delete ======]
                        MENU_ACTIONS.deleteTask(userNum);
                        break;
                    case 5:   // [===== complete ======]
                        MENU_ACTIONS.completeTaskList(userNum);
                        break;
                    case 6: // [===== update  =====]
                        MENU_ACTIONS.updateTaskList(userNum);
                        break;
                    case 7: // [===== calendarNowMonth =====]
                        MENU_ACTIONS.calendarNowMonth(userNum);
                        break;
                    case 8://  [===== searchTasks =====]
                        MENU_ACTIONS.todoSearchTextTime(userNum);
                        break;
                    case 0:  // [===== exit ======]
                        MENU_ACTIONS.exitUser();
                        break;
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
