package pl.edu.pk.student.editor;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.pk.student.editor.model.EngineMeasurement;
import pl.edu.pk.student.editor.model.FileModel;
import pl.edu.pk.student.editor.view.EngineOverviewController;
import pl.edu.pk.student.editor.view.EngineStatisticsController;
import pl.edu.pk.student.editor.view.MeasurementEditDialogController;
import pl.edu.pk.student.editor.view.RootLayoutController;

import java.io.*;
import java.util.prefs.Preferences;

import static javafx.stage.StageStyle.DECORATED;


/**
 * @author efiku Krzysztof Pazdur
 * @author entriu7 Andrzej Mycek
 */
public class Main extends Application {

    public static String VERSION = "v1.0";
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<EngineMeasurement> engineMsData = FXCollections.observableArrayList();
    /*
    *  File header  ; Name ; engid |  Eng Sett data.
    */
    private String motorHeader = "";
    /*
    * I need to set this here because it's a feature !
    */
    private EngineOverviewController engineOvController;

    /**
     * default Constructor
     */
    public Main() {
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
    /*==============================================================*/
    //                  GETTERS                                     
    /*==============================================================*/

    /**
     * Returns the data as an observable list of engineMsData.
     *
     * @return
     */
    public ObservableList<EngineMeasurement> getEngineMsData() {
        return engineMsData;
    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }


    /**
     * Returns the motorHeader
     *
     * @return the motorHeader
     */
    public String getMotorHeader() {
        return motorHeader;
    }

    /**
     * Set main motor header
     *
     * @param motorHeader the motorHeader to set
     */
    public void setMotorHeader(String motorHeader) {
        this.motorHeader = motorHeader;
    }

    /*==============================================================*/
    //                  SETTERS                                     
    /*==============================================================*/

    /**
     * Returns the Main file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getEngineFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setEngineFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("Engine Editor " + VERSION + " " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("Engine Editor " + VERSION);
        }
    }
    /*==============================================================*/
    //                  Layout                                     
    /*==============================================================*/

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.initStyle(DECORATED);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMain(this);

            primaryStage.show();
            primaryStage.setResizable(false);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * Shows the Engine Overview inside the root layout.
     */
    public void showEngineOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EngineOverview.fxml"));
            AnchorPane EngineOverview = (AnchorPane) loader.load();
            EngineOverview.getStylesheets().add(Main.class.getResource("view/style.css").toExternalForm());
            // Set engine overview into the center of root layout.
            rootLayout.setCenter(EngineOverview);

            // Give the controller access to the main app.
            engineOvController = loader.getController();
            engineOvController.setMain(this);
            
            

            File file = getEngineFilePath();
            if (file != null) {
                loadEngineDataFromFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the Engine Measurement Dialog
     *
     * @param engineMS
     * @return
     */
    public boolean showEngineMeasurementDialog(EngineMeasurement engineMS) {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MeasurementEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit data:");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            MeasurementEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEngineMeasurementObject(engineMS);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Shows Engine Engine Measurement Stats window
     */
    public void showEngineMSStats() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EngineStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the data from table into the controller.
            EngineStatisticsController controller = loader.getController();
            controller.setEngineData(engineMsData);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
    /*==============================================================*/
    //                  FILE OP                                     
    /*==============================================================*/

    /**
     * Generate Cool Alert types
     * Necessary JavaFX 8u40 on Windows I guess
     * Linux: Java SE -> JDK 1.8 (Default)
     *
     * @param type
     * @param title
     * @param header
     * @param content
     */
    public void generateExceptionDialog(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    /**
     * Clear all data : Like New File
     */
    public void clearData() {
        getEngineMsData().clear();
        setEngineFilePath(null);

        engineOvController.engineMark.clear();
        engineOvController.externalDiameter.clear();
        engineOvController.totalEngineWidth.clear();
        engineOvController.delayEngine.clear();
        engineOvController.fuelMass.clear();
        engineOvController.totalEngineMass.clear();
        engineOvController.manufacturer.clear();

    }

    /**
     * Save Motor to file with data from table
     * Throws IOException when somethigg goes wrong
     *
     * @param file
     * @throws IOException
     */
    public void saveEngineDataToFile(File file) throws IOException, Exception {
        try {
            this.motorHeader = engineOvController.engineMark.getText() + " " +
                    engineOvController.externalDiameter.getText() + " " +
                    engineOvController.totalEngineWidth.getText() + " " +
                    engineOvController.delayEngine.getText() + " " +
                    (Double.parseDouble(engineOvController.fuelMass.getText()) / 1000) + " " +
                    (Double.parseDouble(engineOvController.totalEngineMass.getText()) / 1000) + " " +
                    engineOvController.manufacturer.getText();
        } catch (Exception e) {
            generateExceptionDialog(AlertType.ERROR, "File save Error !", "Ooops! Empty fields in Motor settings?!\n", " Fix it!");
            throw new Exception("Whoops! Empty string ! Fix It !");
        }   
            /*
            // Print data to file step by step
            */
        try (PrintWriter out = new PrintWriter(file)) {
            out.println("; ENG-Editor by efiku and endriu7  https://github.com/efiku https://github.com/endriu7");
            out.println(";" + engineOvController.engineMark.getText());
            out.println(this.motorHeader);

            engineMsData.stream().forEach((engms) -> {
                out.println(engms.getTime() + " " + engms.getThrust());
            });

            out.println(";");
        } catch (IOException e) {
            generateExceptionDialog(AlertType.ERROR, "File save Error !", "Ooops! Error Detected while saving file!\n", file.getPath());
        }


        setEngineFilePath(file);


    }

    /**
     * Load Motor from file
     *
     * @param file
     */
    public void loadEngineDataFromFile(File file) {
        try {

            BufferedReader in = new BufferedReader(new FileReader(file));
            // temp header from file: 
            String[] header = new String[3];

            // temp line and temp buff
            String line;
            String[] buf;

            // Read firs line
            line = in.readLine();

            // Array starts form index[0] 
            int i = 0;


            FileModel motorDataModel = new FileModel();


            while (line != null) {

                // Read comment only 2 lines from head!
                while (line.length() == 0 || line.charAt(0) == ';' && i < 2) {
                    header[i] = line;

                    line = in.readLine();
                    i++;
                    if (line == null)
                        break;
                }

                // Parse header line, example:
                // B10 11 314 0 0.09 0.10 MSP_Aerospace
                if (line.charAt(0) != ';' || line.length() > 1) {
                    header[2] = line;
                }

                //Read the data
                // and convert data pionts to objects!
                for (
                        line = in.readLine();
                        (line != null) && (line.length() == 0 || line.charAt(0) != ';');
                        line = in.readLine()
                        ) {
                    // regex : all spaces
                    buf = line.split("\\s+");

                    if (buf.length == 3) {
                        EngineMeasurement obj = new EngineMeasurement(buf[1], buf[2]);
                        obj.setTime(buf[1]);
                        obj.setThrust(buf[2]);
                        motorDataModel.setMSd(obj);
                    } else {
                        EngineMeasurement obj = new EngineMeasurement(buf[0], buf[1]);
                        motorDataModel.setMSd(obj);

                    }


                } // endfor

            }

            engineMsData.clear();
            this.motorHeader = header[2];
            String[] splitH;
            splitH = this.motorHeader.split("\\s+");

            // fill Motor settings
            engineOvController.engineMark.setText(splitH[0]);
            engineOvController.externalDiameter.setText(splitH[1]);
            engineOvController.totalEngineWidth.setText(splitH[2]);
            engineOvController.delayEngine.setText(splitH[3]);
            engineOvController.fuelMass.setText(Double.toString(Double.parseDouble(splitH[4]) * 1000));
            engineOvController.totalEngineMass.setText(Double.toString(Double.parseDouble(splitH[5]) * 1000));
            engineOvController.manufacturer.setText(splitH[6]);

            // Add all objects to table 
            engineMsData.addAll(motorDataModel.getEngineMSdata());
            setEngineFilePath(file);

        } catch (IOException e) {
            setEngineFilePath(null);
            generateExceptionDialog(AlertType.ERROR, "Exception while loading file!", "Unable to load file! \n File format is ok?", file.getPath());
        }


    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("eDitor (ENG-Engine Editor) v0.1");

        // Create root Layout
        initRootLayout();

        // Put EngineOverview into root layout
        showEngineOverview();
    }
}
