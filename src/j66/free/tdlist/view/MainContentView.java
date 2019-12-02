package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.Element;
import j66.free.tdlist.model.TodoListManager;
import j66.free.tdlist.tools.Tool;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Optional;

import static j66.free.tdlist.tools.Constant.*;

/**
 * Author : Jores NOUBISSI
 * JavaDoc creation Date : 2019-12-02
 *
 * Class : MainContentView
 * Object : Manage HierarchyView and TodoListView
 */
public class MainContentView {
    private Main main;
    private Stage stage;

    private Image _bothViewImage;
    private Image _hierarchyViewImage;
    private Image _todoListViewImage;
    private Image _planTaskImage;
    private Image _addElementImage;
    private Image _editElementImage;
    private Image _removeElementImage;


    @FXML
    HBox hBox;
    @FXML
    Pane hierarchyPane;
    @FXML
    ToolBar toolBar;
    @FXML
    Pane todoListPane;
    @FXML
    Menu todoListMenu;
    @FXML
    MenuItem menuItemSave;
    @FXML
    MenuBar menuBar;
    @FXML
    Button addButton;
    @FXML
    Button planButton;
    @FXML
    Button editButton;
    @FXML
    Button hierarchyButton;
    @FXML
    Button bothButton;
    @FXML
    Button listButton;
    @FXML
    Button removeButton;
    @FXML
    Line line;

    /**
     * Initialization
     */
    @FXML
    private void initialize(){
        EventHandler<MouseEvent> eventHandler = e -> setItemMenuSave();
        menuBar.addEventHandler(MouseEvent.MOUSE_ENTERED,eventHandler);
    }

    /**
     * Images initialization
     */
    private void initializeImages(){
        try{
            _bothViewImage = new Image(new FileInputStream(BOTH_VIEW_IMAGE),20,20,false,false);
            _hierarchyViewImage = new Image(new FileInputStream(HIERARCHY_VIEW_IMAGE),20,20,false,false);
            _todoListViewImage = new Image(new FileInputStream(TODOLIST_VIEW_IMAGE),20,20,false,false);
            _addElementImage = new Image(new FileInputStream(ADD_ELEMENT_IMAGE),20,20,false,false);
            _editElementImage = new Image(new FileInputStream(EDIT_ELEMENT_IMAGE),20,20,false,false);
            _removeElementImage = new Image(new FileInputStream(REMOVE_ELEMENT_IMAGE),20,20,false,false);
            _planTaskImage =  new Image(new FileInputStream(PLAN_TASK_IMAGE),20,20,false,false);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param main the main
     */
    public void setMain(Main main) {
        this.main = main;
        initializeImages();
        initToolBar();
    }

    /**
     * Finalization of initialization
     * Adding Hierarchy view and TodoListView
     */
    public void endInitialization(){
        this.main.initHierarchyView();
        this.main.initTodoListView(LocalDate.now());
        hierarchyPane.getChildren().add(this.main.getControllerHierarchyView().getAnchorPane());
        todoListPane.getChildren().add(this.main.getControllerTodoListView().getAnchorPane());
    }

    /**
     *
     * @param stage the stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Setting ItemMenu Save
     */
    private void setItemMenuSave(){
        menuItemSave.setDisable(TodoListManager.isSave());
    }

    /**
     * ToolBar initialization
     */
    private void initToolBar(){
        addButton.setGraphic(new ImageView(_addElementImage));
        addButton.setTooltip(new Tooltip("Add Element"));
        addButton.setDisable(true);

        planButton.setGraphic(new ImageView(_planTaskImage));
        planButton.setTooltip(new Tooltip("Plan Task"));
        planButton.setDisable(true);
        editButton.setGraphic(new ImageView(_editElementImage));
        editButton.setTooltip(new Tooltip("Edit Element"));
        editButton.setDisable(true);
        removeButton.setGraphic(new ImageView(_removeElementImage));
        removeButton.setTooltip(new Tooltip("Remove Element"));
        removeButton.setDisable(true);

        hierarchyButton.setGraphic(new ImageView(_hierarchyViewImage));
        hierarchyButton.setTooltip(new Tooltip("Just Hierarchy view"));
        bothButton.setGraphic(new ImageView(_bothViewImage));
        bothButton.setTooltip(new Tooltip("Both views"));
        bothButton.setDisable(true);
        listButton.setGraphic(new ImageView(_todoListViewImage));
        listButton.setTooltip(new Tooltip("Just TodoList View"));
    }

    /**
     * ToolBar updating
     * @param selectedElement : selected element
     */
    void updateToolBarElement(Element selectedElement){
        if (toolBar.isVisible()){
            switch (selectedElement.getTypeElement()){
                case TODOLIST:
                    addButton.setDisable(false);
                    planButton.setDisable(true);
                    editButton.setDisable(true);
                    removeButton.setDisable(true);
                    break;
                case PROJECT:
                case SUBPROJECT:
                    addButton.setDisable(false);
                    planButton.setDisable(true);
                    editButton.setDisable(false);
                    removeButton.setDisable(false);
                    break;
                case TASK:
                    addButton.setDisable(true);
                    planButton.setDisable(false);
                    editButton.setDisable(false);
                    removeButton.setDisable(false);
                    break;
            }
        }
    }

    /**
     * Closing mainContent and return to welcomeView
     */
    @FXML
    private void returnToWelcomeView(){
        if (!TodoListManager.isSave()){
            Alert alert = Tool.getConfirmAlert("Current TodoList it's not save.","Do you really want to quit ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                main.showHome();
            }
        }else
            main.showHome();
    }

    /**
     * Action save current todoList
     */
    @FXML
    private void saveTodoList(){
        TodoListManager.persistTodoList();
    }

    /**
     * Close the application
     */
    @FXML
    private void closeApp (){
        if (!TodoListManager.isSave()){
            Alert alert = Tool.getConfirmAlert("Current TodoList it's not save.","Do you really want to quit ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                stage.close();
            }
        }else
            stage.close();

    }

    /**
     * Adding and element on hierarchyView
     */
    @FXML
    private void addElement(){
        main.getControllerHierarchyView().addElement();
    }

    /**
     * Plan the task selected on hierarchyView
     */
    @FXML
    private void planTask(){
        main.getControllerHierarchyView().planTask();
    }

    /**
     * Edit the element selected on hierarchyView
     */
    @FXML
    private void editElement(){
        main.getControllerHierarchyView().editElement();
    }

    /**
     * Remove the element selected on hierarchyView
     */
    @FXML
    private void removeElement(){
        main.getControllerHierarchyView().removeElement();
    }

    /**
     * Show just the hierarchyView
     */
    @FXML
    private void justHierarchyView(){
        hBox.getChildren().removeAll(hierarchyPane,todoListPane);
        hBox.getChildren().add(hierarchyPane);

        setViewSize(0,640,265,true);

        hierarchyButton.setDisable(true);
        bothButton.setDisable(false);
        listButton.setDisable(false);

        main.getControllerTodoListView().setSlimViewOrNot(false);
    }

    /**
     * Show just the TodoListView
     */
    @FXML
    private void justTodoListView(){

        hBox.getChildren().removeAll(hierarchyPane,todoListPane);
        hBox.getChildren().add(todoListPane);

        setViewSize(0,640,265,true);

        hierarchyButton.setDisable(false);
        bothButton.setDisable(false);
        listButton.setDisable(true);

        main.getControllerTodoListView().setSlimViewOrNot(false);
    }

    /**
     * Show the TodoListView and hierarchyView
     */
    @FXML
    private void bothView(){
        hBox.getChildren().removeAll(hierarchyPane,todoListPane);
        hBox.getChildren().add(hierarchyPane);
        hBox.getChildren().add(todoListPane);

        setViewSize(0,640,515,true);

        hierarchyButton.setDisable(false);
        bothButton.setDisable(true);
        listButton.setDisable(false);

        main.getControllerTodoListView().setSlimViewOrNot(false);
    }

    /**
     * Show slimView
     */
    @FXML
    private void slimView(){
        hBox.getChildren().removeAll(hierarchyPane,todoListPane);
        hBox.getChildren().add(todoListPane);

        setViewSize(-43,640-43,265,false);
        main.getControllerTodoListView().setSlimViewOrNot(true);
    }

    /**
     * Show About the application
     */
    @FXML
    private void aboutView(){
        main.showAboutView();
    }

    /**
     * for slimView
     * @param visibility visible or not
     */
    private void setHeadVisibility(boolean visibility){
        line.setVisible(visibility);
        toolBar.setVisible(visibility);
    }

    /**
     *
     * @param offset - HBox offset
     * @param height - scene height
     * @param width - scene width
     * @param headVisibility - head or not
     */
    private void setViewSize(int offset, int height, int width, boolean headVisibility){
        hBox.setTranslateY(offset);
        stage.setHeight(height);
        stage.setWidth(width);
        setHeadVisibility(headVisibility);
    }


}
