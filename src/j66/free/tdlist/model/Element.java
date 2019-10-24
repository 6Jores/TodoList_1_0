package j66.free.tdlist.model;


import java.io.Serializable;
import java.time.LocalDateTime;

abstract public class Element implements Serializable {

    private String name;
    private String description;
    private Element parent;
    private LocalDateTime creationDate;
    private LocalDateTime editionDate;

    public void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    TypeElement typeElement;

    Element(){
        initDates(this);
    }


    Element(String name, String description) {
        this.name = name;
        this.description = description;
        initDates(this);
    }



    Element(String name, String description, Element parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;
        initDates(this);
    }


    @Override
    public String toString(){
        return this.name;
    }

    private void initDates(Element element){
        element.creationDate = LocalDateTime.now();
        element.editionDate = element.creationDate;
    }

    void autoUpdate(){
        this.editionDate = LocalDateTime.now();
        if (parent !=null)
            parent.autoUpdate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        autoUpdate();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        autoUpdate();
    }

    public Element getParent() {
        return parent;
    }

    void setParent(Element parent) {
        this.parent = parent;
        autoUpdate();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getEditionDate() {
        return editionDate;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }
}
