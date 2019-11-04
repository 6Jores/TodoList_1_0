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
import java.util.ArrayList;
import java.util.Optional;

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
    private Image addElementImage;
    private Image removeElementImage;
    private Image editElementImage;
    private Image planTaskImage;
    private ContextMenu contextMenu;

    private TreeItem<Element> selectedTreeView;
    private Element selectedElement;
    private Element newElement;
    private boolean thereIsASelected;

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

        initializeImages();

        treeView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue)
                -> initializeDescription(newValue.getValue())));

        EventHandler<MouseEvent> eventHandler = e -> {
            setSelectedElement();
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
        initializeTreeView(TodoListManager.getTodoList());
    }

    private void initializeImages(){
        try {
            projectImage = new Image(new FileInputStream(PROJECT_IMAGE),20,20,false,false);
            subProjectImage = new Image(new FileInputStream( SUB_PROJECT_IMAGE),20,20,false,false);
            planImage = new Image(new FileInputStream(PLAN_IMAGE),20,20,false,false);
            noPlanImage = new Image(new FileInputStream(NO_PLAN_IMAGE),20,20,false,false);
            lateImage = new Image(new FileInputStream(LATE_IMAGE),20,20,false,false);
            doneImage = new Image(new FileInputStream(DONE_IMAGE),20,20,false,false);
            cancelImage = new Image(new FileInputStream(CANCEL_IMAGE),20,20,false,false);
            dailyImage = new Image(new FileInputStream(DAILY_IMAGE),20,20,false,false);

            addElementImage = new Image(new FileInputStream(ADD_ELEMENT_IMAGE),20,20,false,false);
            editElementImage = new Image(new FileInputStream(EDIT_ELEMENT_IMAGE),20,20,false,false);
            removeElementImage = new Image(new FileInputStream(REMOVE_ELEMENT_IMAGE),20,20,false,false);
            planTaskImage =  new Image(new FileInputStream(PLAN_TASK_IMAGE),20,20,false,false);
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
                    //task
                    TodoListManager.updateTask(task);
                    TreeItem<Element> itemTask = itemViewFactory(task);
                    rootSubProject.getChildren().add(itemTask);
                }
            }
            //
            for (Task task : project.getListTask()){
                //task
                TodoListManager.updateTask(task);
                TreeItem<Element> itemTask = itemViewFactory(task);
                rootProject.getChildren().add(itemTask);
            }
        }
        for (Task task : todoList.getListTask()){
            //task
            TodoListManager.updateTask(task);
            TreeItem<Element> itemTask = itemViewFactory(task);
            rootElement.getChildren().add(itemTask);
        }
        treeView.setRoot(rootElement);
    }

    private void showContextMenu(MouseEvent event){
        contextMenu = new ContextMenu();

        MenuItem item0 = new MenuItem("Add Element");
        item0.setGraphic(new ImageView(addElementImage));
        item0.setOnAction(actionEvent -> addElement());

        if (thereIsASelected){

            if (selectedElement.getTypeElement() == TypeElement.TASK){
                String text="Plan Task";
                if (((Task) selectedElement).getStatus()==StatusTask.PLAN)
                    text = "RePlan Task";
                MenuItem item1 = new MenuItem(text);
                item1.setGraphic(new ImageView(planTaskImage));
                contextMenu.getItems().add(item1);
                item1.setOnAction(actionEvent -> planTask());

                if (((Task) selectedElement).getStatus() != StatusTask.CANCEL){
                    MenuItem item11 = new MenuItem("Cancel Task");
                    contextMenu.getItems().add(item11);
                    item11.setOnAction(actionEvent -> cancelTask());
                }
                CheckMenuItem item12 = new CheckMenuItem("Daily Task");
                item12.setSelected(((Task) selectedElement).isDaily());
                item12.setOnAction(actionEvent -> setTaskDaily());
                contextMenu.getItems().add(item12);

                contextMenu.getItems().add(new SeparatorMenuItem());
            }else{
                contextMenu.getItems().add(item0);
            }


            if (selectedElement.getTypeElement() != TypeElement.TODOLIST){
                MenuItem item2 = new MenuItem("Edit Element");
                item2.setGraphic(new ImageView(editElementImage));
                contextMenu.getItems().add(item2);
                item2.setOnAction(actionEvent -> editElement());
                MenuItem item3 = new MenuItem("Remove Element");
                item3.setGraphic(new ImageView(removeElementImage));
                contextMenu.getItems().add(item3);
                item3.setOnAction(actionEvent -> removeElement());
            }

            Menu info = new Menu("Info");

            switch (selectedElement.getTypeElement()){
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

                    Menu menuPriority = new Menu("Priority");
                    MenuItem itemPriority = new MenuItem(((Task)selectedElement).getPriority().toString());
                    menuPriority.getItems().add(itemPriority);
                    info.getItems().add(menuPriority);

                    Menu menuPlanDate = new Menu("Plan Date");
                    MenuItem itemPlanDate = new MenuItem(Tool.formatDate(((Task) selectedElement).getTodoDate()));
                    menuPlanDate.getItems().add(itemPlanDate);

                    switch (((Task) selectedElement).getStatus()){
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
                            MenuItem itemDoneDate = new MenuItem(Tool.formatDate(((Task) selectedElement).getDoneDate()));
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
            if (selectedElement.getTypeElement() != TypeElement.TODOLIST){
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
                item = new TreeItem<>(element);
                break;
            case PROJECT:
                item = new TreeItem<>(element);
                item.setGraphic(new ImageView(projectImage));
                break;
            case SUBPROJECT:
                item = new TreeItem<>(element);
                item.setGraphic(new ImageView(subProjectImage));
                break;
            case TASK:
                item = new TreeItem<>(element);
                item.setGraphic(getImageView((Task)element));
                main.updateRegister((Task)element,item);
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

    AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    void cancelTask(){
        Task task = (Task) selectedElement;
        task.setStatus(StatusTask.CANCEL);
        selectedTreeView.setGraphic(new ImageView(cancelImage));
    }

    void addElement(){
        newElement = null;
        ArrayList<TypeElement> choice = new ArrayList<>();
        Optional<TypeElement> option;
        switch (selectedElement.getTypeElement()){
            case TODOLIST:
                choice.add(TypeElement.PROJECT);
                choice.add(TypeElement.TASK);
                break;
            case PROJECT:
                choice.add(TypeElement.SUBPROJECT);
                choice.add(TypeElement.TASK);
                break;
        }
        if (choice.size()>0){
            ChoiceDialog<TypeElement> choiceDialog = Tool.getChoiceDialog(choice,"Select child type.",
                    "Select a type");
            choiceDialog.setSelectedItem(TypeElement.TASK);
            option = choiceDialog.showAndWait();
            option.ifPresent(this::setNewElement);
        }else{
            setNewElement(TypeElement.TASK);
        }
        if(newElement != null){
            if (newElement.getTypeElement()==TypeElement.TASK){
                main.showEditTask((Task)newElement, ACTION_NEW_ELEMENT);
            }else {
                main.showEditElement(newElement, ACTION_NEW_ELEMENT);
            }
        }

    }

    void editElement(){
        if (selectedElement.getTypeElement()==TypeElement.TASK){
            main.showEditTask((Task)selectedElement,ACTION_EDIT_ELEMENT);
            selectedTreeView.setGraphic(getImageView((Task)selectedElement));
            main.getControllerTodoListView().refreshList();
        }else{
            main.showEditElement(selectedElement,ACTION_EDIT_ELEMENT);
        }
    }

    void planTask(){
        editElement();
    }

    void removeElement(){
        Alert alert = Tool.getConfirmAlert("Confirm suppression",
                selectedElement.getTypeElement()+" : "+selectedElement.getName());
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            TreeItem<Element> parentSelectedElement = selectedTreeView.getParent();
            TodoListManager.removeElement(selectedElement);
            parentSelectedElement.getChildren().remove(selectedTreeView);
        }
    }

    void setTaskDaily(){
        Task task = (Task)selectedElement;
        task.setDaily(!task.isDaily());
        TodoListManager.updateDoneTask(task);
        TodoListManager.setSave(false);
        treeView.getSelectionModel().getSelectedItem().setGraphic(getImageView(task));
    }

    private ImageView getImageView(Task task){
        ImageView imageView=null;
        if (task.isDaily() && task.getStatus()!=StatusTask.CANCEL){
            imageView = new ImageView(dailyImage);
        }else {
            switch (task.getStatus()) {
                case NO_PLAN:
                    imageView = new ImageView(noPlanImage);
                    break;
                case PLAN:
                    imageView = new ImageView(planImage);
                    break;
                case DONE:
                    imageView = new ImageView(doneImage);
                    break;
                case LATE:
                    imageView = new ImageView(lateImage);
                    break;
                case CANCEL:
                    imageView = new ImageView(cancelImage);
                    break;
            }
        }
        return imageView;
    }

    private void setSelectedElement(){
        selectedTreeView = treeView.getSelectionModel().getSelectedItem();
        if (selectedTreeView != null){
            selectedElement = selectedTreeView.getValue();
            thereIsASelected = true;
            main.getControllerMainContentView().updateToolBarElement(selectedElement);
        }else {
            selectedElement = null;
            thereIsASelected = false;
        }
    }

    private void setNewElement(TypeElement typeElement) {
        newElement = TodoListManager.getNewElement(typeElement,selectedElement);
    }

    void updateAdding(){
        selectedTreeView.getChildren().add(itemViewFactory(newElement));
    }

    void setSelectedElement(TreeItem<Element> item,Task task){
        int row = treeView.getRow(item);
        if (item != null){
            selectedTreeView = item;
            selectedElement = task;
        }
        if (row > -1){
            treeView.getSelectionModel().select(row);
        }
        item.setGraphic(getImageView(task));
    }
}
