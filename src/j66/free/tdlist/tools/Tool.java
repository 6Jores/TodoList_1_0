package j66.free.tdlist.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Author : Jores NOUBISSI
 * JavaDoc creation Date : 2019-11-05
 *
 * Class : Tools;
 * Object : Create diverse utilities for the application
 */
abstract public class Tool {

    /**
     *
     * @param l : A LocalDateTime convert to Y-M-D h:m
     * @return Date and hour as String
     */
    public static String formatDate(LocalDateTime l){
        if(l == null)
            l=LocalDateTime.now();
        return l.getYear()+"-"+l.getMonthValue()+"-"+l.getDayOfMonth()+" "+l.getHour()+":"+l.getMinute()+":"+l.getSecond();
    }

    /**
     *
     * @param l : A LocalDate convert to Y-M-D
     * @return Date as String
     */
    public static String formatDate(LocalDate l){
        if(l == null)
            l= LocalDate.now();
        return l.getYear()+"-"+l.getMonthValue()+"-"+l.getDayOfMonth();
    }

    /**
     *
     * @param title : The header of the Alert Box
     * @param message : The message in the Alert Box
     */
    public static void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     *
     * @param context : The header of the Alert Box
     * @param message : The message in the Alert Box
     * @return An Alert : to manage it and treat directly the return.
     */
    public static Alert getConfirmAlert(String context, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Please");
        alert.setHeaderText(message);
        alert.setContentText(context);

        return alert;
    }

    /**
     *
     * @param choice : List object in the DialogBox
     * @param tile : The tilte of the box
     * @param headerText : The main message in the box
     * @return a ChoiceDialog
     */
    public static ChoiceDialog<Object> getChoiceDialog(ArrayList<Object> choice, String tile, String headerText){
        ChoiceDialog<Object> choiceDialog = new ChoiceDialog<>();
        choiceDialog.getItems().addAll(choice);

        choiceDialog.setTitle(tile);
        choiceDialog.setHeaderText(headerText);

        return choiceDialog;
    }
}
