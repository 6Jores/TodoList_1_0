package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.TodoList;
import j66.free.tdlist.model.TodoListManager;
import j66.free.tdlist.tools.Tool;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class WelcomeView {

    private Main main;

    @FXML
    CheckBox withArchivedOrNot;
    @FXML
    ListView<TodoList> fileListView;
    @FXML
    Label todoListName;
    @FXML
    Label todoListDescription;
    @FXML
    Label todoListCreationDate;
    @FXML
    Label todoListEditionDate;

    public void setMain(Main main){
        this.main = main;

        fileListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fileListView.setItems(TodoListManager.getTodoLists(withArchivedOrNot.isSelected()));

        initializeDescription(null);

    }

    @FXML
    private void initialize(){
        fileListView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> initializeDescription(newValue)));
    }

    @FXML
    public void setArchivedOrNot(){
        fileListView.setItems(TodoListManager.getTodoLists(withArchivedOrNot.isSelected()));
    }

    private void initializeDescription(TodoList todoList){
        if (todoList != null){
            todoListName.setText(todoList.getName());
            todoListDescription.setText(todoList.getDescription());
            todoListCreationDate.setText(Tool.formatDate(todoList.getCreationDate()));
            todoListEditionDate.setText(Tool.formatDate(todoList.getEditionDate()));
        }
        else {
            todoListName.setText("");
            todoListDescription.setText("");
            todoListCreationDate.setText("");
            todoListEditionDate.setText("");
        }
    }
}
