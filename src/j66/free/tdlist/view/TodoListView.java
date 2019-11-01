package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.Task;
import j66.free.tdlist.model.TodoListManager;
import j66.free.tdlist.tools.Constant;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

public class TodoListView {
    private Main main;
    private LocalDate date;
    private AnchorPane anchorPane;

    private Image todayImage;
    private Image refreshImage;
    private VBox listContent;


    @FXML
    DatePicker datePicker;
    @FXML
    Button todayButton;
    @FXML
    Button updateButton;
    @FXML
    ScrollPane scrollPane;

    @FXML
    private void initialize(){

        try{
            todayImage= new Image(new FileInputStream(Constant.TODAY_IMAGE),15,15,false,false);
            refreshImage= new Image(new FileInputStream(Constant.REFRESH_IMAGE),15,15,false,false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        todayButton.setGraphic(new ImageView(todayImage));
        todayButton.setTooltip(new Tooltip("Today List"));
        updateButton.setGraphic(new ImageView(refreshImage));
        updateButton.setTooltip(new Tooltip("Refresh List"));

        listContent = new VBox();
        listContent.setPrefWidth(220);
        scrollPane.setStyle("-fx-background:none");
        scrollPane.setStyle("-fx-border-color:#b2b2b1");

    }

    public void initializeTodoList(){

        listContent.getChildren().clear();

        List<Task> taskList = TodoListManager.getListTaskForDate(date);
        for (Task task: taskList){
            listContent.getChildren().add(main.getAnElementView(task).getAnchorPane());
        }
        scrollPane.setContent(listContent);
        datePicker.setValue(date);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    AnchorPane getAnchorPane() {
        return anchorPane;
    }

    @FXML
    void refreshList(){
        System.out.println("TodoListView - Refresh");
        date = datePicker.getValue();
        initializeTodoList();
    }

    @FXML
    private void todayList(){
        System.out.println("TodoListView - Today");
        date = LocalDate.now();
        initializeTodoList();
    }
}
