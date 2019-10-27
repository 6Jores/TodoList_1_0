package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import j66.free.tdlist.model.*;

import j66.free.tdlist.tools.Tool;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static j66.free.tdlist.tools.Constant.*;

public class HierarchyView {

    private Main main;
    private AnchorPane anchorPane;
    private Image projectImage;
    private Image subProjectImage;
    private Image planImage;
    private Image noPlanImage;
    private Image lateImage;
    private Image doneImage;
    private Image cancelImage;
    private Image dailyImage;
    private ContextMenu contextMenu;

    @FXML
    Label elementName;
    @FXML
    Label elementEditionDate;
    @FXML
    Label elementDescription;
    @FXML
    TreeView<Element> treeView;

    @FXML
    private void initialize(){
        treeView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> initializeDescription(newValue.getValue())));

        EventHandler<MouseEvent> eventHandler = e -> {
            if (contextMenu!=null && contextMenu.isShowing())
                contextMenu.hide();
            if (e.getButton() == MouseButton.SECONDARY){
                showContextMenu(e);
            }
        };

        treeView.addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler);
    }

    public void setMain(Main main) {
        this.main = main;
        initializeDescription(null);
        initializeImages();
        initializeTreeView(TodoListManager.getTodoList());
    }

    private void initializeImages(){
        try{
            projectImage = new Image(new FileInputStream(PROJECT_IMAGE),20,20,false,false);
            subProjectImage = new Image(new FileInputStream( SUB_PROJECT_IMAGE),20,20,false,false);
            planImage = new Image(new FileInputStream(PLAN_IMAGE),20,20,false,false);
            noPlanImage = new Image(new FileInputStream(NO_PLAN_IMAGE),20,20,false,false);
            lateImage = new Image(new FileInputStream(LATE_IMAGE),20,20,false,false);
            doneImage = new Image(new FileInputStream(DONE_IMAGE),20,20,false,false);
            cancelImage = new Image(new FileInputStream(CANCEL_IMAGE),20,20,false,false);
            dailyImage = new Image(new FileInputStream(DAILY_IMAGE),20,20,false,false);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeTreeView(TodoList todoList){

        TreeItem<Element> rootElement = itemViewFactory(todoList);
        treeView.setTooltip(new Tooltip("Right click to see action or information"));

        for (Project project:todoList.getListProject()){
            //
            TreeItem<Element> rootProject = itemViewFactory(project);
            rootElement.getChildren().add(rootProject);
            for (SubProject subProject : project.getListSubProject()){
                //
                TreeItem<Element> rootSubProject = itemViewFactory(subProject);
                rootProject.getChildren().add(rootSubProject);
                for(Task task:subProject.getListTask()){
                    //
                    TreeItem<Element> itemTask = itemViewFactory(task);
                    rootSubProject.getChildren().add(itemTask);
                }
            }

            //
            for (Task task : project.getListTask()){
                //
                TreeItem<Element> itemTask = itemViewFactory(task);
                rootProject.getChildren().add(itemTask);
            }

        }
        for (Task task : todoList.getListTask()){
            //
            TreeItem<Element> itemTask = itemViewFactory(task);
            rootElement.getChildren().add(itemTask);
        }

        treeView.setRoot(rootElement);

    }

    private void showContextMenu(MouseEvent event){
        contextMenu = new ContextMenu();
        boolean thereIsASelected;
        thereIsASelected = treeView.getSelectionModel().getSelectedItem() != null;
        MenuItem item0 = new MenuItem("Add Element");
        item0.setOnAction(actionEvent -> addElement());

        if (thereIsASelected){
            Element element = treeView.getSelectionModel().getSelectedItem().getValue();
            if (element.getTypeElement() == TypeElement.TASK){
                MenuItem item1 = new MenuItem("Plan Task");
                contextMenu.getItems().add(item1);
                item1.setOnAction(actionEvent -> planTask());
                if (((Task)element).getStatus() != StatusTask.CANCEL){
                    MenuItem item11 = new MenuItem("Cancel Task");
                    contextMenu.getItems().add(item11);
                    item11.setOnAction(actionEvent -> cancelTask());
                }
                CheckMenuItem item12 = new CheckMenuItem("Daily Task");
                item12.setSelected(((Task)element).isDaily());
                item12.setOnAction(actionEvent -> setTaskDaily((Task)element));
                contextMenu.getItems().add(item12);

                contextMenu.getItems().add(new SeparatorMenuItem());
            }
            contextMenu.getItems().add(item0);

            if (treeView.getSelectionModel().getSelectedItem().getValue().getTypeElement() != TypeElement.TODOLIST){
                MenuItem item2 = new MenuItem("Edit Element");
                contextMenu.getItems().add(item2);
                item2.setOnAction(actionEvent -> editElement());
                MenuItem item3 = new MenuItem("Remove Element");
                contextMenu.getItems().add(item3);
                item3.setOnAction(actionEvent -> removeElement());
            }

            Menu info = new Menu("Info");

            switch (element.getTypeElement()){
                case TODOLIST:
                    break;
                case PROJECT:
                    MenuItem itemProject = new MenuItem("Project");
                    itemProject.setGraphic(new ImageView(projectImage));
                    info.getItems().add(itemProject);
                    break;
                case SUBPROJECT:
                    MenuItem itemSubProject = new MenuItem("SubProject");
                    itemSubProject.setGraphic(new ImageView(subProjectImage));
                    info.getItems().add(itemSubProject);
                    break;
                case TASK:

                    Menu menuPlanDate = new Menu("Plan Date");
                    MenuItem itemPlanDate = new MenuItem(Tool.formatDate(((Task)element).getTodoDate()));
                    menuPlanDate.getItems().add(itemPlanDate);

                    switch (((Task)element).getStatus()){
                        case NO_PLAN:
                            MenuItem itemNoPlanTask = new MenuItem("Task not Plan");
                            itemNoPlanTask.setGraphic(new ImageView(noPlanImage));
                            info.getItems().add(itemNoPlanTask);
                            break;
                        case PLAN:
                            MenuItem itemPlanTask = new MenuItem("Task Plan");
                            itemPlanTask.setGraphic(new ImageView(planImage));

                            info.getItems().add(itemPlanTask);
                            info.getItems().add(menuPlanDate);
                            break;
                        case DONE:
                            MenuItem itemDoneTask = new MenuItem("Task Done");
                            itemDoneTask.setGraphic(new ImageView(doneImage));

                            Menu menuDoneDate = new Menu("Done Date");
                            MenuItem itemDoneDate = new MenuItem(Tool.formatDate(((Task)element).getDoneDate()));
                            menuDoneDate.getItems().add(itemDoneDate);

                            info.getItems().add(itemDoneTask);
                            info.getItems().add(menuDoneDate);
                            break;
                        case LATE:
                            MenuItem itemLateTask = new MenuItem("Task Late");
                            itemLateTask.setGraphic(new ImageView(lateImage));

                            info.getItems().add(itemLateTask);
                            info.getItems().add(menuPlanDate);
                            break;
                        case CANCEL:
                            MenuItem itemCancelTask = new MenuItem("Task Cancel");
                            itemCancelTask.setGraphic(new ImageView(cancelImage));
                            info.getItems().add(itemCancelTask);
                            break;
                    }
                    break;
            }
            if (element.getTypeElement() != TypeElement.TODOLIST){
                contextMenu.getItems().add(new SeparatorMenuItem());
                contextMenu.getItems().add(info);
            }

        }else {
            contextMenu.getItems().add(item0);
        }
        treeView.setOnContextMenuRequested(contextMenuEvent -> contextMenu.show(treeView, event.getScreenX(),event.getScreenY()));
    }

    private TreeItem<Element> itemViewFactory(Element element){
        TreeItem<Element> item = new TreeItem<>();
        switch (element.getTypeElement()){
            case TODOLIST:
                item = new TreeItem<>((TodoList)element);
                break;
            case PROJECT:
                item = new TreeItem<>((Project)element);
                item.setGraphic(new ImageView(projectImage));
                break;
            case SUBPROJECT:
                item = new TreeItem<>((SubProject)element);
                item.setGraphic(new ImageView(subProjectImage));
                break;
            case TASK:
                item = new TreeItem<>((Task)element);
                switch (((Task)element).getStatus()){
                    case NO_PLAN:
                        item.setGraphic(new ImageView(noPlanImage));
                        break;
                    case PLAN:
                        item.setGraphic(new ImageView(planImage));
                        break;
                    case DONE:
                        item.setGraphic(new ImageView(doneImage));
                        break;
                    case LATE:
                        item.setGraphic(new ImageView(lateImage));
                        break;
                    case CANCEL:
                        item.setGraphic(new ImageView(cancelImage));
                        break;
                }
                break;
        }

        return item;
    }

    private void initializeDescription(Element element){
        if (element != null){
            elementName.setText(element.getName());
            toolTipIfNecessary(elementName,18);
            elementEditionDate.setText(Tool.formatDate(element.getEditionDate()));
            toolTipIfNecessary(elementEditionDate,18);
            elementDescription.setText(element.getDescription());
            toolTipIfNecessary(elementDescription,45);
        }else {
            elementName.setText("");
            elementEditionDate.setText("");
            elementDescription.setText("");
        }
    }

    private void toolTipIfNecessary(Label label, int maxLength){
        if (label != null){
            if (label.getText().length()>maxLength){
                label.setTooltip(new Tooltip(label.getText()));
            }else {
                label.setTooltip(null);
            }
        }
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    private void planTask(){
        System.out.println("Hierarchy View - PlanTask");
    }

    private void cancelTask(){
        System.out.println("Hierarchy View - cancelTask");
        TreeItem<Element> element = treeView.getSelectionModel().getSelectedItem();
        Task task = (Task)element.getValue();
        task.setStatus(StatusTask.CANCEL);
        element.setGraphic(new ImageView(cancelImage));
    }

    private void addElement(){
        System.out.println("Hierarchy View - addElement");
    }

    private void editElement(){
        System.out.println("Hierarchy View - editElement");
    }

    private void removeElement(){
        System.out.println("Hierarchy View - removeElement");
    }

    private void setTaskDaily(Task task){

    }
}
