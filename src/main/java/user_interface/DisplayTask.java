package user_interface;

import entity.Todo;
import locale_package.MessageManager;

import java.util.ArrayList;
import java.util.Scanner;

public interface DisplayTask {

    void displayTextMenu(int size, MessageManager manager);

    void displayComplete(int numberTask, MessageManager manager);

    void displayTaskList(ArrayList<Todo> tasks, MessageManager manager);

    void displayReturnMenu(Scanner sc, MessageManager manager);

    void displayEmptyList(ArrayList<Todo> tasks, MessageManager manager);

}
