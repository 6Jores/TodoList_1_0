package j66.free.tdlist.model;

import j66.free.tdlist.tools.Constant;
import j66.free.tdlist.tools.FileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

import static j66.free.tdlist.tools.Constant.*;

abstract public class TodoListManager {

    private static boolean saved;
    private static TodoList todoList;
    private static ObservableList<TodoList> todoLists = FXCollections.observableArrayList();

    public static TodoList getTodoList(){
        if (todoList == null)
            todoList = getSampleTodoList();
        return todoList;
    }

    public static TodoList getTodoList(boolean aSample){
        todoList = getSampleTodoList();
        return todoList;
    }

    public static boolean isSaved() {
        return saved;
    }

    public static void setSaved(boolean saved) {
        TodoListManager.saved = saved;
    }

    private static TodoList getSampleTodoList(){

        TodoList sampleTodoList = new TodoList("TodoList : "+LocalDate.now().toString(),"Sample, autogenerated");

        Project project = new Project("Math Book","How to main Mathematics");
        SubProject subProject = new SubProject("Text","End the writting");
        subProject.addTask(new Task("Correction","Wait for corrector return"));
        subProject.addTask(new Task("Display","Design book on InDesign CC"));

        project.addSubProject(subProject);
        project.addTask(new Task("Look for a printer","According to the quality price ratio"));

        sampleTodoList.addProject(project);
        sampleTodoList.addTask(new Task("Sample Task","Just to show you that a task can be orphan"));

        return sampleTodoList;
    }

    public static boolean persistTodoList(){
        System.out.println(todoList.getName().replace(":","_")+new Date().getTime());
        return FileManager.writeFile(todoList, TODOLIST_PATH,todoList.getName().replace(":","_")+"_"+new Date().getTime());
    }

    public static ObservableList<TodoList> getTodoLists(boolean withArchived){

        ObservableList<TodoList> _todoLists = FXCollections.observableArrayList();

        if (withArchived){
            _todoLists = todoLists;
        }else {
            for (TodoList todoList : todoLists){
                if (!todoList.isArchived())
                    _todoLists.add(todoList);
            }
        }

        return _todoLists;
    }

    public static void setTodoLists (){
        String [] listPathFile = FileManager.listFile(TODOLIST_PATH);

        for (String pathFile : listPathFile){
            File file = new File(pathFile);
            if (!pathFile.equals("TodoList ")){
                todoLists.add((TodoList)FileManager.readAnObject(new File(TODOLIST_PATH+pathFile)));
            }
        }
    }

}
