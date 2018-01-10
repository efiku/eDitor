package pl.edu.pk.student.editor.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class MeasurementEditDialogController {

    @FXML
    private TextField engineTime;

    @FXML
    private TextField engineThrust;

    @FXML
    private Button butOK;

    @FXML
    private Button butCancel;


    private Stage dialogStage;


    public void setEngineMeasurementObject() {
    }

    public boolean isOkClicked() {
        return false;
    }


    @FXML
    private void handleCancel() {

    }


    private boolean isDataVaild() {
        return false;
    }


    @FXML
    private void handleOkClicked() {

    }

}
