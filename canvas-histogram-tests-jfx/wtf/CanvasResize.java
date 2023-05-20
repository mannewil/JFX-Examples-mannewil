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
import javafx.stage.Stage;

/**
 *
 * @author st67017
 */
public class CanvasResize extends Application {

    Canvas canvas;

    public static void main(String[] args) {
        launch(args); //that's the start function in pretty much every java fx program
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        canvas = new Canvas();
        root.getChildren().add(canvas);
        canvas.widthProperty().bind(root.widthProperty()); // binds width
        canvas.heightProperty().bind(root.heightProperty()); // binds height

        canvas.widthProperty().addListener((o) -> { //makes the lines on the screen unaffected (to not disappear)
            draw();
        });
        canvas.heightProperty().addListener((o) -> { // same but for height
            draw();
        });

        stage.setScene(new Scene(root));
        stage.setTitle("canvas resize");
        stage.show();

        draw();
    }

    private void draw() {
        if (canvas != null) {
            double width = canvas.getWidth(); // we get width from our window
            double height = canvas.getHeight(); // same for height

            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, width, height); // clearning a rectangle

            gc.setStroke(Color.RED);
            gc.strokeLine(0, 0, width, height); //drawing a line across the window
            gc.strokeLine(0, height, width, 0); //same but different coords

        }

    }
}
