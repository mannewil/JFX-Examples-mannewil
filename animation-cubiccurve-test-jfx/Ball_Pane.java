/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cvik_10;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 * @author st67017
 */
public class Ball_Pane extends Pane {

    Circle ball;

    double dx = 5, dy = 5;
    final double BALL_RAD = 20;

    public Ball_Pane() {
        this.setStyle("--fx-background-color: black");

        this.widthProperty().addListener((o) -> {
            redraw();
            animate();
        });

        this.heightProperty().addListener((o) -> {
            redraw();
            animate();
        });

    }

    private void redraw() {
        ball = new Circle(this.getWidth() / 2, this.getHeight() / 2, BALL_RAD);
        ball.setStroke(null);
        ball.setFill(Color.LIGHTBLUE);
        this.getChildren().clear();
        this.getChildren().add(ball);
    }

    private void animate() {
//        Timeline timeline = new Timeline();
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.setAutoReverse(true);
//        final KeyValue kvl = new KeyValue(ball.centerXProperty(), 0);
//        final KeyValue kv2 = new KeyValue(ball.centerYProperty(), 0);
//        final KeyFrame kf1 = new KeyFrame(Duration.millis(1000), kvl);
//        final KeyFrame kf2 = new KeyFrame(Duration.millis(1000), kv2);
//        timeline.getKeyFrames().addAll(kf1, kf2);
//        timeline.play();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), (t) -> {
            ball.setCenterX(ball.getCenterX() + dx);
            ball.setCenterY(ball.getCenterY() + dy);

            if (ball.getCenterX() <= 0 + BALL_RAD || ball.getCenterX() >= this.getWidth() - ball.getRadius()) {
                dx = -dx;
            }
            if (ball.getCenterY() <= 0 + BALL_RAD || ball.getCenterY() >= this.getHeight() - ball.getRadius()) {
                dy = -dy;
            }

        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

}
