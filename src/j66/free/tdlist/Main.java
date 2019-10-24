package j66.free.tdlist;

import j66.free.tdlist.model.TodoListManager;
import j66.free.tdlist.view.WelcomeView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static j66.free.tdlist.tools.Constant.*;

public class Main extends Application {

    private Stage mainStage;
    private AnchorPane mainContent;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle(WELCOME_VIEW_TITLE);

        this.initialization();
    }

    private void initialization(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/WelcomeView.fxml"));

        TodoListManager.setTodoLists();

        try{
            mainContent = loader.load();
            Scene scene = new Scene(mainContent);

            mainStage.setScene(scene);

            //Run Start viewcontroller
            WelcomeView controller = loader.getController();
            controller.setMain(this);

            mainStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
