package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.TodoList;
import j66.free.tdlist.model.TodoListManager;
import j66.free.tdlist.tools.Constant;
import j66.free.tdlist.tools.Tool;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.EventHandler;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

import static j66.free.tdlist.tools.Tool.showAlert;


public class WelcomeView {

    private Main main;
    private ContextMenu contextMenu;
    private Scene scene;
    private TodoList selectedTodoList;

    @FXML
    TextField search;
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
    @FXML
    Tooltip tooltip;
    @FXML
    Label labelAbout;

    public void setMain(Main main){
        this.main = main;

        fileListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        updateFileListView();

        initializeDescription(null);
    }

    @FXML
    private void initialize(){
        fileListView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue)
                -> initializeDescription(newValue)));

        EventHandler<MouseEvent> eventHandler = e -> {
            if (contextMenu!=null && contextMenu.isShowing())
                contextMenu.hide();
            if (e.getButton() == MouseButton.SECONDARY){
                showPop(e);
            } else if (e.getButton() == MouseButton.PRIMARY){
              if (e.getClickCount()==2 && thereIsASelected())
                  openTodoList();

            }
        };

        fileListView.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandler);

        this.search.textProperty().addListener(((observableValue, oldValue, newValue) -> runSearch(newValue)));

        labelAbout.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton()==MouseButton.PRIMARY)
                showAbout();
        });

    }

    @FXML
    public void setArchivedOrNot(){
        if (search.getText().equals(""))
            fileListView.setItems(TodoListManager.getTodoLists(withArchivedOrNot.isSelected()));
        else
            runSearch(search.getText());
    }

    void initializeDescription(TodoList todoList){
        if (todoList != null && !todoList.getName().equals(Constant.NO_RESULT)){
            todoListName.setText(todoList.getName());
            todoListDescription.setText(todoList.getDescription());
            todoListCreationDate.setText(Tool.formatDate(todoList.getCreationDate()));
            todoListEditionDate.setText(Tool.formatDate(todoList.getEditionDate()));
            if (todoList.getDescription().length() > 70){
                tooltip.setText(todoList.getDescription());
            }
            else
                tooltip.setText("Description");
        }
        else {
            todoListName.setText("");
            todoListDescription.setText("");
            todoListCreationDate.setText("");
            todoListEditionDate.setText("");
            tooltip.setText("Description");
        }
    }

    @FXML
    private void openTodoList(){
        if (thereIsASelected()){
            Alert alert = Tool.getConfirmAlert("Open TodoList...", "With auto save?");
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.isPresent() && optional.get()==ButtonType.OK){
                TodoListManager.setAutoSave(true);
            }else{
                TodoListManager.setAutoSave(false);
            }
            main.openATodoList(selectedTodoList);
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
        search.setText("");
        main.showEditElement(TodoListManager.getTodoList(true), Constant.ACTION_NEW_ELEMENT);
    }

    private void copyTodoList(){
        if (!TodoListManager.copyTodoList(selectedTodoList))
            showAlert("Copy Error","And error had occurred during the copy.");
        else {
            updateFileListView(TodoListManager.getTodoLists(withArchivedOrNot.isSelected()));
        }
    }

    private void deleteTodoList(){
        Alert alert = Tool.getConfirmAlert("Confirm suppression","TodoList : "+selectedTodoList.getName());
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            if (!TodoListManager.deleteTodoList(selectedTodoList))
                showAlert("Delete Error","And error had occurred during the delete.");
            else {
                search.setText("-");
                search.setText("");
            }
        }
    }

    private void editTodoList(){
        main.showEditElement(selectedTodoList, Constant.ACTION_EDIT_ELEMENT);
    }

    private void runSearch(String key){
        if (key != null){
            updateFileListView(TodoListManager.getTodoLists(key,withArchivedOrNot.isSelected()));
        }
    }

    private void updateFileListView(ObservableList<TodoList> todoLists){
        fileListView.getItems().clear();
        fileListView.getItems().addAll(todoLists);

    }

    public void updateFileListView(){
        fileListView.getItems().clear();
        fileListView.getItems().addAll(TodoListManager.getTodoLists(withArchivedOrNot.isSelected()));
    }

    private boolean thereIsASelected(){
        boolean rtn = fileListView.getSelectionModel().getSelectedItem() != null;
        if (rtn) {
            selectedTodoList = fileListView.getSelectionModel().getSelectedItem();
            rtn = !selectedTodoList.getName().equals(Constant.NO_RESULT);
        }
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

            MenuItem item6 = new MenuItem("Edit");
            item6.setOnAction(actionEvent -> editTodoList());
            contextMenu.getItems().add(item6);

        }

        contextMenu.getItems().add(item1);

        fileListView.setOnContextMenuRequested(contextMenuEvent -> contextMenu.show(fileListView, event.getScreenX(),event.getScreenY()));
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private void showAbout(){
        main.showAboutView();
    }
}
