package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.StatusTask;
import j66.free.tdlist.model.Task;
import j66.free.tdlist.model.TaskManager;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.time.LocalDate;

/**
 * Author : Jores NOUBISSI
 * JavaDoc creation Date : 2019-12-02
 *
 * Class : ElementView
 * Object : View of an element for ListView
 */
public class ElementView {
    @FXML
    CheckBox doOrNotCheckBox;
    @FXML
    Label descriptionLabel;
    @FXML
    Button menuButton;
    @FXML
    Line statusLine;
    @FXML
    ContextMenu contextMenu;

    private Main main;
    private Task task;
    private AnchorPane anchorPane;

    static private Label elementNoTask;

    /**
     * Initialization of listeners
     */
    @FXML
    private void initialize(){
        EventHandler<MouseEvent> eventHandler = e -> contextMenu.show(menuButton,e.getScreenX(),e.getScreenY());
        menuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    /**
     *
     * @param main : The general main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * setting and initialization
     * @param anchorPane the anchorPane
     */
    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
        menuButton.setStyle("-fx-text-fill: #2D3443");
        this.anchorPane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> menuButton.setStyle("-fx-text-fill: #4fd4e5"));
        this.anchorPane.addEventHandler(MouseEvent.MOUSE_EXITED,event -> menuButton.setStyle("-fx-text-fill:#2D3443"));
    }

    /**
     *
     * @param task : the task of this view
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Initialization
     * - Title
     * - Context menu
     * - Description
     * - Tooltip
     */
    public void initElementView(){

        doOrNotCheckBox.setText(task.getName());
        doOrNotCheckBox.setSelected(task.getStatus()== StatusTask.DONE);

        descriptionLabel.setText(task.getDescription());

        MenuItem rePlanItem = new MenuItem("RePlan");
        rePlanItem.setOnAction(actionEvent -> rePlanTask());
        contextMenu.getItems().add(rePlanItem);

        MenuItem archiveItem = new MenuItem("Archived");
        archiveItem.setOnAction(actionEvent -> archiveTask());
        contextMenu.getItems().add(archiveItem);

        MenuItem setDailyItem = new MenuItem("Set daily");
        if (task.isDaily()){
            setDailyItem.setText("UnSet Daily");
        }

        setDailyItem.setOnAction(actionEvent -> setDailyTask());
        contextMenu.getItems().add(setDailyItem);

        switch (task.getPriority()){

            case VERY_HIGH:
                statusLine.setStroke(Color.RED);
                break;
            case HIGH:
                statusLine.setStroke(Color.ORANGE);
                break;
            case NORMAL:
                statusLine.setStroke(Color.GREEN);
                break;
            case LOW:
                statusLine.setStroke(Color.BLUE);
                break;
            case VERY_LOW:
                statusLine.setStroke(Color.GREY);
                break;
        }

        String info = TaskManager.getParentInfoForTask(task);

        if (!info.equals("")){
            Tooltip tooltip = new Tooltip(info);

            tooltip.setPrefWidth(150);
            tooltip.setWrapText(true);

            doOrNotCheckBox.setTooltip(tooltip);
            descriptionLabel.setTooltip(tooltip);
        }

        if (task.isDaily()){
            doOrNotCheckBox.setStyle("-fx-text-fill:#f15a23");
        }

        if (task.getDoneDate() != null && task.getDoneDate().isEqual(LocalDate.now())){

            doOrNotCheckBox.setStyle("-fx-text-fill:#b2b2b1;");
            if (task.isDaily()){
                doOrNotCheckBox.setStyle("-fx-text-fill:#b2b2b1;"+"-fx-font-style:italic;");
            }
        }

    }

    /**
     *
     * @return the anchorPane
     */
    AnchorPane getAnchorPane() {
        return anchorPane;
    }

    /**
     * Get EditTaskView
     */
    private void rePlanTask(){
        main.getControllerHierarchyView().setSelectedElement(main.getRegister().get(task),task);
        main.getControllerHierarchyView().editElement();
        main.getControllerTodoListView().refreshList();
    }

    /**
     * Set a task archived
     */
    private void archiveTask(){
        main.getControllerHierarchyView().setSelectedElement(main.getRegister().get(task),task);
        main.getControllerHierarchyView().cancelTask();
        main.getControllerTodoListView().refreshList();
    }

    /**
     * Set a task daily
     */
    private void setDailyTask(){
        main.getControllerHierarchyView().setSelectedElement(main.getRegister().get(task),task);
        main.getControllerHierarchyView().setTaskDaily();
        main.getControllerTodoListView().refreshList();
    }

    /**
     * Set a task done
     */
    @FXML
    private void setDone(){
        TaskManager.setTaskDone(task);
        main.getControllerHierarchyView().setSelectedElement(main.getRegister().get(task),task);
        main.getControllerTodoListView().refreshList();
    }

    /**
     * To notify no Task for a day
     * @return element not task
     */
    static Label getElementNoTask(){
        if (elementNoTask == null)
            setElementNoTask();
        return elementNoTask;
    }

    /**
     * Init ElementNoTask
     */
    static private void setElementNoTask(){
        elementNoTask = new Label("No Tasks for this day.");
        elementNoTask.setStyle("-fx-alignment:center; -fx-font-size:12pt");
        elementNoTask.setTextFill(Color.web("#b2b2b1"));
        elementNoTask.setPrefWidth(220);
    }

    /**
     *
     * @return the line to show the priority
     */
    Line getStatusLine() {
        return statusLine;
    }
}
