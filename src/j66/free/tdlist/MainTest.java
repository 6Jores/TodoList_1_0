package j66.free.tdlist;


import j66.free.tdlist.model.TodoListManager;
import java.util.Date;

/**
 * Author : Jores NOUBISSI
 * JavaDoc creation Date : 2019-12-02
 *
 * Class : MainTest
 * Object : Just for test
 */
public class MainTest {
    /**
     * Run the application
     * @param args : Context variable
     */
    public static void main(String[] args) {
        Main.main(args);
    }

    /**
     * Creation of 6 todoLists (thus 3 archived)
     * For test
     */
    public static void init(){
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
