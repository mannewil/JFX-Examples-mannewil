/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package histogram;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author st67017
 */
public class Hist_fxmlController implements Initializable {

    @FXML
    private ImageView img;
    @FXML
    private BarChart<String, Double> bc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("ladybug.png");
        Image image2 = new Image ("darkforest.jpg");
        img.setImage(image2);

        PixelReader pixelReader = image2.getPixelReader();
        System.out.println("Pixel format: " + pixelReader.getPixelFormat());

        double[] brightnessArray = new double[11];

        XYChart.Series series = new XYChart.Series();
        series.setName("Final brightness");

        for (int i = 0; i < brightnessArray.length; i++) {
            brightnessArray[i] = 0;
        }

        for (int i = 0; i < image2.getHeight(); i++) {
            for (int j = 0; j < image2.getWidth(); j++) {
                Color color = pixelReader.getColor(j, i);

                double brightness = color.getBrightness();
                brightnessArray[(int) (brightness / 0.1)]++;
            }
        }
        for (int i = 0; i < brightnessArray.length; i++) {
            series.getData().add(new XYChart.Data(
                    Integer.toString(i * 10) + " - " + Integer.toString(i * 10 + 9), brightnessArray[i])
            );
        }
        
        bc.getData().addAll(series);

    }

}
