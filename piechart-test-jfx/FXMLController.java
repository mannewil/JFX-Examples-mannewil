/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package piecharttest;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wille
 */
public class FXMLController implements Initializable {

    @FXML
    private Button btnGenerate;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnExit;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label labelPocet;
    @FXML
    private Label labelArithmetic;

    Random rand;
    int counter = 0;
    double values = 0;
    double arithm = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rand = new Random();
        pieChart.setTitle("Chart of random things.");
        clearValues();
    }

    @FXML
    private void onBtnGenerate(ActionEvent event) {
        // Node node = new Node(String.valueOf(rand.nextInt(40)), rand.nextInt(40));
        int value = rand.nextInt(10) + 1;
        labelPocet.setText("Pocet = " + ++counter);
        values += value;
        arithm = values / counter;
        labelArithmetic.setText("Prumer = " + String.format("%.3f", arithm));
        PieChart.Data nData = new PieChart.Data(String.valueOf(value), value);
        pieChart.getData().add(nData);
    }

    @FXML
    private void onBtnClear(ActionEvent event) {
        pieChart.getData().clear();
        clearValues();
    }

    @FXML
    private void onBtnExit(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm exit?");
        alert.setHeaderText("Really exit the program?");
        alert.setContentText("Press OK to exit.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();
        } else {
            alert.close();
        }
    }

    private void clearValues() {
        counter = 0;
        values = 0;
        arithm = 0;
        labelPocet.setText("None");
        labelArithmetic.setText("None");
    }

}
