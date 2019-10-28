package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.TodoListManager;
import j66.free.tdlist.tools.Tool;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import static j66.free.tdlist.tools.Constant.*;
import static j66.free.tdlist.tools.Constant.DAILY_IMAGE;

public class MainContentView {
    private Main main;
    private Stage stage;

    private Image _projectImage;
    private Image _subProjectImage;
    private Image _planImage;
    private Image _noPlanImage;
    private Image _lateImage;
    private Image _doneImage;
    private Image _cancelImage;
    private Image _dailyImage;
    private Image _bothViewImage;
    private Image _hierarchyViewImage;
    private Image _todoListViewImage;
    private Image _planTaskImage;
    private Image _addElementImage;
    private Image _editElementImage;
    private Image _removeElementImage;

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
    private void initialize(){

        EventHandler<MouseEvent> eventHandler = e -> setItemMenuSave();
        menuBar.addEventHandler(MouseEvent.MOUSE_ENTERED,eventHandler);

    }

    private void initializeImages(){
        try{
            _projectImage = new Image(new FileInputStream(PROJECT_IMAGE),20,20,false,false);
            _subProjectImage = new Image(new FileInputStream( SUB_PROJECT_IMAGE),20,20,false,false);
            _planImage = new Image(new FileInputStream(PLAN_IMAGE),20,20,false,false);
            _noPlanImage = new Image(new FileInputStream(NO_PLAN_IMAGE),20,20,false,false);
            _lateImage = new Image(new FileInputStream(LATE_IMAGE),20,20,false,false);
            _doneImage = new Image(new FileInputStream(DONE_IMAGE),20,20,false,false);
            _cancelImage = new Image(new FileInputStream(CANCEL_IMAGE),20,20,false,false);
            _dailyImage = new Image(new FileInputStream(DAILY_IMAGE),20,20,false,false);

            _bothViewImage = new Image(new FileInputStream(BOTH_VIEW_IMAGE),15,15,false,false);
            _hierarchyViewImage = new Image(new FileInputStream(HIERARCHY_VIEW_IMAGE),15,15,false,false);
            _todoListViewImage = new Image(new FileInputStream(TODOLIST_VIEW_IMAGE),15,15,false,false);
            _addElementImage = new Image(new FileInputStream(ADD_ELEMENT_IMAGE),15,15,false,false);
            _editElementImage = new Image(new FileInputStream(EDIT_ELEMENT_IMAGE),15,15,false,false);
            _removeElementImage = new Image(new FileInputStream(REMOVE_ELEMENT_IMAGE),15,15,false,false);
            _planTaskImage =  new Image(new FileInputStream(PLAN_TASK_IMAGE),15,15,false,false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        this.main = main;
        initializeImages();
        initToolBox();
    }

    public void endInitialization(){
        this.main.initHierarchyView();
        hierarchyPane.getChildren().add(this.main.getControllerHierarchyView().getAnchorPane());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setItemMenuSave(){
        menuItemSave.setDisable(TodoListManager.isSave());
    }

    private void initToolBox(){
        addButton.setGraphic(new ImageView(_addElementImage));
        addButton.setTooltip(new Tooltip("Add Element"));

        planButton.setGraphic(new ImageView(_planTaskImage));
        addButton.setTooltip(new Tooltip("Plan Task"));
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

    @FXML
    private void addElement(){

    }

    @FXML
    private void planTask(){

    }

    @FXML
    private void editElement(){

    }

    @FXML
    private void removeElement(){

    }

    @FXML
    private void justHierarchyView(){

    }

    @FXML
    private void justListView(){

    }

    @FXML
    private void bothView(){

    }


}
