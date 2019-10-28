package j66.free.tdlist.tools;

import javafx.scene.image.Image;

abstract public class Constant {
    private static final String curDir = System.getProperty("user.dir") + "/";

    //Paths
    public static final String TODOLIST_PATH = curDir+"files/TodoLists/";
    public static final String IMAGE_PATH = curDir+"files/images/";

    //Icons
    public static final String PROJECT_IMAGE = IMAGE_PATH+"project.png";
    public static final String SUB_PROJECT_IMAGE = IMAGE_PATH+"subProject.png";
    public static final String PLAN_IMAGE = IMAGE_PATH+"plan.png";
    public static final String NO_PLAN_IMAGE = IMAGE_PATH+"noPlan.png";
    public static final String LATE_IMAGE = IMAGE_PATH+"late.png";
    public static final String DONE_IMAGE = IMAGE_PATH+"done.png";
    public static final String CANCEL_IMAGE = IMAGE_PATH+"cancel.png";
    public static final String DAILY_IMAGE = IMAGE_PATH+"daily.png";

    //Texts
    public static final String APP_NAME = "TodoList";
    public static final String WELCOME_VIEW_TITLE = APP_NAME+" - WelcomeView";
    public static final String ACTION_EDIT_ELEMENT = "Edit";
    public static final String ACTION_NEW_ELEMENT = "New";
    public static final String NO_RESULT = "No result";

}
