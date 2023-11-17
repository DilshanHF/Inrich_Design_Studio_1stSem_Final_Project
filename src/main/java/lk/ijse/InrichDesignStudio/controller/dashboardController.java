package lk.ijse.InrichDesignStudio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class dashboardController {

    public AnchorPane pane;
    public Pane sidepane;
    public AnchorPane mainPane;


    public void btnOnDasboard(ActionEvent actionEvent) {//comment

    }

    public void btnOnCustomer(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customerForm.fxml")));

    }

    public void btnOnEmployee(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/employeeForm.fxml")));
    }

    public void btnOnOrders(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/ordersForm.fxml")));
    }

    public void btnOnItems(ActionEvent actionEvent) {

    }

    public void btnOnInventory(ActionEvent actionEvent) {

    }

    public void btnOnIncome(ActionEvent actionEvent) throws IOException {
        /*mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/incomeForm.fxml")));*/
    }

    public void btnOnExpenses(ActionEvent actionEvent) throws IOException {
        /*mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/expensesForm.fxml")));*/
    }

    public void btnOnFinance(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/financialForm.fxml")));


    }
}
