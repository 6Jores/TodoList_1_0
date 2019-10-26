package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.Element;
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

public class EditElement {
    @FXML
    Label title;
    @FXML
    TextField name;
    @FXML
    TextArea description;

    private Main main;
    private Element element;
    private String action;
    private Stage stage;


    @FXML
    private void saveTodoList (TodoList todoList){
        String _name = name.getText().trim();
        if (!_name.equals("") && !_name.equals(NO_RESULT) && FileManager.itsOkForFile(_name.replace(":","_"))){
            element.setName(name.getText());
            element.setDescription(description.getText());
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
        main.getControllerWelcomeView().initializeDescription(todoList);
    }

    @FXML
    private void cancelAction(){
        stage.close();
    }

    @FXML
    private void saveElement(){
        switch (element.getTypeElement()){

            case TODOLIST:
                saveTodoList((TodoList)element);
                break;
            case PROJECT:
                break;
            case SUBPROJECT:
                break;
            case TASK:
                break;
        }
    }


    public void setMain(Main main){
        this.main = main;

    }

    public void setElement(Element element){
        this.element = element;
        this.name.setText(element.getName());
        this.description.setText(element.getDescription());
    }

    public void setAction(String action) {
        this.action = action;
        this.title.setText(action+" "+ APP_NAME);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
