package pl.edu.pk.student.editor.view;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pl.edu.pk.student.editor.Main;
import pl.edu.pk.student.editor.model.EngineMeasurement;

/**
 * FXML Controller class
 *
 * @author Krzysztof Pazdur
 */
public class EngineOverviewController implements Initializable {

    /*
    Table and columns
    */
    @FXML
    private TableView<EngineMeasurement> enigneTable;
    @FXML
    private TableColumn<EngineMeasurement, String> timeColumn;
    @FXML
    private TableColumn<EngineMeasurement, String> thrustColumn;

    /*
    TextFields 
    */
    @FXML
    public TextField engineMark;
    @FXML
    public TextField manufacturer;
    @FXML
    public TextField externalDiameter;
    @FXML
    public TextField totalEngineWidth;
    @FXML
    public TextField delayEngine;
    @FXML
    public TextField fuelMass;
    @FXML
    public TextField totalEngineMass;


    /*
     Buttons
     */
    @FXML
    private Button buttonOK;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;


    // Reference to the main application.
    private Main mainApp;


    /*
       Default Constructor 
    */
    public EngineOverviewController() {

    }


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        timeColumn.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());
        thrustColumn.setCellValueFactory(cellData -> cellData.getValue().getThrustProperty());

        // Listen for selection changes 
        EngineListener();
        enigneTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> EngineListener());


    }

    /**
     * reference back to itself.
     *
     * @param mainApp
     */
    public void setMain(Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        enigneTable.setItems(mainApp.getEngineMsData());
    }


    /**
     * Called when user clicks on the delete button.
     */
    @FXML
    private void handleDeleteEngineMeasurement() {
        int selectedIndex = enigneTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            enigneTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            mainApp.generateExceptionDialog(AlertType.ERROR, "No Selection", "No Measurement Selected", "Please select a Measurement Data in the table.");
        }

    }

    /**
     * Called when user  move mouse or press something on keyborard
     */
    @FXML
    private void EngineListener() {
        int selectedIndex = enigneTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            buttonDelete.setDisable(true);
            buttonEdit.setDisable(true);
        } else {
            buttonDelete.setDisable(false);
            buttonEdit.setDisable(false);
        }
    }

    /**
     * Called when user clicks on add button
     */
    @FXML
    private void hadnleAddEngineMeasurement() {
        EngineMeasurement tempEngineMS = new EngineMeasurement();
        boolean okClicled = mainApp.showEngineMeasurementDialog(tempEngineMS);

        if (okClicled) {
            mainApp.getEngineMsData().add(tempEngineMS);
        }
    }

    /**
     * Called when user click on edit button.
     */
    @FXML
    private void handleEditEngineMeasurement() {
        EngineMeasurement selectedEMS = enigneTable.getSelectionModel().getSelectedItem();

        if (selectedEMS != null) {
            boolean okClicked = mainApp.showEngineMeasurementDialog(selectedEMS);

        } else {
            mainApp.generateExceptionDialog(AlertType.ERROR, "No Selection", "No Measurement Selected", "Please select a Measurement Data in the table.");
        }
    }

}
