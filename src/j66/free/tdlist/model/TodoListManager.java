package j66.free.tdlist.model;

import j66.free.tdlist.tools.FileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static j66.free.tdlist.tools.Constant.*;

abstract public class TodoListManager {

    private static TodoList todoList;
    private static ObservableList<TodoList> todoListsNoResults;
    private static boolean save=true;
    private static boolean autoSave=false;
    private static ObservableList<TodoList> todoLists = FXCollections.observableArrayList();

    public static TodoList getTodoList(){
        return todoList;
    }

    public static TodoList getTodoList(boolean aSample){
        if (aSample)
            todoList = getSampleTodoList();
        return todoList;
    }

    public static void setTodoList(TodoList todoList) {
        TodoListManager.todoList = todoList;
    }

    public static boolean isSave() {
        return save;
    }

    public static void setSave(boolean save) {
        TodoListManager.save = save;
        if (!save && autoSave){
            persistTodoList();
        }
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

        Project project = new Project("Math Book","How to learn Mathematics");
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
        save = FileManager.writeFile(todoList, TODOLIST_PATH,todoList.getNameFile());
        return save;
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
            todoLists.removeAll(todoLists);
            setTodoLists();
        }
        return rtn;
    }

    public static void removeElement(Element element){
        Element elementParent = element.getParent();
        if (elementParent.getTypeElement() == TypeElement.TODOLIST){
            if (element.getTypeElement()==TypeElement.PROJECT){
                ((TodoList)elementParent).getListProject().remove(element);
            }else if (element.getTypeElement()==TypeElement.TASK){
                ((TodoList)elementParent).getListTask().remove(element);
            }
        }else if (elementParent.getTypeElement() == TypeElement.PROJECT){
            if (element.getTypeElement()==TypeElement.SUBPROJECT){
                ((Project)elementParent).getListSubProject().remove(element);
            }else if (element.getTypeElement()==TypeElement.TASK){
                ((Project)elementParent).getListTask().remove(element);
            }
        }else if (elementParent.getTypeElement() == TypeElement.SUBPROJECT){
            ((SubProject)elementParent).getListTask().remove(element);
        }

        if (save)
            save=false;
    }

    public static Element getNewElement(TypeElement typeElement, Element elementParent){
        Element element=null;
        switch (typeElement){
            case PROJECT:
                element = new Project("New "+typeElement.toString(),"New description",todoList);
                todoList.getListProject().add((Project) element);
                break;
            case SUBPROJECT:
                element = new SubProject("New "+typeElement.toString(),"New description",elementParent);
                ((Project)elementParent).getListSubProject().add((SubProject) element);
                break;
            case TASK:
                element = new Task("New "+typeElement.toString(),"New description",elementParent);
                if (elementParent.getTypeElement() == TypeElement.TODOLIST){
                    todoList.getListTask().add((Task)element);
                }else if(elementParent.getTypeElement() == TypeElement.PROJECT){
                    ((Project)elementParent).getListTask().add((Task)element);
                }else {
                    ((SubProject)elementParent).getListTask().add((Task)element);
                }
                break;
        }
        return element;
    }

    public static List<Task> getListTaskForDate (LocalDate date){
        List<Task> taskList = new ArrayList<>();

        for (Task task : todoList.getListTask()){
            if (TaskManager.isTodoForDate(date,task))
                taskList.add(task);
        }
        for (Project project : todoList.getListProject()){
            for (Task task : project.getListTask()){
                if (TaskManager.isTodoForDate(date,task))
                    taskList.add(task);
            }
            for(SubProject subProject : project.getListSubProject()){
                for (Task task : subProject.getListTask()){
                    if (TaskManager.isTodoForDate(date,task))
                        taskList.add(task);
                }
            }
        }

        return rangeByPriority(taskList);
    }

    private static List<Task> rangeByPriority(List<Task> sourceList){
        List<Task> listVeryHigh = new ArrayList<>();
        List<Task> listHigh = new ArrayList<>();
        List<Task> listNormal = new ArrayList<>();
        List<Task> listLow = new ArrayList<>();
        List<Task> listVeryLow = new ArrayList<>();
        List<Task> listDone = new ArrayList<>();

        List<Task> returnList = new ArrayList<>();

        for (Task task :sourceList){
            if (task.getDoneDate() != null && task.getDoneDate().isEqual(LocalDate.now())){
                listDone.add(task);
            }else {
                switch (task.getPriority()){
                    case VERY_HIGH:
                        listVeryHigh.add(task);
                        break;
                    case HIGH:
                        listHigh.add(task);
                        break;
                    case NORMAL:
                        listNormal.add(task);
                        break;
                    case LOW:
                        listLow.add(task);
                        break;
                    case VERY_LOW:
                        listVeryLow.add(task);
                        break;
                }
            }
        }

        returnList.addAll(listVeryHigh);
        returnList.addAll(listHigh);
        returnList.addAll(listNormal);
        returnList.addAll(listLow);
        returnList.addAll(listVeryLow);
        returnList.addAll(listDone);


        return returnList;
    }

    public static void setAutoSave(boolean autoSave) {
        TodoListManager.autoSave = autoSave;
    }
}
