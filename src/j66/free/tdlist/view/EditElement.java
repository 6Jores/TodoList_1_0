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
    TextField nameElement;
    @FXML
    TextArea descriptionElement;

    private Main main;
    private Element element;
    private String action;
    private Stage stage;


    @FXML
    private void saveTodoList (TodoList todoList){
        String _name = nameElement.getText().trim();
        if (!_name.equals("") && !_name.equals(NO_RESULT) && FileManager.itsOkForFile(_name.replace(":","_"))){
            element.setName(nameElement.getText());
            element.setDescription(descriptionElement.getText());
            if (TodoListManager.persistATodoList(todoList)) {
                if (this.action.equals(ACTION_NEW_ELEMENT)) {
                    TodoListManager.getTodoLists().add(todoList);
                    main.updateWelcomeView();
                }
            }else {
                Tool.showAlert("Form Error","Unknown error is occurred.");
            }
            stage.close();
        }else{
            Tool.showAlert("Form Error","Invalid nameElement, please correct it.");
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
            case TASK:
            case SUBPROJECT:
                if (nameElement.getText().trim().equals("")){
                    Tool.showAlert("Form Error","Invalid nameElement, please correct it.");
                }else {
                    element.setName(nameElement.getText());
                    element.setDescription(descriptionElement.getText());
                    stage.close();
                }
                if (action.equals(ACTION_NEW_ELEMENT)){
                    main.getControllerHierarchyView().updateAdding();
                }
                break;
        }
    }

    public void setMain(Main main){
        this.main = main;

    }

    public void setElement(Element element){
        this.element = element;
        this.nameElement.setText(element.getName());
        this.descriptionElement.setText(element.getDescription());
    }

    public void setAction(String action) {
        this.action = action;
        this.title.setText(action+" "+ element.getTypeElement());
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
