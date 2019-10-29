package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.Task;
import j66.free.tdlist.model.TodoListManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class TodoListView {
    private Main main;
    private LocalDate date;
    private AnchorPane anchorPane;

    @FXML
    VBox listContent;
    @FXML
    DatePicker datePicker;
    @FXML
    Button todayButton;
    @FXML
    Button updateButton;

    @FXML
    private void initialize(){

    }

    public void initializeTodoList(){
        List<Task> taskList = TodoListManager.getListTaskForDate(date);
        for (Task task: taskList){
            listContent.getChildren().add(main.getAnElementView(task).getAnchorPane());
        }
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

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}
