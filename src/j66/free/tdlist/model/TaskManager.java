package j66.free.tdlist.model;

import java.time.LocalDate;

/**
 * Authot : Jores NOUBISSI
 * JavaDoc ceation Date : 2019-11-06
 *
 * Class : TaskManager
 * Object : Manage logic action on a task
 */
abstract public class TaskManager {
    /**
     * Update task status and date information
     * @param task : task to update
     */
    public static void updateTask(Task task){
        switch (task.getStatus()){
            case PLAN:
                if (task.isDaily()){
                    if (!task.getTodoDate().isEqual(LocalDate.now())){
                        task.setTodoDate(LocalDate.now());
                    }
                }else if (task.getTodoDate().isBefore(LocalDate.now())){
                    task.setStatus(StatusTask.LATE);
                }
                break;
            case DONE:
                if (task.isDaily()){
                    if (!task.getDoneDate().isEqual(LocalDate.now())){
                        task.setDoneDate(null);
                        task.setStatus(StatusTask.PLAN);
                        task.setTodoDate(LocalDate.now());
                    }
                }
                break;

        }
    }

    /**
     * Set Done or not and update relative information
     * @param task : Task to Set
     */
    public static void setTaskDone (Task task){
        if(task.getStatus()==StatusTask.DONE){
            task.setDoneDate(null);
            task.setStatus(StatusTask.PLAN);
            updateTask(task);
        }else {
            task.setStatus(StatusTask.DONE);
            task.setDoneDate(LocalDate.now());
        }
    }

    /**
     *
     * @param task : Task to get information
     * @return A string as Parent's information
     */
    public static String getParentInfoForTask(Task task){
        String str = "";

        switch (task.getParent().getTypeElement()){

            case TODOLIST:
                str += "Orphan task";
                break;
            case PROJECT:
                str += "Parent : Project : "+task.getParent().getName();
                break;
            case SUBPROJECT:
                SubProject subProject = (SubProject) task.getParent();
                Project project = (Project) subProject.getParent();
                str += "Parent : SubProject : "+subProject.getName();
                str += "- - -";
                str += "Parent of Parent : Project : "+project.getName();
                break;
        }

        return str;
    }

    /**
     *
     * @param date : The Date to check
     * @param task : The Task
     * @return a boolean
     */
    static boolean isTodoForDate(LocalDate date, Task task){
        boolean rtn = false;
        updateTask(task);
        if (task.getStatus()!=StatusTask.CANCEL){
            if (task.isDaily()){
                rtn = true;
            }else if(task.getStatus() == StatusTask.LATE || task.getStatus() == StatusTask.PLAN) {
                if (!task.getTodoDate().isAfter(date))
                    rtn = true;
            }else if(task.getStatus()==StatusTask.DONE && task.getDoneDate().equals(LocalDate.now())){
                rtn=true;
            }
        }
        return rtn;
    }
}
