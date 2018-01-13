package pl.edu.pk.student.editor.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import pl.edu.pk.student.editor.model.EngineMeasurement;

/**
 * FXML Controller class
 *
 * @author efik
 */
public class EngineStatisticsController implements Initializable {


    @FXML
    private LineChart<Double, Double> graph;


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Set data on LineChart
     *
     * @param engineMsData
     */
    public void setEngineData(List<EngineMeasurement> engineMsData) {


        ObservableList<XYChart.Series<Double, Double>> lineChartData =
                FXCollections.observableArrayList();

        LineChart.Series<Double, Double> series1 = new LineChart.Series<>();

        series1.setName("Data Pionts");
        engineMsData.stream().forEach((_item) -> {
            series1.getData().add(
                    new XYChart.Data<>(
                            Double.parseDouble(_item.getTime()),
                            Double.parseDouble(_item.getThrust())
                    ));
        });


        lineChartData.add(series1);

        graph.setData(lineChartData);
        graph.createSymbolsProperty();
        graph.setSnapToPixel(true);

    }
}
