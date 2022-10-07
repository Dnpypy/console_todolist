package user_interface.impls;


import entity.Todo;
import locale_package.MessageManager;
import time.TodoTime;
import user_interface.DisplayTask;

import java.util.ArrayList;
import java.util.IllegalFormatConversionException;
import java.util.Scanner;

public class DisplayTaskImpl implements DisplayTask {


    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RED = "\u001B[31m";

    // ===== очищение цвета =====
    public static final String ANSI_RESET = "\u001B[0m";



    /**
     * Вывод текста список дел
     * @param tasks текущий список дел
     */

    public void displayTaskList(ArrayList<Todo> tasks, MessageManager manager) {
        String task_list = "task.list";
        String task_number = "task.number";
        String task_task = "task.task";
        String task_timeCreate = "task.timeCreate";
        String task_status = "task.statusTask";

        // ########## список дел #################
        String leftAlignFormat = "| %-6s | %-51s | %-33s  | %-28s |%n";

        System.out.println();
        System.out.format("+-------------------------------------------------------------------------------------------------------------------------+%n");
        System.out.format("|                                       " + manager.getString(task_list) + "                                                                        |%n");
        System.out.format("+--------+-----------------------------------------------------+------------------------------------+---------------------+%n");
        System.out.format("| " + manager.getString(task_number) + " |                " + manager.getString(task_task) + "                               |        " + manager.getString(task_timeCreate) + "               |  " + manager.getString(task_status) + "      |%n");
        System.out.format("+--------+-----------------------------------------------------+------------------------------------+---------------------+%n");

        int i = 1;
        for (Todo todo : tasks) {
            System.out.format(leftAlignFormat, i + " ", todo.getName(), todo.getNowTime(), (!todo.isInProgress() ? ANSI_RED + "inProgress" + ANSI_RESET : ANSI_GREEN + "Completed" + ANSI_RESET));
            i++;
        }
        System.out.format("+--------+-----------------------------------------------------+------------------------------------+---------------------+");
        System.out.println(ANSI_RESET);
        System.out.println();
    }

    /**
     * отображение главного меню
     * @param size    количество задач
     * @param manager установка текущей локали
     */
    public void displayTextMenu(int size, MessageManager manager) {
        String task_change_locale = "task.change.locale";
        String task_display_menu = "task.display.menu";
        String task_new_task = "task.new.task";
        String task_deleteTask = "task.deleteTask";
        //String task_saveAllTasks = "task.saveAllTasks";
        String task_mark_task_completed = "task.mark.task.completed";
        String task_edit_task = "task.edit.task";
        String task_calendar = "task.calendar";
        String task_search = "task.search";
        String task_exit = "task.exit";

        String taskMenu = "task.menu";
        String taskNumberMenu = "task.number.menu";
        String taskOption = "task.option";
        String taskMenuList = "task.menu.list";


        String[] menu = new String[]{manager.getString(task_change_locale),
                manager.getString(task_display_menu),
                manager.getString(task_new_task),
                manager.getString(task_deleteTask),
              //  manager.getString(task_saveAllTasks),
                manager.getString(task_mark_task_completed),
                manager.getString(task_edit_task),
                manager.getString(task_calendar),
                manager.getString(task_search),
                manager.getString(task_exit)};

        UserInputImpl.sizeMainMenu = menu.length;

        String leftAlignFormat = "| %-6s | %-28s |%n";
        String leftAlignFormat2 = "| %-2s  %-2d | %-2s %-1s |%n";

        System.out.println();
        System.out.format("+-----------------+---------------------+%n");
        System.out.format("|               " + manager.getString(taskMenu) + "        " + ANSI_PURPLE + TodoTime.currentTime() + ANSI_RESET + "    |%n");
        System.out.format("+--------+------------------------------+%n");
        System.out.format("| " + manager.getString(taskNumberMenu) + " |     " + manager.getString(taskOption) + "                  |%n");
        System.out.format("+--------+------------------------------+%n");

        var en_Ru = "task.layout";
        var layoutMenu = "task.layout.menu";

        try {
            for (int i = 0; i < menu.length; i++) {
                if (i == menu.length - 1) {
                    System.out.format(leftAlignFormat, "#" + 0, menu[i]);
                } else {
                    System.out.format(leftAlignFormat, "#" + (i + 1), menu[i]);
                }
            }

            System.out.format("+--------+-------------+----------------+%n");
            System.out.format(leftAlignFormat2, manager.getString(taskMenuList) + ":", size, manager.getString(layoutMenu) + " :    ", ANSI_RED + manager.getString(en_Ru) + ANSI_RESET);
            System.out.format("+----------------------+----------------+%n");
        } catch (IllegalFormatConversionException e) {
            e.printStackTrace();
        }
    }

    /**
     * показывает номер задания
     * @param numberTask номер задания
     */
    public void displayComplete(int numberTask, MessageManager manager) {
        String taskTextCompleteBegin = "task.text.complete.begin";
        System.out.print(ANSI_PURPLE + "[" + manager.getString(taskTextCompleteBegin) + " " + numberTask + "]");
        System.out.println(ANSI_RESET);
    }

    /**
     * возврат в меню
     * @param sc Текущий поток сканнера
     */
    public void displayReturnMenu(Scanner sc, MessageManager manager) {
        String taskExit = "task.ex";
        System.err.println(manager.getString(taskExit));
        String menu = sc.nextLine().toLowerCase();
        if (!(menu.equals("y"))) {
            System.exit(0);
        }
    }

    /**
     * показывает если лист равен 0, то список дел пуст
     *
     * @param tasks текущий список
     */
    public void displayEmptyList(ArrayList<Todo> tasks, MessageManager manager) {
        String taskTextLocale = "task.empty";
        if (tasks.size() == 0) {
            System.err.println(manager.getString(taskTextLocale));
        }
    }


}
