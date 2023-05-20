/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignmenttest;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author wille
 */
public class AssignmentTest extends Application {

    public static Stage stage;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AssignmentTest.stage = stage;

        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));

        Scene fxmlpractice = new Scene(root);

        stage.setTitle("Shop");
        stage.setScene(fxmlpractice);
        stage.show();
    }
    
}
