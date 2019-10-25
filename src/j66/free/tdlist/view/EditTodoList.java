package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.TodoList;
import j66.free.tdlist.model.TodoListManager;
import j66.free.tdlist.tools.FileManager;
import j66.free.tdlist.tools.Tool;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static j66.free.tdlist.tools.Constant.*;

public class EditTodoList {
    @FXML
    Label title;
    @FXML
    TextField name;
    @FXML
    TextArea description;

    private Main main;
    private TodoList todoList;
    private String action;
    private Stage stage;


    @FXML
    private void saveTodoList (){
        String _name = name.getText().trim();
        if (!_name.equals("") && !_name.equals(NO_RESULT) && FileManager.itsOkForFile(_name.replace(":","_"))){
            todoList.setName(name.getText());
            todoList.setDescription(description.getText());
            if (TodoListManager.persistATodoList(todoList)) {
                if (this.action.equals(ACTION_NEW_TODOLIST)) {
                    TodoListManager.getTodoLists().add(todoList);
                    main.updateWelcomeView();
                }
            }else {
                Tool.showAlert("Form Error","Unknown error is occurred.");
            }
            stage.close();
        }else{
            Tool.showAlert("Form Error","Invalid name, please correct it.");
        }
    }

    @FXML
    private void cancelAction(){
        stage.close();
    }


    public void setMain(Main main){
        this.main = main;

    }

    public void setTodoList(TodoList todoList){
        this.todoList = todoList;
        this.name.setText(todoList.getName());
        this.description.setText(todoList.getDescription());
    }

    public void setAction(String action) {
        this.action = action;
        this.title.setText(action+" "+ APP_NAME);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
