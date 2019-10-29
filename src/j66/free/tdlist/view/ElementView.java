package j66.free.tdlist.view;

import j66.free.tdlist.Main;

import j66.free.tdlist.model.StatusTask;
import j66.free.tdlist.model.Task;
import j66.free.tdlist.model.TodoListManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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

    @FXML
    private void initialize(){
        EventHandler<MouseEvent> eventHandler = e -> {
            contextMenu.show(menuButton,e.getScreenX(),e.getScreenY());
        };
        menuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void initElementView(){
        System.out.println("ElementView - init : "+task.getName());
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

    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    private void rePlanTask(){

    }

    private void archiveTask(){

    }

    private void setDailyTask(){

    }

    @FXML
    private void setDone(){
        if (doOrNotCheckBox.isSelected()){
            task.setStatus(StatusTask.DONE);
        }else {
            TodoListManager.updateDoneTask(task);
        }
        main.getControllerHierarchyView().setSelectedElement(main.getRegister().get(task),task);
    }
}
