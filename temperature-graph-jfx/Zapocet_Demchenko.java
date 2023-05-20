/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package zapocet_demchenko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author st67017
 */
public class Zapocet_Demchenko extends Application {
    public static Stage stage;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
       Zapocet_Demchenko.stage = stage;
       Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
       
       Scene fxml = new Scene(root);
       
       stage.setTitle("Demchenko Mikhail");
       stage.setScene(fxml);
       stage.show();
    }
    
}
