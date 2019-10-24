package j66.free.tdlist.model;

import javax.swing.plaf.TabbedPaneUI;
import java.io.Serializable;
import java.util.ArrayList;

public class TodoList extends Element implements Serializable {

    private ArrayList<Project> listProject = new ArrayList<>();
    private ArrayList<Task> listTask = new ArrayList<>();
    private boolean archived = false;
    private String nameFile;

    private TodoList(){

    }

    public TodoList(String nameFile, String name, String description) {
        super(name, description);
        this.nameFile = nameFile;
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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public TodoList cloneMy(){
        TodoList source = this;
        TodoList clone = new TodoList();

        clone.nameFile = source.nameFile + " - copy";
        clone.setName(source.getName() + " - copy");
        clone.setDescription(source.getDescription());
        clone.setTypeElement(clone.getTypeElement());
        clone.setParent(null);

        clone.listProject = source.listProject;
        clone.listTask = source.listTask;
        clone.archived = source.archived;

        return clone;
    }
}
