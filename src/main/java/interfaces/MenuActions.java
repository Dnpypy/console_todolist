package interfaces;

import locale_package.MessageManager;

public interface MenuActions {

    MessageManager choiceLocale();

    void exitUser();

    void readerTasks();

    void addTask(int numberMenu);

    void deleteTask(int numberMenu);

    void completeTaskList(int numberMenu);

    void updateTaskList(int numberMenu);

    void calendarNowMonth(int userNum);

    int todoGetSize();

    void todoSearchTextTime(int userNum);

}
