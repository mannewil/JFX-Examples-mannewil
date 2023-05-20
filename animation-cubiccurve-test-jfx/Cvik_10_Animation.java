/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cvik_10;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author st67017
 */
public class Cvik_10_Animation extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Ball_Pane());
        stage.setTitle("Ball animation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
