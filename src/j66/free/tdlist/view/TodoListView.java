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

/**
 * Author : Jores NOUBISSI
 * JavaDoc creation Date : 2019-12-02
 *
 * Class : TodoListView
 * Object : List tasks of a day
 */

public class TodoListView {
    private Main main;
    private LocalDate date;
    private AnchorPane anchorPane;

    private Image todayImage;
    private Image refreshImage;
    private VBox listContent;

    private boolean slimViewOrNot;

    @FXML
    DatePicker datePicker;
    @FXML
    Button todayButton;
    @FXML
    Button updateButton;
    @FXML
    ScrollPane scrollPane;

    /**
     * Initialization view
     */
    @FXML
    private void initialize(){

        datePicker.valueProperty().addListener((observable,oldDate,newDate)->refreshList());

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

    }

    /**
     * Initialization elements
     */
    public void initializeTodoList(){

        listContent.getChildren().clear();

        List<Task> taskList = TodoListManager.getListTaskForDate(date);
        if (taskList.size()!=0){
            for (Task task: taskList){
                ElementView elementView = main.getAnElementView(task);
                if (slimViewOrNot){
                    elementView.descriptionLabel.setVisible(false);
                    elementView.getAnchorPane().setPrefHeight(40);
                    elementView.getStatusLine().setTranslateY(-30);
                }
                listContent.getChildren().add(elementView.getAnchorPane());
            }
        }else {
            listContent.getChildren().add(ElementView.getElementNoTask());
        }
        scrollPane.setContent(listContent);
        datePicker.setValue(date);

    }

    /**
     *
     * @param main The main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     *
     * @param anchorPane set the AnchorPane
     */
    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    /**
     *
     * @param date The TodoList of the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     *
     * @return the anchorPane
     */
    AnchorPane getAnchorPane() {
        return anchorPane;
    }

    /**
     * Refresh to list of task for a date
     */
    @FXML
    public void refreshList(){
        date = datePicker.getValue();
        initializeTodoList();
    }

    /**
     * Show the list of task for today
     */
    @FXML
    private void todayList(){
        date = LocalDate.now();
        initializeTodoList();
    }

    /**
     *
     * @param slimViewOrNot, it's a slimView or not?
     */
    void setSlimViewOrNot(boolean slimViewOrNot) {
        this.slimViewOrNot = slimViewOrNot;
        refreshList();
    }

}