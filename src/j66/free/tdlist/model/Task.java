package j66.free.tdlist.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Task extends Element implements Serializable {

    private StatusTask status;
    private boolean daily;
    private PriorityTask priority;
    private LocalDateTime todoDate;
    private LocalDateTime doneDate;


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
            doneDate = LocalDateTime.now();
        }
        autoUpdate();
    }

    public LocalDateTime getTodoDate() {
        return todoDate;
    }

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public void setTodoDate(LocalDateTime todoDate) {
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
}
