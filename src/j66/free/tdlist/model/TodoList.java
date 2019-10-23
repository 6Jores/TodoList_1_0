package j66.free.tdlist.model;

import java.io.Serializable;
import java.util.ArrayList;

class TodoList extends Element implements Serializable {

    private ArrayList<Project> listProject = new ArrayList<>();
    private ArrayList<Task> listTask = new ArrayList<>();

    public TodoList(String name, String description) {
        super(name, description);
        this.typeElement = TypeElement.TODOLIST;
    }

    public ArrayList<Project> getListProject() {
        return listProject;
    }

    public ArrayList<Task> getListTask() {
        return listTask;
    }

    public void addTask(Task task){
        listTask.add(task);
        task.setParent(this);
    }

    public void addProject(Project project){
        listProject.add(project);
        project.setParent(this);
    }
}
