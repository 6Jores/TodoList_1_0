package j66.free.tdlist;


import j66.free.tdlist.model.TodoListManager;
import java.util.Date;

public class MainTest {
    public static void main(String[] args) {

        for (int i=0;i<3;i++) {
            TodoListManager.getTodoList(true);
            TodoListManager.getTodoList().setArchived(true);
            System.out.println(TodoListManager.persistTodoList() + ":"+new Date().getTime());
        }
        for (int i=0;i<3;i++) {
            TodoListManager.getTodoList(true);
            TodoListManager.getTodoList().setArchived(false);
            System.out.println(TodoListManager.persistTodoList() + ":"+new Date().getTime());
        }

    }
}
