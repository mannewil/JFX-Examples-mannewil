/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package zaklad2;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author st67017
 */
public class FXMLDocument2Controller implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private ImageView imgView;
    @FXML
    private LineChart histogramChart;
    @FXML
    private StackPane imgViewPane;
    @FXML
    private AnchorPane histPane;

    private ButtonType end;
    private Menu fileMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //make it so the chart lines aren't made of symbols
        histogramChart.setCreateSymbols(false);
        
        //clear the menu files to get rid of pre-set Scene Builder values in the menu bar
        menuBar.getMenus().clear();       
        fileMenu = new Menu("Soubor");
        MenuItem openFile = new MenuItem("Načti obrázek...");
        MenuItem endFile = new MenuItem("Konec");
        openFile.setOnAction((t) -> {
            readImage();
        });
        endFile.setOnAction((t) -> {
            // if selected, ask the user if they're sure they want to close the program
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konec?");
            alert.setHeaderText("Jste se jísty, že chcete program ukončit?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                // way to force-exit from javafx-based program
                Platform.exit();
                System.exit(0);
            }
        });
        fileMenu.getItems().addAll(openFile, endFile);
        menuBar.getMenus().add(fileMenu);
        
        //bind property of imageView to the container, making the view redraw the image upon resize
        imgView.fitWidthProperty().bind(imgViewPane.widthProperty().divide(100).multiply(80));
        imgView.fitHeightProperty().bind(imgViewPane.heightProperty().divide(100).multiply(80));

    }

    private void readImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG obrázek", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP obrázek", "*.bmp")
        );

        File selectedFile = fileChooser.showOpenDialog(null); //null because otherwise is impossible, in non-scene builder programs usually you supply a parent scene to it
        if (selectedFile != null) {
            try {
                imgViewPane.getChildren().clear();
                imgViewPane.getChildren().add(imgView);
                
                histPane.getChildren().clear();
                histPane.getChildren().add(histogramChart);
                
                imgView.setImage(new Image(new FileInputStream(selectedFile)));
                
                updateHistogram();
            } catch (Exception e) { // if (usually) IOException is caught, display an alert message instead
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Chyba čteni!");
                alert.setContentText("čteni souboru " + selectedFile.getName() + " selhalo.");
                alert.showAndWait();
            }
        }
    }

    private void updateHistogram() {
        // remove prior data
        histogramChart.getData().clear();        
        Image image = imgView.getImage();        
        PixelReader reader = image.getPixelReader();
        
        // get current image width and height (x, y)
        int imageWidth = (int) image.getWidth();
        int imageHeight = (int) image.getHeight();
        
        // arrays for colour values
        double[] redColorValues = new double[256];
        double[] greenColorValues = new double[256];
        double[] blueColorValues = new double[256];
        
        // for each of the pixels in the image, get their colour and assign it to arrays of colour values
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                Color pixelColor = reader.getColor(x, y);
                redColorValues[(int) (pixelColor.getRed() * 255)]++;
                greenColorValues[(int) (pixelColor.getGreen() * 255)]++;
                blueColorValues[(int) (pixelColor.getBlue() * 255)]++;
            }
        }
        
        // normalization, finds the maximum in each of the colour values
        int maxCount = 0;
        for (int i = 0; i < 256; i++) {
            maxCount = (int) Math.max(maxCount, redColorValues[i]);
            maxCount = (int) Math.max(maxCount, greenColorValues[i]);
            maxCount = (int) Math.max(maxCount, blueColorValues[i]);
        }
        
        // make new series to populate the chart with
        XYChart.Series<Number, Number> redSeries = new XYChart.Series<>();
        redSeries.setName("Červená");

        XYChart.Series<Number, Number> greenSeries = new XYChart.Series<>();
        greenSeries.setName("Zelená");

        XYChart.Series<Number, Number> blueSeries = new XYChart.Series<>();
        blueSeries.setName("Modrá");
        
        
        for (int i = 0; i < 256; i++) {
            // normalization, formula takes the array values, and divides it by the maximum, multiplying by 100
            // this leads to the case where the max value in our chart is always 100, never more, never less
            double redPercentage = (double) redColorValues[i] / maxCount * 100;
            double greenPercentage = (double) greenColorValues[i] / maxCount * 100;
            double bluePercentage = (double) blueColorValues[i] / maxCount * 100;
            
            // add values to series
            redSeries.getData().add(new XYChart.Data<>(i, redPercentage));
            greenSeries.getData().add(new XYChart.Data<>(i, greenPercentage));
            blueSeries.getData().add(new XYChart.Data<>(i, bluePercentage));
        }
        // add series to the chart
        histogramChart.getData().addAll(redSeries, greenSeries, blueSeries);
    }

}
