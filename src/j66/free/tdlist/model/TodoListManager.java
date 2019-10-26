package j66.free.tdlist.model;

import j66.free.tdlist.tools.FileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

import static j66.free.tdlist.tools.Constant.*;

abstract public class TodoListManager {

    private static TodoList todoList;
    private static ObservableList<TodoList> todoListsNoResults;

    private static ObservableList<TodoList> todoLists = FXCollections.observableArrayList();

    public static TodoList getTodoList(){
        return todoList;
    }

    public static TodoList getTodoList(boolean aSample){
        todoList = getSampleTodoList();
        return todoList;
    }

    public static void setTodoList(TodoList todoList) {
        TodoListManager.todoList = todoList;
    }

    public static ObservableList<TodoList> getTodoLists() {
        if (todoLists.size()==0)
            return getTodoListsNoResults();
        return todoLists;
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
        if (_todoLists.size()==0)
            _todoLists = getTodoListsNoResults();
        return _todoLists;
    }

    public static ObservableList<TodoList> getTodoLists(String key, boolean withArchived){
        ObservableList<TodoList> _todoLists = FXCollections.observableArrayList();

        for (TodoList todoList : todoLists){
            if (todoList.getName().toLowerCase().contains(key.toLowerCase()))
                if (withArchived)
                    _todoLists.add(todoList);
                else {
                    if (!todoList.isArchived())
                        _todoLists.add(todoList);
                }
        }

        if (_todoLists.size()==0)
            _todoLists = getTodoListsNoResults();
        return _todoLists;
    }

    private static TodoList getSampleTodoList(){

        TodoList sampleTodoList = new TodoList(APP_NAME+"_"+new Date().getTime(),"TodoList : "+LocalDate.now().toString(),"Sample, autogenerated");

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

    private static ObservableList<TodoList> getTodoListsNoResults() {
        if (todoListsNoResults == null){
            todoListsNoResults = FXCollections.observableArrayList();
            TodoList _todoList = getSampleTodoList();
            _todoList.setName(NO_RESULT);
            _todoList.setDescription("");
            todoListsNoResults.add(_todoList);
        }
        return todoListsNoResults;
    }

    public static boolean persistTodoList(){
        return FileManager.writeFile(todoList, TODOLIST_PATH,todoList.getNameFile());
    }

    public static void setTodoLists (){
        String [] listPathFile = FileManager.listFile(TODOLIST_PATH);

        for (String pathFile : listPathFile){
            if (!pathFile.equals("TodoList ")){
                todoLists.add((TodoList)FileManager.readAnObject(new File(TODOLIST_PATH+pathFile)));
            }
        }
    }

    public static boolean persistATodoList(TodoList _todoList){
        todoList = _todoList;
        boolean rtn = persistTodoList();
        todoList = null;

        return rtn;
    }

    public static boolean copyTodoList (TodoList source){
        TodoList copy;
        copy = source.cloneMy();
        boolean rtn = persistATodoList(copy);

        if (rtn)
            todoLists.add(copy);

        return rtn;
    }

    public static boolean deleteTodoList(TodoList todoList){
        boolean rtn ;
        rtn = FileManager.removeFile(TODOLIST_PATH,todoList.getNameFile());
        if (rtn){
            getTodoLists().remove(todoList);
        }
        return rtn;
    }

}
