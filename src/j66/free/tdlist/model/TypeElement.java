package j66.free.tdlist.model;

public enum TypeElement {
    TODOLIST("TodoList"),PROJECT("Project"),SUBPROJECT("SubProject"),TASK("Task");

    private String nameElement;

    TypeElement(String nameElement) {
        this.nameElement = nameElement;
    }

    public String getNameElement() {
        return nameElement;
    }
}
