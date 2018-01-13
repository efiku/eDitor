package pl.edu.pk.student.editor.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.edu.pk.student.editor.Main;
import pl.edu.pk.student.editor.model.EngineMeasurement;

/**
 * FXML Controller class
 * @author efiku Krzysztof Pazdur
 * @author entriu7 Andrzej Mycek
 */
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
    private Main mainApp;
    private EngineMeasurement engineMeasurementObject;
    private boolean okClicked = false;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {

    }

    /**
     * @param dialogStage the dialogStage to set
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    /**
     * @param mainApp the mainApp to set
     */
    public void setMain(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * @param engineMeasurementObject the engineMeasurementObject to set
     */
    public void setEngineMeasurementObject(EngineMeasurement engineMeasurementObject) {
        this.engineMeasurementObject = engineMeasurementObject;

        this.engineTime.setText(this.engineMeasurementObject.getTime());
        this.engineThrust.setText(this.engineMeasurementObject.getThrust());
    }

    /**
     * @return the okClicked
     */
    public boolean isOkClicked() {
        return okClicked;
    }


    /**
     * Close window.
     */
    @FXML
    private void handleCancel() {
        this.dialogStage.close();
    }


    /**
     * Check datatype is vaild
     *
     * @return boolean
     */
    private boolean isDataVaild() {

        try {
            double tmpA = Double.parseDouble(this.engineTime.getText());
            double tmpB = Double.parseDouble(this.engineThrust.getText());

            return true;
        } catch (Exception e) {
            return false;
        }


    }

    /**
     * Callled when user click DONE
     */
    @FXML
    private void handleOkClicked() {
        if (isDataVaild()) {
            engineMeasurementObject.setTime(this.engineTime.getText());
            engineMeasurementObject.setThrust(this.engineThrust.getText());

            this.okClicked = true;
            this.dialogStage.close();

        } else {

            mainApp.generateExceptionDialog(AlertType.ERROR, "Data vaildator!", "Whooa! Incorrect data types!\n", "Numbers only!");

        }
    }

}
