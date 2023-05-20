/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wtf;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;

/**
 *
 * @author st67017
 */
public class CanvasTransform extends Application {

    int canvasWidth = 400;
    int canvasHeight = 400;
    int centX = canvasWidth / 2;
    int centY = canvasHeight / 2;
    int stepsCount = 10;

    public static void main(String[] args) {
        launch(args); //that's the start function in pretty much every java fx program
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Canvas canvas = new Canvas();
        root.getChildren().add(canvas);

        draw(canvas.getGraphicsContext2D());

        stage.setScene(new Scene(root));
        stage.setTitle("canvas transform");
        stage.show();

    }

    private void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);

        gc.strokeOval(centX - 10, centY - 10, 20, 20);

        Affine transformate = new Affine();
        double rotAngle = 360f / stepsCount;
        transformate.appendRotation(rotAngle, centX, centY);

        for (int i = 0; i < stepsCount; i++) {
            gc.fillOval(centX + 100, centY - 20, 60, 40);
            gc.transform(transformate);
        }
    }

}
