/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wtf;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author st67017
 */
public class CanvasEvent extends Application {

    Point2D lineStart = null;

    public static void main(String[] args) {
        launch(args); //that's the start function in pretty much every java fx program
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Canvas canvas = new Canvas(400, 400);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawBorder(gc);

        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, (t) -> {
            gc.setStroke(Color.BLACK);
            gc.strokeOval(t.getX(), t.getY(), 1, 1);
        });

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (t) -> {
            switch (t.getButton()) {
                case PRIMARY:
                    lineStart = new Point2D(t.getX(), t.getY());
                    break;
                case SECONDARY:
                    gc.setFill(Color.AQUA);
                    gc.fillRect(t.getX() - 10, t.getY() - 10, 20, 20);
                    break;
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (t) -> {
            if (t.getButton() == MouseButton.PRIMARY) {
                if (lineStart != null) {
                    gc.setStroke(Color.RED);
                    gc.strokeLine(lineStart.getX(), lineStart.getY(), t.getX(), t.getY());
                    lineStart = new Point2D(t.getX(), t.getY());
                }
            }
        });
        
        stage.setScene(new Scene(root));
        stage.setTitle("canvas event");
        stage.show();

    }

    private void drawBorder(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

}
