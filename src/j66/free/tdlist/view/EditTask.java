package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.PriorityTask;
import j66.free.tdlist.model.Task;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class EditTask {

    private Task task;
    private String action;
    private Stage stage;
    private Main main;

    @FXML
    TextField nameTask;
    @FXML
    TextArea descriptionTask;
    @FXML
    DatePicker todoDatePicker;
    @FXML
    ComboBox<PriorityTask> priorityTaskComboBox;
    @FXML
    CheckBox dailyCheckBox;
    @FXML
    CheckBox archivedCheckBox;
    @FXML
    CheckBox planCheckBox;
    @FXML
    Label titleLabel;


    public void setMain(Main main) {
        this.main = main;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


}
