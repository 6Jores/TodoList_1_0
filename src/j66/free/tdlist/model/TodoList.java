package j66.free.tdlist.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    void addTask(Task task){
        listTask.add(task);
        task.setParent(this);
    }

    void addProject(Project project){
        listProject.add(project);
        project.setParent(this);
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    String getNameFile() {
        return nameFile;
    }

    TodoList cloneMy(){
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

    @Override
    public void autoUpdate(){
        this.editionDate = LocalDateTime.now();
        TodoListManager.setSave(false);
    }

    @Override
    public String toString(){
        String rtn =this.getName();
        if (isArchived()){
            rtn+=" - Archived";
        }
        return rtn;
    }
}
