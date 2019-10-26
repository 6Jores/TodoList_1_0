package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.TodoList;
import j66.free.tdlist.model.TodoListManager;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MainContentView {
    private Main main;

    @FXML
    Pane hierarchyPane;
    @FXML
    Pane todoListPane;


    public void setMain(Main main) {
        this.main = main;
        this.main.initHierarchyView(TodoListManager.getTodoList());
        hierarchyPane.getChildren().add(this.main.getControllerHierarchyView().getAnchorPane());
    }

}
