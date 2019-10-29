package j66.free.tdlist.view;

import j66.free.tdlist.Main;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TodoListView {
    private Main main;
    private AnchorPane anchorPane;

    @FXML
    private void initialize(){

    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}
