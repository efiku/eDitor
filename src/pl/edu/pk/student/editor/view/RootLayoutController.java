package pl.edu.pk.student.editor.view;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import pl.edu.pk.student.editor.Main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 * @author efiku Krzysztof Pazdur
 * @author entriu7 Andrzej Mycek
 */
public class RootLayoutController {

    // Reference to the main application
    private Main main;


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param main main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * This method is called when user clicks Statistics option
     */
    @FXML
    private void handleShowEngineMsStats() {
        main.showEngineMSStats();
    }

    /**
     * This method is called when user clicks Close option
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }


    /**
     * This method is called when user clicks New option
     */
    @FXML
    private void handleNew() {
        main.clearData();

    }


    /**
     * This method is called when user clicks Open option
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "ENG Data Files (*.eng)", "*.eng");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

        if (file != null) {
            main.loadEngineDataFromFile(file);
        }
    }


    /**
     * This method is called when user clicks Save option
     */
    @FXML
    private void handleSave() throws Exception {
        File dataFile = main.getEngineFilePath();
        if (dataFile != null) {
            try {
                main.saveEngineDataToFile(dataFile);
            } catch (IOException ex) {
                Logger.getLogger(RootLayoutController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() throws Exception {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "ENG Data Files (*.eng)", "*.eng");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

        if (file != null) {
            try {
                // Make sure it has the correct extension
                if (!file.getPath().endsWith(".eng")) {
                    file = new File(file.getPath() + ".eng");
                }
                main.saveEngineDataToFile(file);
            } catch (IOException ex) {
                Logger.getLogger(RootLayoutController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}