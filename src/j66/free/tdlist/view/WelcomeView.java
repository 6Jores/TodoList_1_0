package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.TodoList;
import j66.free.tdlist.model.TodoListManager;
import j66.free.tdlist.tools.Tool;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.EventHandler;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;




public class WelcomeView {

    private Main main;
    private ContextMenu contextMenu;
    private TodoList selectedTodoList;


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

        EventHandler<MouseEvent> eventHandler = e -> {
            if (contextMenu!=null && contextMenu.isShowing())
                contextMenu.hide();
            if (e.getButton() == MouseButton.SECONDARY){
                showPop(e);
            }
        };

        fileListView.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandler);

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

    @FXML
    private void openTodoList(){
        System.out.println("Open");
        if (thereIsASelected()){

        }else {
            showAlert("Error","Please select a TodoList.");
        }
    }


    private void archiveTodoList(){
        selectedTodoList.setArchived(!selectedTodoList.isArchived());
        setArchivedOrNot();
        TodoListManager.persistATodoList(selectedTodoList);
    }


    private void newTodoList(){
        System.out.println("New");
    }


    private void copyTodoList(){
        if (!TodoListManager.copyTodoList(selectedTodoList))
            showAlert("Copy Error","And error had occurred during the copy.");
        else {
            fileListView.getItems().clear();
            fileListView.getItems().addAll(TodoListManager.getTodoLists(withArchivedOrNot.isSelected()));
        }
    }

    private void deleteTodoList(){
        if (!TodoListManager.deleteTodoList(selectedTodoList))
            showAlert("Delete Error","And error had occurred during the delete.");
        else {
            fileListView.getItems().clear();
            fileListView.getItems().addAll(TodoListManager.getTodoLists(withArchivedOrNot.isSelected()));
        }
    }



    private boolean thereIsASelected(){
        boolean rtn = fileListView.getSelectionModel().getSelectedItem() != null;
        if (rtn)
            selectedTodoList = fileListView.getSelectionModel().getSelectedItem();
        return rtn;
    }

    private void showPop(MouseEvent event){

        contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("New");
        item1.setOnAction(actionEvent -> newTodoList());

        if (thereIsASelected()){
            MenuItem item2 = new MenuItem("Open");
            item2.setOnAction(actionEvent -> openTodoList());
            contextMenu.getItems().add(item2);

            MenuItem item3 = new MenuItem("Copy");
            item3.setOnAction(actionEvent -> copyTodoList());
            contextMenu.getItems().add(item3);

            contextMenu.getItems().add(new SeparatorMenuItem());

            CheckMenuItem item4 = new CheckMenuItem("Archived");
            item4.setSelected(selectedTodoList.isArchived());
            item4.setOnAction(actionEvent -> archiveTodoList());
            contextMenu.getItems().add(item4);

            MenuItem item5 = new MenuItem("Delete");
            item5.setOnAction(actionEvent -> deleteTodoList());
            contextMenu.getItems().add(item5);

            contextMenu.getItems().add(new SeparatorMenuItem());

        }

        contextMenu.getItems().add(item1);

        fileListView.setOnContextMenuRequested(contextMenuEvent -> contextMenu.show(fileListView, event.getScreenX(),event.getScreenY()));
    }

    public Main getMain() {
        return main;
    }

    public void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
