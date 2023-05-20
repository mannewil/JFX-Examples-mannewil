/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package zapocet_demchenko;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author st67017
 */
public class FXMLController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClear;
    @FXML
    private TextField tfTemperature;
    @FXML
    private TextField tfTime;
    @FXML
    private TextArea textArea;
    @FXML
    private LineChart<Number, Number> lineChart;

    XYChart.Series<Number, Number> series;
    ArrayList<Mereni> list;

    //float counter = 0;
    float values = 0;
    float prumer = 0;
    float min = 999;
    float max = 0;
    int maxTime = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = new ArrayList<>();
        series = new XYChart.Series();

        lineChart.getXAxis().setLabel("Cas [s]");
        lineChart.getXAxis().setAutoRanging(true);
        lineChart.getYAxis().setLabel("Teplota [°C]");
        lineChart.getYAxis().setAutoRanging(true);
        lineChart.setAnimated(false);
        lineChart.setLegendVisible(false);

        lineChart.getData().add(series);

        textArea.setEditable(false);
    }

    @FXML
    private void onBtnAdd(ActionEvent event) {
        if ((!tfTemperature.getText().isEmpty() && !tfTime.getText().isEmpty()) && Integer.parseInt(tfTime.getText()) > 0 && Integer.parseInt(tfTime.getText()) > maxTime) {
            Mereni mereni = new Mereni(Float.parseFloat(tfTemperature.getText()), Integer.parseInt(tfTime.getText()));
            list.add(mereni);

            if (mereni.getTime() > maxTime) {
                maxTime = mereni.getTime();
            }

            values += Float.parseFloat(tfTemperature.getText());

            if (min > Float.parseFloat(tfTemperature.getText())) {
                min = Float.parseFloat(tfTemperature.getText());
            }

            if (max < Float.parseFloat(tfTemperature.getText())) {
                max = Float.parseFloat(tfTemperature.getText());
            }

            prumer = values / (float) list.size();

            //data = new Data(mereni.getTemperature(), mereni.getTime());
            //series.getData().add(data);
            addToGraph(mereni.getTime(), mereni.getTemperature());
            textArea.setText("Teplotni statistika: \n"
                    + "Prumer: " + String.format("%2.2f", prumer) + "\n"
                    + "Min: " + String.format("%2.2f", min) + "\n"
                    + "Max: " + String.format("%2.2f", max));
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Chyba!");
            alert.setHeaderText("Zadane udaje nelze prevest na ciselne hodnoty!");
            alert.setContentText("Temperature musi byt realne cislo, cas musi byt nezaporne cele cislo!");
            alert.showAndWait();
        }

    }

    @FXML
    private void onBtnClear(ActionEvent event) {

        list.clear();
        series.getData().clear();
        lineChart.getData().removeAll();

        //counter = 0;
        max = 0;
        min = 9999;
        values = 0;
        prumer = 0;
        maxTime = 0;
        
        textArea.setText("Cleared...");
    }

    private void addToGraph(int x, float y) {
        series.getData().add(new XYChart.Data<Number, Number>(x, y));
    }

}
