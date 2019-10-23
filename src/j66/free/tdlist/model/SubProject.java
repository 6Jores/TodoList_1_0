package j66.free.tdlist.model;

import java.io.Serializable;
import java.util.ArrayList;


public class SubProject extends Element implements Serializable {
    private ArrayList<Task> listTask = new ArrayList<>();

    public SubProject(String name, String description) {
        super(name, description);
        this.typeElement = TypeElement.SUBPROJECT;
    }

    public SubProject(String name, String description, Element parent) {
        super(name, description, parent);
        this.typeElement = TypeElement.SUBPROJECT;
    }

    public ArrayList<Task> getListTask() {
        return listTask;
    }

    public void addTask(Task task){
        listTask.add(task);
        task.setParent(this);
    }
}
