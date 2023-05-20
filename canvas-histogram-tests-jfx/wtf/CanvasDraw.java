/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wtf;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color; // when you're writing with java fx, you have to do a lot of imports, that's fine
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType; // just note that imports have to be from javafx, not awt
import javafx.stage.Stage;

/**
 *
 * @author st67017
 */
public class CanvasDraw extends Application {

    public static void main(String[] args) {
        launch(args); //that's the start function in pretty much every java fx program
    }

    @Override //since we extend application, we have to implement it's abstract type, therefore 'start'
    public void start(Stage stage) throws Exception {
        Group root = new Group(); // new group of root
        Canvas canvas = new Canvas(800, 600); // creating a canvas
        GraphicsContext gc = canvas.getGraphicsContext2D(); // implementation of graphics context allowing us to draw

        //setClipping(gc);

        drawingBasics(gc); // method of drawing

        patternFill(gc); // method of drawing patterns

        gradDraw(gc); // method of drawing gradients

        drawPath(gc); // method of drawing paths

        root.getChildren().add(canvas); //typical javaFX thing to pass canvas to root
        stage.setScene(new Scene(root)); // creating a new scene
        stage.setTitle("Canvas basics"); // setting title for the scene
        stage.show(); // showing the scene to the user

    }

    private void drawingBasics(GraphicsContext gc) {
        gc.setFill(Color.GREEN); //self explanatory, sets color of filling to be green
        gc.setStroke(Color.BLUE); //same but for stroke

        gc.setLineWidth(5); // line width
        gc.strokeLine(10, 40, 100, 10); //drawing a line
        gc.fillOval(10, 60, 40, 20); //drawing a filled oval
        gc.strokeOval(60, 60, 40, 20); // drawing an empty oval
        gc.fillRoundRect(110, 60, 30, 30, 10, 10); //drawing filled rectangle
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10); // drawing empty rectangle

        gc.strokeArc(10, 110, 30, 30, 45, 240, ArcType.OPEN); // makes an arc of open type
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD); // + of chord type
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND); // + of round type

        gc.fillPolygon(new double[]{10, 40, 10, 40}, // makes a filled polygon from two doubles and an int
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90}, // an empty polygon
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140}, // a polyline (an even emptier polygon)
                new double[]{210, 210, 240, 240}, 4);
    }

    private void patternFill(GraphicsContext gc) {
        Image image = new Image("wtf/obr1.png"); // setting an image that we'd use as a pattern

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

        ImagePattern pat1 = new ImagePattern(image); // setting the pattern with above image
        gc.setFill(pat1); // filling
        gc.fillRect(300, 300, 100, 50); //x = width of screen, y = length of screen, x1 = width of rect, x2 = length of rect
        gc.strokeRect(300, 300, 100, 50);

        ImagePattern pat2 = new ImagePattern(image, 0, 0, 50, 100, false);
        gc.setFill(pat2);
        gc.fillRect(400, 300, 100, 50);
        gc.strokeRect(400, 300, 100, 50);

        ImagePattern pat3 = new ImagePattern(image, 0, 0, 0.25, 0.5, true);
        gc.setFill(pat3);
        gc.fillRect(500, 300, 100, 50);
        gc.strokeRect(500, 300, 100, 50);

    }

    private void gradDraw(GraphicsContext gc) {
        // coords for the gradient, then you should use false else the gradient seems to not work, then you choose a method of this gradient's cycle, and stops are stops for one gradient and another (and their number)
        LinearGradient gradient = new LinearGradient(330, 470, 350, 490, false, CycleMethod.REFLECT, new Stop(0f, Color.CORAL), new Stop(1f, Color.AQUA));
        gc.setFill(gradient);
        gc.setStroke(Color.BLACK);
        gc.fillRect(300, 450, 200, 100);
        gc.fillRect(300, 450, 200, 100);

    }

    private void drawPath(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.RED); //<- chosen color

        gc.beginPath(); // i assume it's a path from point a to point b
        gc.moveTo(50, 450);
        gc.lineTo(200, 400);
        gc.quadraticCurveTo(150, 500, 200, 600); //draws a curve from our path

        gc.fill(); //fills our thing with the chosen colour
        gc.stroke();
        gc.closePath();

    }

    private void setClipping(GraphicsContext gc) { //this clips out the image
        gc.beginPath();
        gc.moveTo(50, 50);
        gc.lineTo(120, 150);
        gc.lineTo(230, 200);
        gc.lineTo(300, 300);
        gc.stroke();
        gc.clip();
    }

}
