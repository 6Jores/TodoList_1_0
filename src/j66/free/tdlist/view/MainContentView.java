package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.TodoListManager;
import j66.free.tdlist.tools.Tool;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Optional;

public class MainContentView {
    private Main main;
    private Stage stage;

    @FXML
    Pane hierarchyPane;
    @FXML
    Pane todoListPane;
    @FXML
    Menu todoListMenu;
    @FXML
    MenuItem menuItemSave;
    @FXML
    MenuBar menuBar;

    @FXML
    private void initialize(){

        EventHandler<MouseEvent> eventHandler = e -> setItemMenuSave();
        menuBar.addEventHandler(MouseEvent.MOUSE_ENTERED,eventHandler);

    }

    public void setMain(Main main) {
        this.main = main;
        this.main.initHierarchyView(TodoListManager.getTodoList());
        hierarchyPane.getChildren().add(this.main.getControllerHierarchyView().getAnchorPane());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setItemMenuSave(){
        menuItemSave.setDisable(TodoListManager.isSave());
    }

    @FXML
    private void getMainView(){
        if (!TodoListManager.isSave()){
            Alert alert = Tool.getConfirmAlert("Current TodoList it's not save.","Do you really want to quit ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                main.showHome();
            }
        }else
            main.showHome();
    }

    @FXML
    private void saveTodoList(){
        TodoListManager.persistTodoList();
    }

    @FXML
    private void closeApp (){
        if (!TodoListManager.isSave()){
            Alert alert = Tool.getConfirmAlert("Current TodoList it's not save.","Do you really want to quit ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                stage.close();
            }
        }else
            stage.close();

    }
}
