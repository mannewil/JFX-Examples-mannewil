/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JavaFXDrawing;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author wille
 */
public class Main extends Application {

    Point2D lineStart = null;
    int canvasWidth = 800;
    int canvasHeight = 400;
    Canvas canvas = new Canvas(canvasWidth, canvasHeight);
    Slider slider;

    Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        HBox buttonBox = new HBox();
        HBox canvasBox = new HBox();
        HBox.setHgrow(canvasBox, Priority.ALWAYS);
        slider = new Slider(1, 20, 1);
        slider.setOrientation(Orientation.HORIZONTAL);
        slider.setMinorTickCount(10);
        slider.setMajorTickUnit(10.0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setSnapToTicks(true);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearCanvas(gc);
        Button btnClear = new Button("Reset Canvas");
        btnClear.setOnAction((t) -> {
            clearCanvas(gc);
        });

        Button btnClose = new Button("Close program");
        btnClose.setOnAction((t) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Truly close the program?");
            alert.setContentText("OK to close.");
            if (alert.showAndWait().get() == ButtonType.OK) {
                stage.close();
            }
        });

        Label firstColor = new Label("Color 1 = ");
        ColorPicker colorPicker1 = new ColorPicker();
        Label secondColor = new Label("Color 2 = ");
        ColorPicker colorPicker2 = new ColorPicker();
        canvasBox.getChildren().add(canvas);
        buttonBox.getChildren().addAll(btnClear, btnClose, firstColor, colorPicker1, secondColor, colorPicker2, slider);
        root.getChildren().addAll(buttonBox, canvasBox);

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (t) -> {
            if (t.getButton() == MouseButton.PRIMARY) {
                if (lineStart != null) {
//                    gc.setFill(colorPicker1.getValue());
//                    gc.fillOval(lineStart.getX(), lineStart.getY(), slider.getValue(),  slider.getValue());
                    gc.setStroke(colorPicker1.getValue());
                    gc.setLineWidth(slider.getValue());
                    gc.strokeLine(lineStart.getX(), lineStart.getY(), t.getX(), t.getY());
                    lineStart = new Point2D(t.getX(), t.getY());
                }
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (t) -> {
            if (t.getButton() == MouseButton.SECONDARY) {
                gc.setLineWidth(1);
                gc.setStroke(colorPicker2.getValue());
                gc.strokeOval(t.getX(), t.getY(), 20 + slider.getValue(), 20 + slider.getValue());
                gc.setStroke(colorPicker1.getValue());
                gc.strokeOval(t.getX() - 10, t.getY() - 10, 40 + slider.getValue(), 40 + slider.getValue());
                gc.setStroke(colorPicker2.getValue());
                gc.strokeOval(t.getX() - 20, t.getY() - 20, 60 + slider.getValue(), 60 + slider.getValue());
                gc.setStroke(colorPicker1.getValue());
                gc.strokeOval(t.getX() - 30, t.getY() - 30, 80 + slider.getValue(), 80 + slider.getValue());
            }
            if (t.getButton() == MouseButton.PRIMARY) {
                lineStart = new Point2D(t.getX(), t.getY());
            }
        });
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setTitle("Demchenko Mikhail - canvas");
        stage.show();
    }

    private void drawBorder(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void clearCanvas(GraphicsContext gc) {
        slider.setValue(1);
        gc.setLineWidth(slider.getValue());
        gc.setFill(Color.PAPAYAWHIP);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
        drawBorder(gc);
    }

}
