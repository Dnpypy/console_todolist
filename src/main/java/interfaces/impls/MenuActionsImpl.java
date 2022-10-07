package interfaces.impls;


import entity.Todo;
import exceptions.DoubleException;
import exceptions.EmptyException;
import exceptions.OutOfRangeException;
import interfaces.MenuActions;
import interfaces.TodoDao;
import locale_package.MessageManager;

import time.Calendar;
import time.impls.CalendarImpl;
import user_interface.DisplayTask;
import user_interface.impls.DisplayTaskImpl;
import utility.impls.UtilitsImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuActionsImpl implements MenuActions {

    /**
     * Отображение текста для пользователя
     */
    DisplayTask displayTaskImpl = new DisplayTaskImpl();


    /**
     * Дао слой Бизнес логика (код) -> DAO и его реализации -> БД
     */
    private static final TodoDao todoDao;

    static {
        try {
            todoDao = new TodoDaoDBImpl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calendar class
     */

    private static final Calendar calendar = new CalendarImpl();

    /**
     * дополнительный сканер для ввода задач
     */
    private static final Scanner scanner = new Scanner(System.in);
    private static final Scanner scanner2 = new Scanner(System.in);

    private static Scanner scanner3 = new Scanner(System.in);
    private static Scanner scanner4 = new Scanner(System.in);

    /**
     * настройки локали, по умолчанию english,
     * чтоб сменить локаль в отдельную меню
     */
    public static MessageManager messageMainManager = MessageManager.EN;

    /**
     * ============= Выход пользователя =========
     * Выход цифра 0
     */
    public void exitUser() {
        System.exit(0);
    }

    /**
     * ============= 1 Смена локали =========
     * 1 - english, 2 - русский
     */
    @Override
    public MessageManager choiceLocale() {
        var changeLoc = "task.change.loc";
        System.out.println(messageMainManager.getString(changeLoc));
        var errorNum = "task.error.number";
        var errorMenuNumber = "task.menu.number";
        var errorEmpty = "task.empty.string";

        var status = true;
        while (status) {
            var choiceEnRu = 2;
            try {
                String str = scanner.nextLine();
                if (str.isEmpty()) throw new EmptyException();
                if (str.length() > choiceEnRu) throw new OutOfRangeException();

                int userNum = Integer.parseInt(str);
                if (userNum < 0 | userNum > choiceEnRu) throw new OutOfRangeException();
                switch (userNum) {
                    case 1:  //
                        messageMainManager = MessageManager.EN;
                        break;
                    case 2:  //
                        messageMainManager = MessageManager.RU;
                        break;
                    default:
                        messageMainManager = MessageManager.EN;
                        break;
                }
                status = false;
            } catch (NumberFormatException e) { // на строки
                System.err.println(messageMainManager.getString(errorNum));
            } catch (OutOfRangeException e) { // на цифры меньше 0 и больше 8
                System.err.println(messageMainManager.getString(errorMenuNumber));
            } catch (EmptyException e) { // на пустую строку
                System.err.println(messageMainManager.getString(errorEmpty));
            }
        }
        return messageMainManager;
    }

    /**
     * ============== 2 считывания списка дел ============
     * список(вывод задач)
     * лист задач который есть на данный момент
     */
    public void readerTasks() {
        var status = true;
        while (status) {
            // ===== список дел пуст=====
            displayTaskImpl.displayEmptyList(todoDao.get_tasks(), messageMainManager);

            // ===== список дел =====
            displayTaskImpl.displayTaskList(todoDao.get_tasks(), messageMainManager);
            status = false;
        }

        // ====== возврат в меню =====
        displayTaskImpl.displayReturnMenu(scanner2, messageMainManager);

    }

    /**
     * ========= 3. добавить новую задачу в базу данных ==========
     *
     * @param numberMenu цифра в выборе меню, ее передаю для вывода сообщения, что все прошло хорошо!
     */
    public void addTask(int numberMenu) {
        var status = true;
        var errorEmpty = "task.error.number.or.string";
        var addExit = "task.add.exit";
        do {
            System.out.println(messageMainManager.getString(addExit));
            try {
                String task = scanner.nextLine();
                if (task.isEmpty()) throw new EmptyException();
                if (task.equals("y")) {
                    // ====== возврат в меню =====
                    // displayTaskImpl.displayReturnMenu(scanner2, messageMainManager);
                    status = false;
                } else if (task != null || !(task.equals(""))) {
                    var byDefaultStatus = false;
                    Todo todoAdd = new Todo();
                    todoAdd.setName(task);
                    todoAdd.setNowTime(new Date().toString());
                    todoAdd.setInProgress(byDefaultStatus);

                    todoDao.add(todoAdd);
                    displayTaskImpl.displayComplete(numberMenu, messageMainManager);
                }

            } catch (EmptyException e) { // на пустую строку
                System.err.println(messageMainManager.getString(errorEmpty));
            }
        } while (status);

    }

    /**
     * ====== 4. Удаление строки тут пользователь выбирает номер строки, ============
     * которую хочет удалить например 2(по индексу 1) и по индексу вычитаю минус 1 ==========
     *
     * @param numberMenu цифра в выборе меню, ее передаю для вывода сообщения, что все прошло хорошо!
     */
    public void deleteTask(int numberMenu) {
        // ===== список дел =====
        displayTaskImpl.displayTaskList(todoDao.get_tasks(), messageMainManager);

        var status = true;
        while (status) {
            // ===== список дел пуст =====
            displayTaskImpl.displayEmptyList(todoDao.get_tasks(), messageMainManager);

            if (todoGetSize() <= 0) break;
            var deleteTask = "task.delete.task";
            System.out.println(messageMainManager.getString(deleteTask));


            var errorNum = "task.error.number";
            var errorMenuNumber = "task.menu.number";
            var errorEmpty = "task.empty.string";
            try {
                String str = scanner.nextLine();
                if (str.isEmpty()) throw new EmptyException();
                if (str.length() > todoGetSize()) throw new OutOfRangeException();

                int num = Integer.parseInt(str);
                if (num > todoGetSize()) throw new OutOfRangeException();

                num -= 1;
                todoDao.delete(num);

                displayTaskImpl.displayComplete(numberMenu, messageMainManager);

                status = false;
            } catch (NumberFormatException e) { // на строки
                System.err.println(messageMainManager.getString(errorNum));
            } catch (OutOfRangeException e) { // на цифры меньше 0 и больше sizeMainMenu
                System.err.println(messageMainManager.getString(errorMenuNumber));
            } catch (EmptyException e) { // на пустую строку
                System.err.println(messageMainManager.getString(errorEmpty));
            }

        }
        // ====== возврат в меню =====
        displayTaskImpl.displayReturnMenu(scanner2, messageMainManager);
    }

    /**
     * =========== 5 Mark the task completed ===============
     * пометить задачу выполненной
     * выбираю номер задачи
     *
     * @param numberMenu цифра в выборе меню, ее передаю для вывода сообщения, что все прошло хорошо!
     */
    public void completeTaskList(int numberMenu) {

        var status = true;

        // ===== список дел =====
        displayTaskImpl.displayTaskList(todoDao.get_tasks(), messageMainManager);

        do {

            // ===== список дел пуст =====
            displayTaskImpl.displayEmptyList(todoDao.get_tasks(), messageMainManager);
            // ===== если длина списка 0 выход из цикла =====
            if (todoGetSize() <= 0) break;

            var statusTask = "task.status.task";
            var errorNum = "task.error.number";
            var errorEmpty = "task.empty.string";
            var errorMenuNumber = "task.menu.number";

            System.out.println();
            System.out.println(messageMainManager.getString(statusTask));

            try {

                String line = scanner.nextLine();
                if (line.isEmpty()) throw new EmptyException();
                if (line.length() > todoGetSize()) throw new OutOfRangeException();

                if (line.equals("y")) {
                    status = false;
                } else {
                    int num = Integer.parseInt(line);
                    if (num > todoGetSize()) throw new OutOfRangeException();
                    num -= 1;
                    // получение объекта // айди в обновление не нужен
                    Todo nowTodo = todoDao.get_tasks().get(num);
                    var research = true;
                    if (!(nowTodo.isInProgress())) {
                        nowTodo.setInProgress(research);
                    } else {
                        research = false;
                        nowTodo.setInProgress(research);
                    }
                    todoDao.complete(nowTodo);
                    displayTaskImpl.displayComplete(numberMenu, messageMainManager);

                }
            } catch (NumberFormatException e) { // на строки
                System.err.println(messageMainManager.getString(errorNum));
            } catch (EmptyException e) { // на пустую строку
                System.err.println(messageMainManager.getString(errorEmpty));
            } catch (OutOfRangeException e) { // на цифры меньше 0 и больше sizeMainMenu
                System.err.println(messageMainManager.getString(errorMenuNumber));
            }
        } while (status);
    }

    /**
     * =========== 6 update  ===============
     * редактировать задание
     * выбираю номер задачи
     *
     * @param numberMenu цифра в выборе меню, передаю для редактирования
     */
    public void updateTaskList(int numberMenu) {

        var status = true;

        var errorNum = "task.error.number";
        var errorEmpty = "task.empty.string";
        var errorMenuNumber = "task.menu.number";
        var taskEdit = "task.seven.edit";

        // ===== список дел =====
        displayTaskImpl.displayTaskList(todoDao.get_tasks(), messageMainManager);

        // ===== список дел пуст =====
        displayTaskImpl.displayEmptyList(todoDao.get_tasks(), messageMainManager);

        do {
            // ===== если длина списка 0 выход из цикла =====
            if (todoGetSize() <= 0) break;

            try {
                // сканнер норм работает, когда открываю потоки в блоке try, без reset и close!!!!
                var scanner1 = new Scanner(System.in);
                var scanner2 = new Scanner(System.in);
                System.out.println();
                System.out.println(messageMainManager.getString(taskEdit));

                String line = scanner1.nextLine();

                if (line.isEmpty()) throw new EmptyException();
                if (line.length() > todoGetSize()) throw new OutOfRangeException();

                if (line.equals("y")) {
                    status = false;
                } else {
                    var currentIndex = Integer.parseInt(line);
                    var indexArray = 1;
                    currentIndex -= indexArray;
                    if (currentIndex > todoGetSize()) throw new OutOfRangeException();

                    // получение объекта // айди в обновление не нужен
                    var nowTodo = todoDao.get_tasks().get(currentIndex);
                    var statusA = true;
                    String line2Boolean = null;
                    while (new UtilitsImpl().statusBooleanInfo(statusA, nowTodo.getName())) {
                        line2Boolean = scanner2.nextLine();
                        try {
                            if (line2Boolean.isEmpty()) {
                                throw new EmptyException();

                            } else {
                                statusA = false;
                            }
                        } catch (EmptyException e) {
                            System.err.println(messageMainManager.getString(errorEmpty));
                        }
                    }
                    var updateTask = line2Boolean;

                    var updateBoolean = true;
                    var statusW = true;
                    while (new UtilitsImpl().statusBooleanInfo(statusW)) {
                        String line3Boolean = scanner2.nextLine();
                        try {
                            if (line3Boolean.length() > todoGetSize()) {
                                throw new OutOfRangeException();
                            } else if (line3Boolean.isEmpty()) {
                                throw new EmptyException();
                            } else if (!(line3Boolean.equals("true") || line3Boolean.equals("false"))) {
                                throw new InputMismatchException("DFhdfh");
                            } else if (line3Boolean.equals("false")) {
                                statusW = false;
                                updateBoolean = false;
                            } else if (line3Boolean.equals("true")) {
                                statusW = false;
                                updateBoolean = true;
                            }
                        } catch (OutOfRangeException e) { // на цифры меньше 0 и больше sizeMainMenu
                            System.err.println(messageMainManager.getString(errorMenuNumber));
                        } catch (InputMismatchException e) {
                            System.err.println("Не булево значение");
                        } catch (EmptyException e) { // на пустую строку
                            System.err.println(messageMainManager.getString(errorEmpty));
                        }
                    }

                    nowTodo.setName(updateTask);
                    nowTodo.setNowTime(new Date().toString());
                    nowTodo.setInProgress(updateBoolean);

                    todoDao.update(nowTodo);
                    displayTaskImpl.displayComplete(numberMenu, messageMainManager);

                }

            } catch (NumberFormatException e) { // на строки
                System.err.println(messageMainManager.getString(errorNum));
            } catch (EmptyException e) { // на пустую строку
                System.err.println(messageMainManager.getString(errorEmpty));
            } catch (OutOfRangeException e) { // на цифры меньше 0 и больше sizeMainMenu
                System.err.println(messageMainManager.getString(errorMenuNumber));
            } catch (InputMismatchException e) {
                System.err.println("Не булево значение");
            }

        } while (status);

    }

    /**
     * =========== 7 calendarNowMonth ===============
     * редактировать задание
     * выбираю номер задачи
     *
     * @param userNum цифра в выборе меню, передаю для редактирования
     */
    @Override
    public void calendarNowMonth(int userNum) {
        var year = LocalDate.now().getYear();
        var month = LocalDate.now().getMonth().getValue();
        LocalDate localDate = LocalDate.now();

        calendar.printCalendarOnMonth(year, month, localDate);

        // ====== возврат в меню =====
        displayTaskImpl.displayReturnMenu(scanner2, messageMainManager);
    }

    /**
     * 8. Поиск задачи по тексту или по времени(тест)
     * Использую дополнительный лист ArrayList todos
     */
    public void todoSearchTextTime(int numberMenu) {
        var status = true;
        var errorEmpty = "task.empty.string";
        var errorMenuNumber = "task.menu.number";
        var taskSearchExit = "task.search.exit";
        ArrayList<Todo> todos = new ArrayList<>();

        while (status) {
            try {
                System.out.println("Пустой или нет? try begin " + todos.isEmpty());
                //DbStorage._tasks.clear();
                todos.clear();

                // ===== список дел =====
                var sc = new Scanner(System.in);
                System.out.println(messageMainManager.getString(taskSearchExit));
                String text = sc.nextLine();
                if (text.equals("y")) break;
                DoubleException.exDoubleException(text, todoGetSize());
                todoDao.find(todos, text);

            } catch (EmptyException e) { // на пустую строку
                System.err.println(messageMainManager.getString(errorEmpty));
            } catch (OutOfRangeException e) { // на цифры меньше 0 и больше sizeMainMenu
                System.err.println(messageMainManager.getString(errorMenuNumber));
            }

            System.out.println("Проверка список дел: " + todos.isEmpty());

            // ===== список дел todos не БД!!!!!=====
            displayTaskImpl.displayTaskList(todos, messageMainManager);
        } //while

    }

    /**
     * размер текущего списка
     *
     * @return количество
     */
    @Override
    public int todoGetSize() {
        return todoDao.getSize();
    }


}
