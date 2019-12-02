package j66.free.tdlist.tools;

/**
 * Author : Jores NOUBISSI
 * JavaDoc creation Date : 2019-11-05
 *
 * Class : Constant
 * Object : Group basic constants used in the project
 *
 * All constants are not there, some of them are directly create in the using class
 */
abstract public class Constant {
    //Dynamic constant for the application portability
    private static final String curDir = System.getProperty("user.dir") + "/";

    //Paths
    public static final String TODOLIST_PATH = curDir+"files/TodoLists/";
    private static final String IMAGE_PATH = curDir+"files/images/";

    //image paths
    public static final String APP_ICON_IMAGE = IMAGE_PATH+"icon.png";
    public static final String PROJECT_IMAGE = IMAGE_PATH+"project.png";
    public static final String SUB_PROJECT_IMAGE = IMAGE_PATH+"subProject.png";
    public static final String PLAN_IMAGE = IMAGE_PATH+"plan.png";
    public static final String NO_PLAN_IMAGE = IMAGE_PATH+"noPlan.png";
    public static final String LATE_IMAGE = IMAGE_PATH+"late.png";
    public static final String DONE_IMAGE = IMAGE_PATH+"done.png";
    public static final String CANCEL_IMAGE = IMAGE_PATH+"cancel.png";
    public static final String DAILY_IMAGE = IMAGE_PATH+"daily.png";
    public static final String BOTH_VIEW_IMAGE = IMAGE_PATH+"both.png";
    public static final String HIERARCHY_VIEW_IMAGE = IMAGE_PATH+"hierarchy.png";
    public static final String TODOLIST_VIEW_IMAGE = IMAGE_PATH+"todoListView.png";
    public static final String ADD_ELEMENT_IMAGE = IMAGE_PATH+"addElement.png";
    public static final String EDIT_ELEMENT_IMAGE = IMAGE_PATH+"editElement.png";
    public static final String REMOVE_ELEMENT_IMAGE = IMAGE_PATH+"removeElement.png";
    public static final String PLAN_TASK_IMAGE = IMAGE_PATH+"planTask.png";
    public static final String TODAY_IMAGE = IMAGE_PATH+"today.png";
    public static final String REFRESH_IMAGE = IMAGE_PATH+"refresh.png";
    public static final String AUTO_SAVE_IMAGE = IMAGE_PATH+"autoSave.png";

    //Texts
    public static final String APP_NAME = "TodoList";
    public static final String WELCOME_VIEW_TITLE = APP_NAME+" - WelcomeView";
    public static final String ACTION_EDIT_ELEMENT = "Edit";
    public static final String ACTION_NEW_ELEMENT = "New";
    public static final String NO_RESULT = "No result";

}
