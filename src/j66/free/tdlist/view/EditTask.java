package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.PriorityTask;
import j66.free.tdlist.model.Task;
import j66.free.tdlist.model.TypeElement;
import j66.free.tdlist.tools.Constant;
import j66.free.tdlist.tools.Tool;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Collections;

import static j66.free.tdlist.model.StatusTask.*;

/**
 * Author : Jores NOUBISSI
 * JavaDoc creation Date : 2019-11-06
 *
 * Class : EditTask
 * Object : View for Edition or Creation Task
 */
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Setting Task and Filling form
     * @param task : Created or Edited Task
     */
    public void setTask(Task task) {
        this.task = task;
        if (task.getTodoDate() == null)
            todoDatePicker.setValue(LocalDate.now());
        else
            todoDatePicker.setValue(task.getTodoDate());

        nameTask.setText(task.getName());
        descriptionTask.setText(task.getDescription());
        dailyCheckBox.setSelected(task.isDaily());
        archivedCheckBox.setSelected(task.getStatus() == CANCEL);
        planCheckBox.setSelected(task.getStatus() == PLAN || task.getStatus() == LATE);
        updatePickerEnable();
        priorityTaskComboBox.getSelectionModel().select(task.getPriority());
    }

    /**
     *
     * @param action Creation or Edition
     */
    public void setAction(String action) {
        this.action = action;
        titleLabel.setText(action + " " + TypeElement.TASK);
    }

    /**
     * View initialization
     */
    @FXML
    private void initialize(){
        todoDatePicker.setShowWeekNumbers(true);

        ObservableList<PriorityTask> observableList = FXCollections.observableArrayList();
        Collections.addAll(observableList, PriorityTask.values());
        priorityTaskComboBox.setItems(observableList);
    }

    /**
     * Cancel the action
     */
    @FXML
    private void cancelAction(){
        stage.close();
    }

    /**
     * Check information and save Task
     */
    @FXML
    private void saveTask(){
        if (nameTask.getText().trim().equals("")){
            Tool.showAlert("Form Error","Invalid nameElement, please correct it.");
        }else {
            task.setName(nameTask.getText());
            task.setDescription(descriptionTask.getText());

            task.setPriority(priorityTaskComboBox.getValue());
            task.setDaily(dailyCheckBox.isSelected());

            if (planCheckBox.isSelected()) {
                if (!task.isDaily()){
                    LocalDate date = todoDatePicker.getValue();
                    task.setTodoDate(date);

                    if (date.isBefore(LocalDate.now())) {
                        task.setStatus(LATE);
                    } else {
                        task.setStatus(PLAN);
                    }
                }
                if(task.getDoneDate() != null){
                    task.setDoneDate(null);
                }
            }

            if (archivedCheckBox.isSelected()) {
                task.setStatus(CANCEL);
            }

            if (action.equals(Constant.ACTION_NEW_ELEMENT)){
                main.getControllerHierarchyView().updateAdding();
            }

            stage.close();
        }

    }

    /**
     * Enable or not TodoDate setting
     */
    @FXML
    private void updatePickerEnable(){
        todoDatePicker.setDisable(!planCheckBox.isSelected());
        if(planCheckBox.isSelected() && task.getStatus()== CANCEL){
            archivedCheckBox.setSelected(false);
        }
    }


}
