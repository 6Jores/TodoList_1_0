package j66.free.tdlist.model;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.Serializable;
import java.time.LocalDate;

public class Task extends Element implements Serializable {

    private StatusTask status;
    private boolean daily;
    private PriorityTask priority;
    private LocalDate todoDate;
    private LocalDate doneDate;
    private TreeItem<Element> taskTreeItem;

    public Task(String name, String description) {
        super(name, description);
        this.typeElement = TypeElement.TASK;
        this.status = StatusTask.NO_PLAN;
        this.priority = PriorityTask.NORMAL;
        this.daily = false;
    }

    public Task(String name, String description, Element parent) {
        super(name, description, parent);
        this.typeElement = TypeElement.TASK;
        this.status = StatusTask.NO_PLAN;
        this.priority = PriorityTask.NORMAL;
        this.daily = false;
    }

    public StatusTask getStatus() {
        return status;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
        if (status==StatusTask.DONE){
            doneDate = LocalDate.now();
        }
        autoUpdate();
    }

    public LocalDate getTodoDate() {
        return todoDate;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    public void setTodoDate(LocalDate todoDate) {
        this.todoDate = todoDate;
        autoUpdate();
    }

    public boolean isDaily() {
        return daily;
    }

    public void setDaily(boolean daily) {
        this.daily = daily;
    }

    public PriorityTask getPriority() {
        return priority;
    }

    public void setPriority(PriorityTask priority) {
        this.priority = priority;
    }

    public TreeItem<Element> getTaskTreeItem() {
        return taskTreeItem;
    }

    public void setTaskTreeItem(TreeItem<Element> taskTreeItem) {
        this.taskTreeItem = taskTreeItem;
    }
}
