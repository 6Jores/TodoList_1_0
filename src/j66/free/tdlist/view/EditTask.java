package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.PriorityTask;
import j66.free.tdlist.model.Task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Collections;


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
        if (task.getTodoDate() == null)
            todoDatePicker.setValue(LocalDate.now());
        else
            todoDatePicker.setValue(task.getTodoDate());

        priorityTaskComboBox.getSelectionModel().select(task.getPriority());
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize(){
        todoDatePicker.setShowWeekNumbers(true);

        ObservableList<PriorityTask> observableList = FXCollections.observableArrayList();
        Collections.addAll(observableList, PriorityTask.values());
        priorityTaskComboBox.setItems(observableList);
    }


}
