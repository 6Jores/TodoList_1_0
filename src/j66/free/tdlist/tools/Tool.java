package j66.free.tdlist.tools;

import j66.free.tdlist.model.TypeElement;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

abstract public class Tool {

    public static String formatDate(LocalDateTime l){
        if(l == null)
            l=LocalDateTime.now();
        return l.getYear()+"-"+l.getMonthValue()+"-"+l.getDayOfMonth()+" "+l.getHour()+":"+l.getMinute()+":"+l.getSecond();
    }

    public static String formatDate(LocalDate l){
        if(l == null)
            l= LocalDate.now();
        return l.getYear()+"-"+l.getMonthValue()+"-"+l.getDayOfMonth();
    }

    public static void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static Alert getConfirmAlert(String context, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Please");
        alert.setHeaderText(message);
        alert.setContentText(context);

        return alert;
    }

    public static ChoiceDialog<TypeElement> getChoiceDialog(ArrayList<TypeElement> choice, String tile, String headerText){
        ChoiceDialog<TypeElement> choiceDialog = new ChoiceDialog<>();
        choiceDialog.getItems().addAll(choice);

        choiceDialog.setTitle(tile);
        choiceDialog.setHeaderText(headerText);

        return choiceDialog;
    }
}
