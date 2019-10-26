package j66.free.tdlist.tools;

import javafx.scene.control.Alert;

import java.time.LocalDateTime;

abstract public class Tool {

    public static String formatDate(LocalDateTime l){
        if(l == null)
            l=LocalDateTime.now();
        return l.getYear()+"-"+l.getMonthValue()+"-"+l.getDayOfMonth()+" "+l.getHour()+":"+l.getMinute()+":"+l.getSecond();
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
}
