package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.PriorityTask;
import j66.free.tdlist.model.StatusTask;
import j66.free.tdlist.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Collections;

import static j66.free.tdlist.model.StatusTask.*;

public class EditTask {

    private Task task;
    private Main main;
    private String action;
    private Stage stage;

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
    private void cancel(){
        stage.close();
    }
    @FXML
    private void saveTask(){
        task.setName(nameTask.getText());
        task.setDescription(descriptionTask.getText());

        if (planCheckBox.isSelected()){
            LocalDate date = todoDatePicker.getValue();
            task.setTodoDate(date);
            if(date.isBefore(LocalDate.now())){
                task.setStatus(LATE);
            }else {
                task.setStatus(PLAN);
            }
        }

        task.setPriority(priorityTaskComboBox.getValue());
        task.setDaily(dailyCheckBox.isSelected());
        if (archivedCheckBox.isSelected()){
            task.setStatus(CANCEL);
        }

        stage.close();

    }
    @FXML
    private void initialize(){
        todoDatePicker.setValue(LocalDate.now());
        todoDatePicker.setShowWeekNumbers(true);

        ObservableList<PriorityTask> observableList = FXCollections.observableArrayList();
        Collections.addAll(observableList, PriorityTask.values());
        priorityTaskComboBox.setItems(observableList);
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

    public void setMain(Main main) {
        this.main = main;

        nameTask.setText(task.getName());
        descriptionTask.setText(task.getDescription());
        dailyCheckBox.setSelected(task.isDaily());
        archivedCheckBox.setSelected(task.getStatus() == CANCEL);
        planCheckBox.setSelected(task.getStatus() == PLAN || task.getStatus() == LATE);
        updatePickerEnable();

        priorityTaskComboBox.setValue(task.getPriority());


    }

    @FXML
    private void updatePickerEnable(){
        todoDatePicker.setDisable(!planCheckBox.isSelected());
    }

}