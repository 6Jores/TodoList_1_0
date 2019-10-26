package j66.free.tdlist;

import j66.free.tdlist.model.TodoList;
import j66.free.tdlist.model.TodoListManager;
import j66.free.tdlist.view.EditTodoList;
import j66.free.tdlist.view.HierarchyView;
import j66.free.tdlist.view.MainContentView;
import j66.free.tdlist.view.WelcomeView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static j66.free.tdlist.tools.Constant.*;

public class Main extends Application {

    private Stage mainStage;
    private MainContentView controllerMainContentView;
    private WelcomeView controllerWelcomeView;
    private HierarchyView controllerHierarchyView;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle(WELCOME_VIEW_TITLE);

        this.initialization();
    }

    public void initialization(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/WelcomeView.fxml"));

        TodoListManager.setTodoLists();

        try{
            AnchorPane mainContent = loader.load();
            Scene scene = new Scene(mainContent);

            mainStage.setScene(scene);

            //Run Start viewController
            controllerWelcomeView = loader.getController();
            controllerWelcomeView.setMain(this);
            controllerWelcomeView.setScene(scene);

            mainStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateWelcomeView (){
        controllerWelcomeView.updateFileListView();
    }

    public void showEditTodoList(TodoList todoList, String action){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EditTodoList.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle(action+" "+ APP_NAME);
            stage.initModality(Modality.WINDOW_MODAL);

            stage.initOwner(mainStage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);

            EditTodoList controller = loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            controller.setTodoList(todoList);
            controller.setAction(action);

            stage.showAndWait();


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

            //Run Start viewcontroller
            controllerMainContentView = loader.getController();
            controllerMainContentView.setMain(this);
            controllerMainContentView.setStage(mainStage);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initHierarchyView(TodoList todoList){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/HierarchyView.fxml"));

        try{
            AnchorPane mainContent = loader.load();

            controllerHierarchyView = loader.getController();

            controllerHierarchyView.setAnchorPane(mainContent);
            controllerHierarchyView = loader.getController();
            controllerHierarchyView.setMain(this);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public HierarchyView getControllerHierarchyView() {
        return controllerHierarchyView;
    }

    public void showHome(){
        mainStage.setScene(controllerWelcomeView.getScene());
    }
}
