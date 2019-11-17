package j66.free.tdlist;

import j66.free.tdlist.model.Element;
import j66.free.tdlist.model.Task;
import j66.free.tdlist.model.TodoList;
import j66.free.tdlist.model.TodoListManager;
import j66.free.tdlist.tools.Constant;
import j66.free.tdlist.tools.Tool;
import j66.free.tdlist.view.*;

import javafx.scene.control.Alert;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static j66.free.tdlist.tools.Constant.*;

public class Main extends Application {

    private Stage mainStage;
    private MainContentView controllerMainContentView;
    private WelcomeView controllerWelcomeView;
    private HierarchyView controllerHierarchyView;
    private TodoListView controllerTodoListView;
    private Map<Task, TreeItem<Element>> register = new HashMap<>();

    private Image _appIconImage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle(WELCOME_VIEW_TITLE);

        try {
            _appIconImage = new Image(new FileInputStream(APP_ICON_IMAGE),49,46,false,false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mainStage.getIcons().add(_appIconImage);

        this.initialization();
    }

    private void initialization(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/WelcomeView.fxml"));

        TodoListManager.setTodoLists();

        try{
            AnchorPane mainContent = loader.load();
            Scene scene = new Scene(mainContent);

            mainStage.setScene(scene);
            mainStage.setResizable(false);

            controllerWelcomeView = loader.getController();
            controllerWelcomeView.setMain(this);
            controllerWelcomeView.setScene(scene);

            mainStage.show();

            mainStage.setOnCloseRequest(this::beforeClosingWelcomeView);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void beforeClosingWelcomeView(WindowEvent event){
        Alert alert = Tool.getConfirmAlert("Closing Welcome View","Would you really want to quit ?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.isPresent() && optional.get()==ButtonType.OK){
            mainStage.close();
        }else {
            event.consume();
        }
    }

    public void showAboutView(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/AboutView.fxml"));
        try{
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("About App");
            stage.initModality(Modality.WINDOW_MODAL);

            stage.initOwner(mainStage);

            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(_appIconImage);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateWelcomeView (){
        controllerWelcomeView.updateFileListView();
    }

    public void showEditElement(Element element, String action){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EditElement.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle(action+" "+ element.getTypeElement());
            stage.initModality(Modality.WINDOW_MODAL);

            stage.initOwner(mainStage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(_appIconImage);

            EditElement controller = loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            controller.setElement(element);
            controller.setAction(action);

            stage.showAndWait();

            if (controllerTodoListView != null){
                controllerTodoListView.refreshList();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openATodoList(TodoList todoList){

        TodoListManager.setTodoList(todoList);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MainContentView.fxml"));

        try{
            AnchorPane mainContent = loader.load();
            Scene scene = new Scene(mainContent);

            mainStage.setScene(scene);
            mainStage.setResizable(false);

            controllerMainContentView = loader.getController();
            controllerMainContentView.setMain(this);
            controllerMainContentView.setStage(mainStage);

            controllerMainContentView.endInitialization();

            mainStage.setWidth(515);
            mainStage.setHeight(640);
            mainStage.setOnCloseRequest(this::beforeClosingMainContentView);

            mainStage.setTitle(TodoListManager.getTodoList().getName()+" - "+ APP_NAME);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void beforeClosingMainContentView(WindowEvent event){
        if (!TodoListManager.isSave()){
            Alert alert = Tool.getConfirmAlert("Closing the application","Would you really want to quit " +
                    "without saving?");
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.isPresent() && optional.get()==ButtonType.OK){
                mainStage.close();
            }else{
                event.consume();
            }
        }
    }

    public void initHierarchyView(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/HierarchyView.fxml"));

        try{
            AnchorPane mainContent = loader.load();

            controllerHierarchyView = loader.getController();

            controllerHierarchyView.setAnchorPane(mainContent);
            controllerHierarchyView.setMain(this);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initTodoListView(LocalDate date){
        FXMLLoader loader =  new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/TodoListView.fxml"));

        try {
            AnchorPane mainContent = loader.load();

            controllerTodoListView = loader.getController();

            controllerTodoListView.setAnchorPane(mainContent);
            controllerTodoListView.setDate(date);
            controllerTodoListView.setMain(this);

            controllerTodoListView.initializeTodoList();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ElementView getAnElementView(Task task){
        ElementView elementView = new ElementView();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ElementView.fxml"));

        try{
            AnchorPane mainContent = loader.load();

            elementView = loader.getController();

            elementView.setMain(this);
            elementView.setAnchorPane(mainContent);
            elementView.setTask(task);
            elementView.initElementView();

        }catch (IOException e){
            e.printStackTrace();
        }

        return elementView;
    }

    public HierarchyView getControllerHierarchyView() {
        return controllerHierarchyView;
    }

    public WelcomeView getControllerWelcomeView() {
        return controllerWelcomeView;
    }

    public MainContentView getControllerMainContentView() {
        return controllerMainContentView;
    }

    public TodoListView getControllerTodoListView() {
        return controllerTodoListView;
    }

    public void showHome(){
        mainStage.setScene(controllerWelcomeView.getScene());
        mainStage.setWidth(525);
        mainStage.setHeight(450);
    }

    public void showEditTask(Task task, String action){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/EditTask.fxml"));

        try{
            AnchorPane mainContent = loader.load();

            Stage stage = new Stage();
            stage.setTitle(action+" Task");
            stage.initModality(Modality.WINDOW_MODAL);

            stage.initOwner(mainStage);
            Scene scene = new Scene(mainContent);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(_appIconImage);

            EditTask controller = loader.getController();
            controller.setTask(task);
            controller.setAction(action);
            controller.setStage(stage);

            controller.setMain(this);

            stage.showAndWait();

            if (controllerTodoListView != null){
                controllerTodoListView.refreshList();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateRegister(Task task, TreeItem<Element> treeItem){
        register.put(task,treeItem);
    }

    public Map<Task, TreeItem<Element>> getRegister() {
        return register;
    }
}
