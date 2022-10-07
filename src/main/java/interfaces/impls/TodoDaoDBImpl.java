package interfaces.impls;

import db.SQLiteConnection;
import entity.Todo;
import interfaces.TodoDao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TodoDaoDBImpl implements TodoDao {

    /**
     * List of all Tasks.
     */
    public static List<Todo> _tasks = new ArrayList<>();

    /**
     * Заполнение листа из таблицы БД
     */
    public TodoDaoDBImpl() throws IOException {
        _tasks = findAll();
    }

    @Override
    public void add(Todo todo) {
        try (Connection con = SQLiteConnection.getConnection();
             PreparedStatement statement = con.prepareStatement("insert into untitled(task, time_create, status)" +
                     " values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, todo.getName());
            statement.setString(2, todo.getNowTime());
            statement.setBoolean(3, todo.isInProgress());

            int result = statement.executeUpdate();

            if (result > 0) {
                int id = statement.getGeneratedKeys().getInt(1);// получить сгенерированный id вставленной записи
                todo.setId(id);
                _tasks.add(todo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MenuActionsImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int number) {
        try (Connection con = SQLiteConnection.getConnection();
             Statement statement = con.createStatement()) {
            int result = statement.executeUpdate("delete from untitled where id=" +_tasks.get(number).getId());

            if (result > 0) {
                _tasks.remove(_tasks.get(number));
                System.out.println("Удаление прошло");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void complete(Todo todo) {
        try (Connection con = SQLiteConnection.getConnection();
             PreparedStatement statement =
                     // con.prepareStatement("update untitled set task=?, time_create=?, status=? where id=?")) {
                     con.prepareStatement("update untitled set task=?, time_create=?, status=? where id=?")) {
            statement.setString(1, todo.getName());
            statement.setString(2, todo.getNowTime());
            statement.setBoolean(3, todo.isInProgress());
            statement.setInt(4, todo.getId());


            int result = statement.executeUpdate();
            if (result > 0) {}
        } catch (SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Todo todo) {
        try (Connection con = SQLiteConnection.getConnection();
             PreparedStatement statement =
                     con.prepareStatement("update untitled set task=?, time_create=?, status=? where id=?")) {
            statement.setString(1, todo.getName());
            statement.setString(2, todo.getNowTime());
            statement.setBoolean(3, todo.isInProgress());
            statement.setInt(4, todo.getId());


            int result = statement.executeUpdate();
            if (result > 0) {}
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * во всех колонках ищу похожий текст
     * @param todos временный лист
     * @param text входящий текст сканнера
     */
    @Override
    public void find(ArrayList<Todo> todos, String text) {
        try (Connection con = SQLiteConnection.getConnection();
             PreparedStatement statement = con.prepareStatement("select * from untitled where task like ? or time_create like ? or status like ?");) {

            String searchStr = "%" + text + "%";

            statement.setString(1, searchStr);
            statement.setString(2, searchStr);
            statement.setString(3, searchStr);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Todo todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setName(rs.getString("task"));
                todo.setNowTime(rs.getString("time_create"));
                todo.setInProgress(rs.getBoolean("status"));

                todos.add(todo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuActionsImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * получение всех записей из базы данных todolist.db при загрузках программы
     * @return список записей.
     */
    @Override
    public List<Todo> findAll()  {
        List<Todo> empty = new ArrayList<>();
        try (Connection con = SQLiteConnection.getConnection();
             Statement statement = con.createStatement();
             // из таблицы untitled
             ResultSet rs = statement.executeQuery("select * from untitled");) {
            while (rs.next()) {
                Todo todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setName(rs.getString("task"));
                todo.setNowTime(rs.getString("time_create"));
                todo.setInProgress(rs.getBoolean("status"));

                empty.add(todo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuActionsImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return empty;
    }

    /**
     * @return возвращает текущий лист
     */
    public ArrayList<Todo> get_tasks(){
        return (ArrayList<Todo>) _tasks;
    }

    /**
     * @return возвращает размер листа
     */
    public int getSize() {
        return _tasks.size();
    }
}
