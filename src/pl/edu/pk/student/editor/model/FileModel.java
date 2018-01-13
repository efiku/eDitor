package pl.edu.pk.student.editor.model;

import java.util.ArrayList;
import java.util.List;
/**
 * FXML Controller class
 * @author efiku Krzysztof Pazdur
 * @author entriu7 Andrzej Mycek
 */
public class FileModel {

    private List<EngineMeasurement> engineMSdata = new ArrayList<>();

    /**
     * @return the engineMSdata
     */
    public List<EngineMeasurement> getEngineMSdata() {
        return engineMSdata;
    }

    /**
     * @param engineMSdata the engineMSdata to set
     */
    public void setEngineMSdata(List<EngineMeasurement> engineMSdata) {
        this.engineMSdata = engineMSdata;
    }

    public void setMSd(EngineMeasurement x) {
        this.engineMSdata.add(x);
    }


}
