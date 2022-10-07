package interfaces;

import entity.Todo;

import java.util.ArrayList;
import java.util.List;

public interface TodoDao {

    void add(Todo todo);

    void delete(int num);

    void complete(Todo todo);

    void update(Todo todo);

    void find(ArrayList<Todo> todo, String text);

    List<Todo> findAll();

    ArrayList<Todo> get_tasks();

    int getSize();
}
