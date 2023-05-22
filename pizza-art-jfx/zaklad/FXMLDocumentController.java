package zaklad;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author wille
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ListView<Ingredients> listViewIngredients;
    @FXML
    private Button btnDrawDough;
    @FXML
    private Button btnAddIngredient;
    @FXML
    private TextField tfIngreidentName;
    @FXML
    private Spinner<Integer> spinnerIngredientSize;
    @FXML
    private Spinner<Double> spinnerTranslucency;
    @FXML
    private ColorPicker colorPickerIngredient;
    @FXML
    private Canvas canvas;
    @FXML
    private StackPane paneCanvas;
    @FXML
    private Rectangle rectangleColorList;

    private SpinnerValueFactory svf;
    private SpinnerValueFactory svfOpacity;
    private GraphicsContext gc;

    private boolean doughExists;

    private Color dough;
    private Color sauce;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dough = Color.rgb(247, 228, 196, 1.0);
        sauce = Color.rgb(178, 24, 7, 0.48);
        gc = canvas.getGraphicsContext2D();
        doughExists = false;

        svf = new IntegerSpinnerValueFactory(10, 40, 10, 5);
        spinnerIngredientSize.setValueFactory(svf);
        spinnerIngredientSize.setEditable(false);

        svfOpacity = new DoubleSpinnerValueFactory(0.1, 1, 1, 0.1);
        spinnerTranslucency.setValueFactory(svfOpacity);
        spinnerTranslucency.setEditable(false);

        colorPickerIngredient.setValue(Color.RED);
        rectangleColorList.setFill(Color.WHEAT);

        listViewIngredients.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> {
            rectangleColorList.setFill(t1.getColor());
        });

        paneCanvas.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            canvas.setWidth(newWidth.doubleValue());
            clearCanvas();
        });

        paneCanvas.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            canvas.setHeight(newHeight.doubleValue());
            clearCanvas();
        });
    }

    @FXML
    private void onClickDrawDough(ActionEvent event) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Map<String, Double> circleProperties = calculateCircleProperties();
        double centX = circleProperties.get("centX");
        double centY = circleProperties.get("centY");
        double radius = circleProperties.get("radius");

        gc.setFill(dough);
        gc.fillOval(centX - radius, centY - radius, radius * 2, radius * 2);
        gc.setFill(sauce);
        gc.fillOval(centX - radius + 10, centY - radius + 10, (radius * 2) - 20, (radius * 2) - 20);
        doughExists = true;
    }

    @FXML
    private void onBtnAddIngredient(ActionEvent event) {
        if (validateInputs()) {
            Ingredients item = new Ingredients(tfIngreidentName.getText(), spinnerIngredientSize.getValue(), colorPickerIngredient.getValue());
            item.setTransparentColor(spinnerTranslucency.getValue());
            listViewIngredients.getItems().add(item);
            spinnerTranslucency.getValueFactory().setValue(1.0);
        }
    }

    @FXML
    private void onCanvasClick(MouseEvent event) {
        if (getSelectedIngredient() == null) {
            showAlertMessage("Can't draw without a selected item!");
            return;
        }

        if (doughExists == false) {
            showAlertMessage("There's no pizza to add to. What are you doing?");
            return;
        }

        double adjustedX = event.getX() - getSelectedIngredient().getSize() / 2;
        double adjustedY = event.getY() - getSelectedIngredient().getSize() / 2;

        if (event.getButton() == MouseButton.PRIMARY && isInsideCircle(event.getX(), event.getY())) {
            gc.setFill(getSelectedIngredient().getColor());
            gc.fillOval(adjustedX, adjustedY, getSelectedIngredient().getSize(), getSelectedIngredient().getSize());
        }
    }

    @FXML
    private void onCanvasDragClick(MouseEvent event) {
        if (getSelectedIngredient() == null) {
            showAlertMessage("Can't draw without a selected item!");
            return;
        }

        if (doughExists == false) {
            showAlertMessage("There's no pizza to add to. What are you doing?");
            return;
        }

        double adjustedX = event.getX() - getSelectedIngredient().getSize() / 2;
        double adjustedY = event.getY() - getSelectedIngredient().getSize() / 2;

        if (event.getButton() == MouseButton.SECONDARY && isInsideCircle(event.getX(), event.getY())) {
            gc.setFill(getSelectedIngredient().getColor());
            gc.setStroke(getSelectedIngredient().getColor());
            gc.setLineWidth(getSelectedIngredient().getSize() / 4);
            gc.strokeLine(adjustedX, adjustedY, event.getX(), event.getY());
        }
    }

    private boolean validateInputs() {
        if (tfIngreidentName.getText().isBlank()) {
            showAlertMessage("The text field is empty...");
            return false;
        }
        return true;
    }

    private void showAlertMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(message);
        alert.setHeaderText("Error!");
        alert.showAndWait();
    }

    private Ingredients getSelectedIngredient() {
        return listViewIngredients.getSelectionModel().getSelectedItem();
    }

    private boolean isInsideCircle(double x, double y) {
        Map<String, Double> circleProperties = calculateCircleProperties();
        double centX = circleProperties.get("centX");
        double centY = circleProperties.get("centY");
        double radius = circleProperties.get("radius");

        double distance = Math.sqrt(Math.pow(x - centX, 2) + Math.pow(y - centY, 2));
        double distanceFromEdge = distance - radius;
        return distanceFromEdge <= 0;
    }

    private Map<String, Double> calculateCircleProperties() {
        Map<String, Double> properties = new HashMap<>();

        double widthCanvas = canvas.getWidth();
        double heightCanvas = canvas.getHeight();

        double centX = widthCanvas / 2;
        double centY = heightCanvas / 2;
        double radius = Math.min(widthCanvas, heightCanvas) / 4;

        properties.put("centX", centX);
        properties.put("centY", centY);
        properties.put("radius", radius);

        return properties;
    }

    private void clearCanvas() {
        if (doughExists) {
            gc.setFill(Color.TRANSPARENT);
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            doughExists = false;
        }
    }

}
