/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package assignmenttest;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author wille
 */
public class FXMLController implements Initializable {

    @FXML
    private ListView<Item> listViewStore;
    @FXML
    private ListView<Item> listViewCart;
    @FXML
    private TextField tfBudget;
    @FXML
    private CheckBox checkBoxBudget;
    @FXML
    private Button btnMovetoCart;
    @FXML
    private Button btnMoveBack;
    @FXML
    private Label labelTotalPrice;

    private final double unsetBudget = 999999;
    private double budget = unsetBudget;
    private double price = 0;

    ObservableList<Item> oListStore = FXCollections.observableArrayList(
            new Item("MB 1", 600),
            new Item("MB 2", 400),
            new Item("MB 3", 150),
            new Item("CPU 1", 400),
            new Item("CPU 2", 100),
            new Item("GPU 1", 300),
            new Item("GPU 2", 150),
            new Item("CPU fan", 40),
            new Item("Case fan", 15),
            new Item("Case", 70));
    ObservableList<Item> oListCart = FXCollections.observableArrayList();
    @FXML
    private GridPane gridPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfBudget.setVisible(false);

        updatePrice();

        listViewStore.setItems(oListStore);
        listViewStore.getSelectionModel().select(0);
        listViewCart.setItems(oListCart);
    }

    @FXML
    private void onBtnMovetoCart(ActionEvent event) {
        if (listViewStore.getSelectionModel().getSelectedItem() != null) {
            Item item = listViewStore.getSelectionModel().getSelectedItem();
            price += item.getPrice();
            oListCart.add(item);
            listViewCart.refresh();
            updatePrice();
        }
    }

    @FXML
    private void onBtnMoveBack(ActionEvent event) {
        if (listViewCart.getSelectionModel().getSelectedItem() != null) {
            Item item = listViewCart.getSelectionModel().getSelectedItem();
            price -= item.getPrice();
            oListCart.remove(item);
            listViewCart.refresh();
            updatePrice();
        }
    }

    private void updatePrice() {

        tfBudget.visibleProperty().bind(checkBoxBudget.selectedProperty());

        if (!tfBudget.getText().isEmpty()) {
            budget = Double.parseDouble(tfBudget.getText());
        }

        if (checkBoxBudget.isSelected() == false) {
            tfBudget.clear();
            budget = unsetBudget;
        }

        labelTotalPrice.setText(String.format("%5.2f $", price));

        if (price > budget) {
            labelTotalPrice.setTextFill(Color.RED);
            gridPane.setStyle(
                    "-fx-padding:5;"
                    + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;"
                    + "-fx-border-radius: 5;"
                    + "-fx-border-color: red;");
        } else {
            labelTotalPrice.setTextFill(Color.GREEN);
            gridPane.setStyle(
                    "-fx-padding:5;"
                    + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;"
                    + "-fx-border-radius: 5;"
                    + "-fx-border-color: green;");
        }

    }

}
