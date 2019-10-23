package j66.free.tdlist.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Project extends Element implements Serializable {

    private ArrayList<Task> listTask = new ArrayList<>();
    private ArrayList<SubProject> listSubProject = new ArrayList<>();

    public Project(String name, String description) {
        super(name, description);
        this.typeElement = TypeElement.PROJECT;
    }

    public Project(String name, String description, Element parent) {
        super(name, description, parent);
        this.typeElement = TypeElement.PROJECT;
    }

    public ArrayList<Task> getListTask() {
        return listTask;
    }

    public ArrayList<SubProject> getListSubProject() {
        return listSubProject;
    }

    public void addTask(Task task){
        listTask.add(task);
        task.setParent(this);
        task.autoUpdate();
    }

    public void addSubProject(SubProject subProject){
        listSubProject.add(subProject);
        subProject.setParent(this);
        subProject.autoUpdate();
    }
}
